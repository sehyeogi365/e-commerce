package kr.hhplus.be.server.domain.coupon.usecase;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    //쿠폰 목록 조회
    @Transactional
    Optional<Coupon> getCoupon(){

        return couponRepository.getCoupon();
    }

    //사용자 쿠폰 목록 조회
    Optional<Coupon> getUserCoupon(int userId){

        return couponRepository.getUserCoupon(userId);
    }


}
