package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository {

    //쿠폰 목록 조회
    Optional<Coupon> getCouponList();

    //쿠폰 발급
    Coupon getCoupon(int userId);

    //사용자 쿠폰 목록 조회
    Optional<Coupon> getUserCoupon(int userId);

}
