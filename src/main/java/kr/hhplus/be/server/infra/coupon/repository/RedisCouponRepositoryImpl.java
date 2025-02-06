package kr.hhplus.be.server.infra.coupon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
@Repository
@RequiredArgsConstructor
@Transactional
public class RedisCouponRepositoryImpl {

    private final RedisTemplate<String, Object> redisTemplate;

    // 선착순 요청 등록
    public void addCouponRequest(Long couponId, Long userId) {
        double score = Instant.now().getEpochSecond(); // UNIX timestamp (초 단위)
        redisTemplate.opsForZSet().add("coupon:request:" + couponId, userId, score);
    }

    // 쿠폰 개수 감소
    public Long decrementCouponCount(Long couponId) {
        return redisTemplate.opsForValue().decrement("coupon:count:" + couponId);
    }

    // 쿠폰 개수 롤백 (오버 차감 방지)
    public void rollbackCouponCount(Long couponId) {
        redisTemplate.opsForValue().increment("coupon:count:" + couponId);
    }

    // 중복 발급 방지 (Set 활용)
    public boolean isCouponAlreadyIssued(Long userId, Long couponId) {
        String key = "coupon:issued:" + couponId; // 쿠폰별 발급된 사용자 저장
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, userId));
    }

    // 쿠폰 발급 처리
    public void issueCouponToUser(Long userId, Long couponId) {
        String key = "coupon:issued:" + couponId; // 쿠폰별 발급된 사용자 저장
        redisTemplate.opsForSet().add(key, userId);
    }
}
