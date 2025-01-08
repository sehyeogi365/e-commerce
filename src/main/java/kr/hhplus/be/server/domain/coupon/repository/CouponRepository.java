package kr.hhplus.be.server.domain.coupon.repository;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository {

    //쿠폰 목록 조회
    Page<Coupon> getCoupons(Pageable pageable);

    //쿠폰 한행 조회
    Optional<Coupon> getCouponInfo(int id);

    //쿠폰 발급
    Coupon getCoupon(Coupon coupon);

    //사용자 쿠폰 목록 조회
    Optional<List<Coupon>> getUserCoupon(int userId);

}
