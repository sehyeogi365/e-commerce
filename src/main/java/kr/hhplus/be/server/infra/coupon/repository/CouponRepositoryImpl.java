package kr.hhplus.be.server.infra.coupon.repository;


import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;

import kr.hhplus.be.server.infra.coupon.jparepository.CouponJpaRepository;
import kr.hhplus.be.server.infra.coupon.jparepository.UserCouponJpaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;
    private final UserCouponJpaRepository userCouponJpaRepository;

    //쿠폰 목록 조회

    @Override
    public List<Coupon> getCoupons() {
        return  couponJpaRepository.findAll();
    }

    //쿠폰 한행 조회
    @Override
    public Coupon getCouponInfo(long id){
        return couponJpaRepository.findById(id);
    }

    //쿠폰 수량 차감
    @Override
    public void deductCoupon(long id){
        couponJpaRepository.deductCoupon(id);
    }

    //쿠폰 발급
    @Override
    public UserCoupon issueCoupon(UserCoupon userCoupon) {
        return userCouponJpaRepository.save(userCoupon);
    }

    //사용자 쿠폰 목록 조회
    @Override
    public List<UserCoupon> getUserCoupon(int userId) {
        return userCouponJpaRepository.findByUserId(userId);
//        return Optional.ofNullable(userCouponJpaRepository.findByUserId(userId))
//                .orElseThrow(() -> new IllegalArgumentException("Coupons not found"));
    }

    //쿠폰 사용
    @Override
    public void useCoupon(long id) {
        userCouponJpaRepository.useCoupon(id);
    }

}
