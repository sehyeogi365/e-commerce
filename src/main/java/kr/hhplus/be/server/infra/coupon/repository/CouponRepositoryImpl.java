package kr.hhplus.be.server.infra.coupon.repository;


import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CouponRepositoryImpl implements CouponRepository {

    //쿠폰 목록 조회
    @Override
    public Optional<Coupon> getCouponList() {
        return Optional.empty();
    }

    //쿠폰 발급
    @Override
    public Coupon getCoupon(int userId) {
        return getCoupon(userId);
    }

    //사용자 쿠폰 목록 조회
    @Override
    public Optional<Coupon> getUserCoupon(int userId) {

        return getUserCoupon(userId);
    }






}
