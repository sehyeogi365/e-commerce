package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.enums.CouponStatus;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;

import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.interfaces.coupon.dto.CouponResponse;
import kr.hhplus.be.server.interfaces.coupon.dto.UserCouponResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;


    @Test
    @DisplayName("쿠폰 목록 조회")
    void 쿠폰_목록_조회() throws ParseException {
        //given
        String dateString = "2025-01-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);
        Coupon coupon = Coupon.builder().id(1).percent(20).expirationDate(expirationDate).quantity(1).build();

        List<CouponResponse> coupnList = couponService.getCouponList();

        //when
        when(coupnList).thenReturn(couponService.getCouponList());

        //then
        assertThat(couponService.getCouponList()).isEqualTo(coupnList);
    }

    @Test
    @DisplayName("쿠폰 발급")
    void 쿠폰_발급()throws ParseException{
        //given
        String dateString = "2025-01-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);
        long id = 1L;
        int userId = 1;
        int couponId = 1;

        UserCoupon userCoupon = UserCoupon.builder().
                                        id(id).userId(userId).couponId(couponId).couponStatus(CouponStatus.USED).build();
        //when
        couponService.getCoupon(userCoupon);
        when(couponRepository.getCoupon(userCoupon)).thenReturn(userCoupon);
        List<UserCouponResponse> result = couponService.getUserCoupon(userId);
        //then
        assertThat(result.get(0).getCouponId()).isEqualTo(couponId);
        assertThat(result.get(0).getUserId()).isEqualTo(userId);
    }

//    @Test
//    @DisplayName("쿠폰 발급 실패케이스: 수량이없을시")
//    void 쿠폰_발급2(){
//
//
//    }
//
//    @Test
//    @DisplayName("쿠폰 발급 실패케이스: 수량이없을시")
//    void 쿠폰_발급2(){
//
//
//    }


//    @Test
//    @DisplayName("사용자 쿠폰 목록 조회")
//    void 사용자_쿠폰_목록_조회(){
//
//
//    }


}