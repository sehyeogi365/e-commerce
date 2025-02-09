package kr.hhplus.be.server.domain.coupon.repository;

public interface RedisCouponRepository {
    void addCouponRequest(Long couponId, Long userId);
    Long decrementCouponCount(Long couponId);
    void rollbackCouponCount(Long couponId);
    boolean isCouponAlreadyIssued(Long userId, Long couponId);
    void issueCouponToUser(Long userId, Long couponId);
}
