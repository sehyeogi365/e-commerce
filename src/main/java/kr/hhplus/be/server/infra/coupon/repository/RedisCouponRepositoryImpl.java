package kr.hhplus.be.server.infra.coupon.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@Repository
@RequiredArgsConstructor
@Transactional
public class RedisCouponRepositoryImpl {

    // 쿠폰 발급
    private final RedisTemplate<String, Long> redisTemplate;

    // 쿠폰 개수 감소
    public Long decrementCouponCount(Long couponId) {
        return redisTemplate.opsForValue().decrement("coupon:count:" + couponId);
    }

    // 쿠폰 개수 롤백 (오버 차감 방지)
    public void rollbackCouponCount(Long couponId) {
        redisTemplate.opsForValue().increment("coupon:count:" + couponId);
    }

    // 중복 발급 방지 (Set 활용)
    public boolean isCouponAlreadyIssued(Long userId) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("coupon:users", userId));
    }

    // 쿠폰 발급 처리
    public void issueCouponToUser(Long userId) {
        redisTemplate.opsForSet().add("coupon:users", userId);
    }
}
