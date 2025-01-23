package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.interfaces.coupon.dto.CouponResponse;
import kr.hhplus.be.server.interfaces.coupon.dto.UserCouponResponse;
import kr.hhplus.be.server.interfaces.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponRepository couponRepository;//TODO: 생성자 or 롬복 주입 required..
    //쿠폰 목록 조회
    public List<CouponResponse> getCouponList(){//메서드 명칭 변경 or 타입변경

        List<Coupon> couponList = couponRepository.getCoupons();

        List<CouponResponse> response = new ArrayList<>();

        for(Coupon coupon : couponList){
            response.add(new CouponResponse(coupon.getId()));
        }

        return response;
    }

    //쿠폰 발급
    public UserCouponResponse getCoupon(UserCoupon userCoupon){

        LocalDate today = LocalDate.now();

        //쿠폰 한행
        Coupon coupon = new Coupon();
        Coupon optionalCoupon = couponRepository.getCouponInfo(coupon.getId());

        //쿠폰 있는지 없는지 여부 판별
        if(optionalCoupon == null){
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }

        LocalDate expirationDate = optionalCoupon.getExpirationDate().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        if(expirationDate.isBefore(today)){//만료일이 오늘 보다 이전 날짜 라면
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }

        return new UserCouponResponse(userCoupon.getCouponId(), userCoupon.getUserId());
    }

    //사용자 쿠폰 목록 조회
    public List<UserCouponResponse> getUserCoupon(int userId){

        List<UserCoupon> userCouponList = couponRepository.getUserCoupon(userId);

        if(userCouponList.isEmpty()){
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }

        List<UserCouponResponse> response = new ArrayList<>();
        for(UserCoupon userCoupon : userCouponList){
            response.add(new UserCouponResponse(userCoupon.getUserId(), userCoupon.getCouponId()));
        }

        return response;//impl에서 쿠폰 있는지 없는지 로직처리
    }

}
