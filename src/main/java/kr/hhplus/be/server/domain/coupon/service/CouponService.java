package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;//TODO: 생성자 or 롬복 주입 required..

    //쿠폰 목록 조회
    @Transactional
    public Page<Coupon> getCouponList(Pageable pageable){//메서드 명칭 변경 or 타입변경

        Page<Coupon> coupons = couponRepository.getCoupons(pageable);

        try {
            return couponRepository.getCoupons(pageable);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("No Coupon");
        }
    }

    //쿠폰 발급
    @Transactional
    public Coupon getCoupon(Coupon coupon){

        try {
            LocalDate today = LocalDate.now();

            //쿠폰 한행
            Optional<Coupon> optionalCoupon = couponRepository.getCouponInfo(coupon.getId());

            //쿠폰 있는지 없는지 여부 판별
            if(optionalCoupon.isEmpty()){
                throw new IllegalStateException("No coupons found");
            }
            Coupon existingCoupon = optionalCoupon.get();

            LocalDate expirationDate = existingCoupon.getExpirationDate().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();

            if(expirationDate.isBefore(today)){//만료일이 오늘 보다 이전 날짜 라면
                throw new IllegalStateException("Over Expiration Date");
            }
            return couponRepository.getCoupon(coupon);
        } catch (IllegalStateException e){
            System.out.println("Error: " + e.getMessage());
            return null; // 또는 적절한 기본값 반환
        } catch (Exception e){
            System.out.println("Unexpected error: " + e.getMessage());
            return null;
        }
    }

    //사용자 쿠폰 목록 조회
    public Optional<List<Coupon>> getUserCoupon(int userId){
        return couponRepository.getUserCoupon(userId);//impl에서 쿠폰 있는지 없는지 로직처리
    }

}
