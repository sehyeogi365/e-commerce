package kr.hhplus.be.server.domain.coupon.repository;

public interface RedisCouponRepository {


    void cacheCouponInfo(long couponId, String info);
    String getCouponInfo(long couponId);

    void addCouponRequest(Long couponId, Long userId);
    Integer couponCount(Long couponId);

    String expirationDateStr(Long couponId);
    void couponExpiration(Long couponId);
    Long decrementCouponCount(Long couponId);
    void rollbackCouponCount(Long couponId);
    boolean isCouponAlreadyIssued(Long userId, Long couponId);
    void issueCouponToUser(Long userId, Long couponId);
}
