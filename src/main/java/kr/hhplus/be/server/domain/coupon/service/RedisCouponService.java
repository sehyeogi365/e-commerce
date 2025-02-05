package kr.hhplus.be.server.domain.coupon.service;


import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.infra.coupon.repository.RedisCouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.coupon.dto.UserCouponResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class RedisCouponService {

    private final RedisCouponRepositoryImpl redisCouponRepository;
    private final CouponRepository couponRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String COUPON_ZSET_KEY = "coupon:queue";  // 선착순 처리
    private static final String COUPON_SET_KEY = "coupon:issued";  // 중복 방지
    private static final String COUPON_COUNT_KEY = "coupon:count";

    private static final String COUPON_CACHE_KEY = "coupon:info";

    public void cacheCouponInfo(String couponId, String info) {
        redisTemplate.opsForValue().set(COUPON_CACHE_KEY + ":" + couponId, info, 10, TimeUnit.MINUTES);
    }

    public String getCouponInfo(String couponId) {
        return redisTemplate.opsForValue().get(COUPON_CACHE_KEY + ":" + couponId);
    }
    // 쿠폰 발급
    public UserCouponResponse issueCoupon(UserCoupon userCoupon) {
        long userId = userCoupon.getUserId();
        long couponId = userCoupon.getCouponId();

        // 1. 중복 발급 방지 (Set 활용)
        Boolean alreadyIssued = redisTemplate.opsForSet().isMember(COUPON_SET_KEY, userId);
        if (Boolean.TRUE.equals(alreadyIssued)) {
            throw new CustomException(ErrorCode.COUPON_ALREADY_ISSUED);
        }

        // 2. 쿠폰 개수 감소 (String - decrement 활용)
        String couponCountStr = redisTemplate.opsForValue().get(COUPON_COUNT_KEY);
        int couponCount = (couponCountStr != null) ? Integer.parseInt(couponCountStr) : 0;

        if (couponCount <= 0) {
            throw new CustomException(ErrorCode.COUPON_QUANTITY_ZERO);
        }

        // 3️⃣ 쿠폰 만료 여부 체크 (Redis 캐싱 활용)
        String expirationDateStr = redisTemplate.opsForValue().get("coupon:expiration:" + couponId);
        LocalDate expirationDate = expirationDateStr != null ? LocalDate.parse(expirationDateStr) : null;

        if (expirationDate == null) {
            Coupon coupon = couponRepository.findCouponInfo(couponId)
                    .orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));
            expirationDate = coupon.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // Redis에 캐싱 (10분 유지)
            redisTemplate.opsForValue().set("coupon:expiration:" + couponId, expirationDate.toString(), 10, TimeUnit.MINUTES);
        }

        if (expirationDate.isBefore(LocalDate.now())) {
            throw new CustomException(ErrorCode.COUPON_OVER_DATE);
        }

        // 4️⃣ 쿠폰 수량 감소 (Redis에서 차감)
        Long newCount = redisTemplate.opsForValue().decrement(COUPON_COUNT_KEY);
        if (newCount != null && newCount < 0) {
            redisTemplate.opsForValue().increment(COUPON_COUNT_KEY); // 롤백
            throw new CustomException(ErrorCode.COUPON_QUANTITY_ZERO);
        }

        // 5️⃣ 쿠폰 발급 기록 (Set에 저장)
        //redisCouponRepository.issueCouponToUser(userId);
        redisTemplate.opsForSet().add(COUPON_SET_KEY, String.valueOf(userId));

        // 6️⃣ (비동기) 일정 시간 후 DB 반영
        CompletableFuture.runAsync(() -> {
            redisCouponRepository.decrementCouponCount(couponId);
        });

        return new UserCouponResponse(couponId, userId);
    }

}
