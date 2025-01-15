package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.List;
import java.util.Optional;



@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;//TODO: 생성자 or 롬복 주입 required..
    //쿠폰 목록 조회
    public List<Coupon> getCouponList(){//메서드 명칭 변경 or 타입변경
        return couponRepository.getCoupons();
    }

    //쿠폰 발급
    public UserCoupon getCoupon(long id, int userId){

        LocalDate today = LocalDate.now();

        //쿠폰 한행
        Optional<Coupon> optionalCoupon = couponRepository.getCouponInfo(id);

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

        return couponRepository.getCoupon(id, userId);
    }

    //사용자 쿠폰 목록 조회
    public List<UserCoupon> getUserCoupon(int userId){
        log.info(String.valueOf(userId));
        return couponRepository.getUserCoupon(userId);//impl에서 쿠폰 있는지 없는지 로직처리
    }

}
