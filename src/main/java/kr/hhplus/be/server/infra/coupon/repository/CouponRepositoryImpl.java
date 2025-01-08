package kr.hhplus.be.server.infra.coupon.repository;


import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.infra.coupon.jparepository.CouponJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class CouponRepositoryImpl implements CouponRepository {

    private final CouponJpaRepository couponJpaRepository;

    public CouponRepositoryImpl(CouponJpaRepository couponJpaRepository) {
        this.couponJpaRepository = couponJpaRepository;
    }

    //쿠폰 목록 조회
    @Override
    public Page<Coupon> getCoupons(Pageable pageable) {

        Page<Coupon> coupons = couponJpaRepository.findAll(pageable);

        if(coupons == null){
            throw new IllegalArgumentException("No coupons found");
        }

        return coupons;
    }

    //쿠폰 한행 조회
    @Override
    public Optional<Coupon> getCouponInfo(int id){

        return couponJpaRepository.findById(id);
    }

    //쿠폰 상태 변경

    //쿠폰 발급
    @Override
    public Coupon getCoupon(Coupon coupon) {

        if(coupon == null){
            throw new IllegalArgumentException("No coupons found");
        }

        return couponJpaRepository.save(coupon);
    }

    //사용자 쿠폰 목록 조회
    @Override
    public Optional<List<Coupon>> getUserCoupon(int userId) {

        return Optional.ofNullable(couponJpaRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Coupons not found")));
    }

}
