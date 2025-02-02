package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;


import java.util.List;
import java.util.Optional;

//@Repository //디비 조회가 일어나는 구간이 아니기에 지우기
public interface CouponRepository {

    // 쿠폰 목록 조회
    List<Coupon> getCoupons();

    // 쿠폰 한행 조회
    Coupon getCouponInfo(long id);

    Optional<Coupon> findCouponInfo(long id);

    // 쿠폰 수량 차감
    void deductCoupon(long id);

    // 쿠폰 발급
    UserCoupon issueCoupon(UserCoupon userCoupon);

    // 사용자 쿠폰 목록 조회
    List<UserCoupon> getUserCoupon(int userId);

    // 사용자 쿠폰 한행
    UserCoupon getUserCouponInfo(int userId, int couponId);

    Optional<UserCoupon> findUserCouponInfo(int userId, int couponId);

    // 쿠폰 사용
    void useCoupon(long id);
}
