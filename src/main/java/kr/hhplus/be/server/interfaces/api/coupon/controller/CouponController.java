package kr.hhplus.be.server.interfaces.api.coupon.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.service.CouponService;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import kr.hhplus.be.server.interfaces.api.coupon.dto.CouponResponse;
import kr.hhplus.be.server.interfaces.api.coupon.dto.UserCouponResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/")
    @Tag(name = "쿠폰 조회")
    @Operation(summary = "쿠폰 조회", description = "쿠폰을 주문 합니다.")
    public ApiResponse<List<CouponResponse>> couponSelect(){

        List<CouponResponse> response = couponService.getCouponList();

        log.info("response " +response);
        return ApiResponse.ok(response);
    }

    @PostMapping("/receive")
    @Tag(name = "쿠폰 발급")
    @Operation(summary = "쿠폰 발급", description = "쿠폰을 발급 합니다.")
    public ApiResponse<UserCouponResponse> couponReceive(UserCoupon userCoupon){

        UserCouponResponse response = couponService.issueCoupon(userCoupon);

        log.info("response " +response);
        return ApiResponse.ok(response);
    }
    //사용자 쿠폰 조회
    @GetMapping("/{userId}")
    @Tag(name = "사용자 쿠폰 조회")
    @Operation(summary = "사용자 쿠폰 조회", description = "사용자 쿠폰을 조회합니다.")
    public ApiResponse<List<UserCouponResponse>> userCouponSelect(@PathVariable("userId") int userId){

        List<UserCouponResponse> response = couponService.getUserCoupon(userId);

        log.info("response " +response);
        return ApiResponse.ok(response);
    }

}
