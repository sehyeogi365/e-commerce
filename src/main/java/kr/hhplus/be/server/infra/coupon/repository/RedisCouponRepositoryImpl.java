package kr.hhplus.be.server.infra.coupon.repository;

import kr.hhplus.be.server.domain.coupon.repository.RedisCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class RedisCouponRepositoryImpl implements RedisCouponRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String COUPON_ZSET_KEY = "coupon:queue";  // 선착순 처리
    //private static final String COUPON_SET_KEY = "coupon:issued";  // 중복 방지
    private static final String COUPON_COUNT_KEY = "coupon:count";
    private static final String COUPON_CACHE_KEY = "coupon:info";
    // 쿠폰 정보 조회
    @Override
    public void cacheCouponInfo(long couponId, String info) {
        redisTemplate.opsForValue().set(COUPON_CACHE_KEY + ":" + couponId, info, 10, TimeUnit.MINUTES);
    }

    @Override
    public String getCouponInfo(long couponId) {
        return (String) redisTemplate.opsForValue().get(COUPON_CACHE_KEY + ":" + couponId);
    }

    // 선착순 요청 등록
    @Override
    public void addCouponRequest(Long couponId, Long userId) {
        double score = Instant.now().getEpochSecond(); // UNIX timestamp (초 단위)
        redisTemplate.opsForZSet().add("coupon:request:" + couponId, userId, score);
    }

    // 선착순 쿠폰 개수 확인
    @Override
    public Integer couponCount(Long couponId){
        Object countStr = redisTemplate.opsForValue().get(COUPON_COUNT_KEY);
        return Integer.parseInt((String) countStr);
    }

    // 쿠폰 만료 여부 체크 (Redis 캐싱 활용)
    @Override
    public String expirationDateStr(Long couponId){
        Object expirationDate = redisTemplate.opsForValue().get("coupon:expiration:" + couponId);
        return (String) expirationDate;
    }

    // Redis에 캐싱 (10분 유지)
    @Override
    public void couponExpiration(Long couponId){
        Object expirationDate = redisTemplate.opsForValue().get("coupon:expiration:" + couponId);
        redisTemplate.opsForValue().set("coupon:expiration:" + couponId, expirationDate.toString(), 10, TimeUnit.MINUTES);
    }

    // 쿠폰 개수 감소
    @Override
    public Long decrementCouponCount(Long couponId) {
        return redisTemplate.opsForValue().decrement(COUPON_COUNT_KEY + couponId);
    }

    // 쿠폰 개수 롤백 (오버 차감 방지)
    @Override
    public void rollbackCouponCount(Long couponId) {
        redisTemplate.opsForValue().increment("coupon:count:" + couponId);
    }

    // 중복 발급 방지 (Set 활용)
    @Override
    public boolean isCouponAlreadyIssued(Long userId, Long couponId) {
        String key = "coupon:issued:" + couponId; // 쿠폰별 발급된 사용자 저장
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, userId));
    }

    // 쿠폰 발급 처리
    @Override
    public void issueCouponToUser(Long userId, Long couponId) {
        String key = "coupon:issued:" + couponId; // 쿠폰별 발급된 사용자 저장
        redisTemplate.opsForSet().add(key, userId);
    }
}
