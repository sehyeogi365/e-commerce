package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;

import java.util.Optional;

public interface CouponRepository {

    //쿠폰 목록 조회
    Optional<Coupon> getCoupon();

    //사용자 쿠폰 목록 조회
    Optional<Coupon> getUserCoupon(int userId);

}
