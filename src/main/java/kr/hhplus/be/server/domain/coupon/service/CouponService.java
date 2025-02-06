package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.infra.coupon.repository.RedisCouponRepositoryImpl;
import kr.hhplus.be.server.interfaces.coupon.dto.CouponResponse;
import kr.hhplus.be.server.interfaces.coupon.dto.UserCouponResponse;
import kr.hhplus.be.server.interfaces.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;//TODO: 생성자 or 롬복 주입 required..
    private final RedisCouponRepositoryImpl redisCouponRepository;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String COUPON_ZSET_KEY = "coupon:queue";  // 선착순 처리
    //private static final String COUPON_SET_KEY = "coupon:issued";  // 중복 방지
    private static final String COUPON_COUNT_KEY = "coupon:count";
    private static final String COUPON_CACHE_KEY = "coupon:info";

    public void cacheCouponInfo(long couponId, String info) {
        redisTemplate.opsForValue().set(COUPON_CACHE_KEY + ":" + couponId, info, 10, TimeUnit.MINUTES);
    }

    public String getCouponInfo(long couponId) {
        return redisTemplate.opsForValue().get(COUPON_CACHE_KEY + ":" + couponId);
    }

    // TODO: 쿠폰 선착순요청 sorted set 자료구조 활용 쿠폰 중복 발급방지 sets 자료구조 활용

    // 쿠폰 목록 조회
    public List<CouponResponse> getCouponList(){// 메서드 명칭 변경 or 타입변경
        List<Coupon> couponList = couponRepository.getCoupons();

        List<CouponResponse> response = new ArrayList<>();

        for(Coupon coupon : couponList){
            response.add(new CouponResponse(coupon.getId()));
        }

        return response;
    }

    // 쿠폰 발급
    //@Transactional
    public UserCouponResponse issueCoupon(UserCoupon userCoupon){
        LocalDate today = LocalDate.now();

        // 쿠폰 한행 -> 쿠폰 정보를 가져온다
        //Coupon coupon = couponRepository.findCouponInfo(userCoupon.getCouponId()).orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));
        // 쿠폰 있는지 없는지 여부 판별

//        if(coupon.getQuantity() <= 0){
//            throw new CustomException(ErrorCode.COUPON_QUANTITY_ZERO);// COUPON_NOT_FOUND -> CouponQuatntity Notfound 뭐 이런식으로 변경
//        }
        long userId = userCoupon.getUserId();
        long couponId = userCoupon.getCouponId();

        // 1. 중복 발급 방지 (Set 활용)
        //Boolean alreadyIssued = redisTemplate.opsForSet().isMember(COUPON_SET_KEY, userId);
        if (redisCouponRepository.isCouponAlreadyIssued(userId, couponId)) {
            throw new CustomException(ErrorCode.COUPON_ALREADY_ISSUED);
        }

        // 2. 선착순 요청 등록
        redisCouponRepository.addCouponRequest(couponId, userId);

        // 3. 선착순 쿠폰 개수 확인 (Redis에서 수량 확인)
        String couponCountStr = redisTemplate.opsForValue().get(COUPON_COUNT_KEY);
        int couponCount = (couponCountStr != null) ? Integer.parseInt(couponCountStr) : 0;

        if (couponCount <= 0) {
            throw new CustomException(ErrorCode.COUPON_QUANTITY_ZERO);
        }

        // 4. 쿠폰 만료 여부 체크 (Redis 캐싱 활용)
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

        // 5. 쿠폰 수량 감소 (Redis에서 차감)
        Long newCount = redisTemplate.opsForValue().decrement(COUPON_COUNT_KEY);
        if (newCount != null && newCount < 0) {
            redisCouponRepository.rollbackCouponCount(couponId);
            //redisTemplate.opsForValue().increment(COUPON_COUNT_KEY); // 롤백
            throw new CustomException(ErrorCode.COUPON_QUANTITY_ZERO);
        }

        // 6. 쿠폰 발급 기록 (Set에 저장)
        redisCouponRepository.issueCouponToUser(userId, couponId);
        //redisTemplate.opsForSet().add(COUPON_SET_KEY, String.valueOf(userId));

        // 7. (비동기) 일정 시간 후 DB 반영
        CompletableFuture.runAsync(() -> {
            redisCouponRepository.decrementCouponCount(couponId);
        });
        return new UserCouponResponse(couponId, userId);
    }

    // 사용자 쿠폰 목록 조회
    public List<UserCouponResponse> getUserCoupon(long userId){
        List<UserCoupon> userCouponList = couponRepository.getUserCoupon(userId);

        if(userCouponList.isEmpty()){
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }

        List<UserCouponResponse> response = new ArrayList<>();
        for(UserCoupon userCoupon : userCouponList){
            response.add(new UserCouponResponse(userCoupon.getUserId(), userCoupon.getCouponId()));
        }

        return response;// impl에서 쿠폰 있는지 없는지 로직처리
    }

}
