package kr.hhplus.be.server.interfaces.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.enums.CouponStatus;
import kr.hhplus.be.server.domain.coupon.service.CouponService;

import kr.hhplus.be.server.interfaces.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.interfaces.api.coupon.dto.UserCouponResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CouponService couponService;

    @Test
    @DisplayName("쿠폰 조회")
    void 쿠폰_조회() throws Exception {
        // given
        String dateString = "2025-01-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);

        long id = 1L;

        CouponResponse couponResponse = new CouponResponse(id);

        List<CouponResponse> mockCouponList = List.of(couponResponse);

        when(couponService.getCouponList()).thenReturn(mockCouponList);

        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/coupons/")  // GET 요청 + Path Variable 적용
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));  // List라서 [0] 인덱스 필요
    }

    @Test
    @DisplayName("쿠폰 발급")
    void 쿠폰_발급() throws Exception {
        // given
        String dateString = "2026-04-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);

        long id = 1L;
        long userId = 1L;
        long couponId = 1L;

        Coupon coupon = Coupon.builder()
                                .id(id)//아이디가 널이면 인서트 낫널이면 업데이트
                                .percent(20)
                                .quantity(1)
                                .expirationDate(expirationDate)
                                .build();

        UserCoupon userCoupon = UserCoupon.builder()
                                            .id(id)
                                            .userId(userId)
                                            .couponId(couponId)
                                            .couponStatus(CouponStatus.UNUSED)
                                            .build();

        UserCouponResponse response = new UserCouponResponse(userId, couponId);

        when(couponService.issueCoupon(userCoupon)).thenReturn(response);

        // when&then
        mockMvc.perform(post("/api/v1/coupons/receive")
                        .content(objectMapper.writeValueAsString(userCoupon))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(1L))
                .andExpect(jsonPath("$.data.couponId").value(1L));
    }

    @Test
    @DisplayName("사용자 쿠폰 목록 ")
    void 사용자_쿠폰_목록 () throws Exception {
        // given
        long id = 1L;
        long userId = 1L;
        long couponId = 1L;

        UserCoupon userCoupon = UserCoupon.builder()
                .id(id)
                .userId(userId)
                .couponId(couponId)
                .couponStatus(CouponStatus.UNUSED)
                .build();

        UserCouponResponse userCouponResponse = new UserCouponResponse(userId, couponId);

        List<UserCouponResponse> mockedUserCoupons = List.of(userCouponResponse);
        when(couponService.getUserCoupon(userId)).thenReturn(mockedUserCoupons);

        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/coupons/{userId}", userId)  // GET 요청 + Path Variable 적용
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userId").value(1L))  // List라서 [0] 인덱스 필요
                .andExpect(jsonPath("$.data[0].couponId").value(1L));
    }



}