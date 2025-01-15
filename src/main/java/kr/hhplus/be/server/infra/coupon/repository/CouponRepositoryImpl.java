package kr.hhplus.be.server.infra.coupon.repository;


import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.infra.coupon.jparepository.CouponJpaRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    //쿠폰 목록 조회

    @Override
    public List<Coupon> getCoupons() {
        return  couponJpaRepository.findAll();
    }

    //쿠폰 한행 조회
    @Override
    public Optional<Coupon> getCouponInfo(long id){
        return couponJpaRepository.findById(id);
    }

    //쿠폰 사용
    @Override
    public void useCoupon(long id){
        couponJpaRepository.useCoupon(id);
    }

    //쿠폰 발급
    @Override
    public UserCoupon getCoupon(long id, int userId) {

        if(id <= 0){
            throw new IllegalArgumentException("No coupons found");
        }
        if(userId <= 0){
            throw new IllegalArgumentException("No users found");
        }

        return couponJpaRepository.save(id, userId);
    }

    //사용자 쿠폰 목록 조회
    @Override
    public List<UserCoupon> getUserCoupon(int userId) {

        return Optional.ofNullable(couponJpaRepository.findByUserId(userId))
                .orElseThrow(() -> new IllegalArgumentException("Coupons not found"));
    }

}
