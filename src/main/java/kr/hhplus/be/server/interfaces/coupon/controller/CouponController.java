package kr.hhplus.be.server.interfaces.coupon.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import kr.hhplus.be.server.interfaces.coupon.dto.CouponRequest;
import kr.hhplus.be.server.interfaces.coupon.dto.CouponResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
@RequestMapping("/api/v1/coupons")
public class CouponController {

    @GetMapping("/")
    @Tag(name = "쿠폰 조회")
    @Operation(summary = "쿠폰 조회", description = "쿠폰을 주문 합니다.")
    public ApiResponse<List<CouponResponse>> couponSelect(@RequestBody CouponRequest couponRequest){

        List<CouponResponse> response = new ArrayList<>();

        response.add(new CouponResponse(1,1));
        response.add(new CouponResponse(1,2));
        response.add(new CouponResponse(1,3));

        log.info("response " +response);
        return ApiResponse.ok(response);
    }

    @PostMapping("/receive")
    @Tag(name = "쿠폰 발급")
    @Operation(summary = "쿠폰 발급", description = "쿠폰을 발급 합니다.")
    public ApiResponse<CouponResponse> couponReceive(@RequestBody CouponRequest couponRequest){

        CouponResponse response = new CouponResponse(1,1);

        log.info("response " +response);
        return ApiResponse.ok(response);
    }
    //사용자 쿠폰 조회
    @PostMapping("/{userId}")
    @Tag(name = "사용자 쿠폰 조회")
    @Operation(summary = "사용자 쿠폰 조회", description = "사용자 쿠폰을 조회합니다.")
    public ApiResponse<List<CouponResponse>> userCouponSelect(@RequestBody CouponRequest couponRequest){

        List<CouponResponse> response = new ArrayList<>();
        response.add(new CouponResponse(1,1));
        response.add(new CouponResponse(1,2));
        response.add(new CouponResponse(1,3));

        log.info("response " +response);
        return ApiResponse.ok(response);
    }

}
