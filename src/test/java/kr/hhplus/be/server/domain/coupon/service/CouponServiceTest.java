package kr.hhplus.be.server.domain.coupon.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.enums.CouponStatus;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;

import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

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
        Coupon coupon = Coupon.builder()
                .id(1)
                .percent(20)
                .expirationDate(expirationDate)
                .quantity(1)
                .build();

        List<Coupon> mockCouponList = List.of(coupon);
        //CouponResponse expectedCoupon = new CouponResponse(1);

        //when
        when(couponRepository.getCoupons()).thenReturn(mockCouponList);
        List<CouponResponse> couponList = couponService.getCouponList();


        //then
        assertThat(couponList).isNotNull();
        assertThat(couponList.size()).isEqualTo(mockCouponList.size());
        //assertThat(expectedCoupon.getId()).isEqualTo(coupon.getId());
        //assertThat(couponService.getCouponList()).isEqualTo(couponList);
        // 첫 번째 쿠폰의 필드 값 검증
        CouponResponse actualCoupon = couponList.get(0);
        assertThat(actualCoupon.getId()).isEqualTo(coupon.getId());
    }

    @Test
    @DisplayName("쿠폰 발급")
    void 쿠폰_발급()throws ParseException{
        //given
        String dateString = "2026-04-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);
        long id = 1L;
        int userId = 1;
        int couponId = 1;
        //쿠폰 자체도 저장
        Coupon coupon = Coupon.builder().
                                id(id)//아이디가 널이면 인서트 낫널이면 업데이트
                                .percent(20)
                                .quantity(1)
                                .expirationDate(expirationDate)
                                .build();

        UserCoupon userCoupon = UserCoupon.builder().
                                        id(id)
                                        .userId(userId)
                                        .couponId(couponId)
                                        .couponStatus(CouponStatus.UNUSED)
                                        .build();

        // 가짜 리포지터리 값 설정

        //when
        when(couponRepository.findCouponInfo(userId)).thenReturn(Optional.of(coupon));
        //when(couponRepository.issueCoupon(userCoupon)).thenReturn(userCoupon);

        UserCouponResponse result = couponService.issueCoupon(userCoupon);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.getCouponId()).isEqualTo(1);
    }

    @Test
    @DisplayName("쿠폰 발급 실패케이스: 수량이없을시")
    void 쿠폰_발급2()throws ParseException{
        //given
        String dateString = "2026-04-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);
        long id = 1L;
        int userId = 1;
        int couponId = 1;
        //쿠폰 자체도 저장
        Coupon coupon = Coupon.builder().
                id(id)//아이디가 널이면 인서트 낫널이면 업데이트
                .percent(20)
                .quantity(0)
                .expirationDate(expirationDate)
                .build();

        UserCoupon userCoupon = UserCoupon.builder().
                id(id)
                .userId(userId)
                .couponId(couponId)
                .couponStatus(CouponStatus.UNUSED)
                .build();

        // 가짜 리포지터리 값 설정
        //when(couponRepository.getUserCoupon(userId)).thenReturn(mockedUserCoupons);
        when(couponRepository.findCouponInfo(userId)).thenReturn(Optional.of(coupon));
        //사용자 쿠폰 목록 조회는 통테에 들어갈 부분 발급을 테스트 하는거니 issueCoupon을
        //issueCoupon 를 호출햇을때 예외를 발생시키는 방식으로
        //전반적인 로직이 탄탄하지 않으니 쿠폰수량 어떤 값을 넣어도 익셉션이 터지는 것
        // when & then
        //assertThat Throw 활용해보기
        assertThatThrownBy(() -> couponService.issueCoupon(userCoupon)) // 예외가 발생해야 함
                .isInstanceOf(CustomException.class) // CustomException 발생 예상
                .hasMessage(ErrorCode.COUPON_QUANTITY_ZERO.getMessage());
    }
//
    @Test
    @DisplayName("쿠폰 발급 실패케이스: 만료기한 초과")
    void 쿠폰_발급3()throws ParseException{
        //given
        String dateString = "2025-01-01";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);
        long id = 1L;
        int userId = 1;
        int couponId = 1;

        //쿠폰 자체도 저장
        Coupon coupon = Coupon.builder().
                id(id)//아이디가 널이면 인서트 낫널이면 업데이트
                .percent(20)
                .quantity(1)
                .expirationDate(expirationDate)
                .build();

        UserCoupon userCoupon = UserCoupon.builder().
                id(id)
                .userId(userId)
                .couponId(couponId)
                .couponStatus(CouponStatus.UNUSED)
                .build();

        when(couponRepository.findCouponInfo(userId)).thenReturn(Optional.of(coupon));

        assertThatThrownBy(() -> couponService.issueCoupon(userCoupon)) // 예외가 발생해야 함
                .isInstanceOf(CustomException.class) // CustomException 발생 예상
                .hasMessage(ErrorCode.COUPON_OVER_DATE.getMessage());
    }

    // 사용자 쿠폰 목록
    @Test
    @DisplayName("사용자 쿠폰 목록 ")
    void 사용자_쿠폰_목록 ()throws ParseException{
        //given
        String dateString = "2026-04-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);
        long id = 1L;
        int userId = 1;
        int couponId = 1;

        //쿠폰 자체도 저장
        Coupon coupon = Coupon.builder().
                id(id)//아이디가 널이면 인서트 낫널이면 업데이트
                .percent(20)
                .quantity(1)
                .expirationDate(expirationDate)
                .build();

        UserCoupon userCoupon = UserCoupon.builder().
                id(id)
                .userId(userId)
                .couponId(couponId)
                .couponStatus(CouponStatus.UNUSED)
                .build();

        List<UserCoupon> mockedUserCoupons = List.of(userCoupon);
        //when
        when(couponRepository.getUserCoupon(userId)).thenReturn(mockedUserCoupons);

        List<UserCouponResponse> result = couponService.getUserCoupon(userId);
        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }
}