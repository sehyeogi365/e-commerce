package kr.hhplus.be.server.interfaces.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import kr.hhplus.be.server.interfaces.order.dto.OrderRequest;
import kr.hhplus.be.server.interfaces.order.dto.OrderResponse;
import kr.hhplus.be.server.interfaces.payment.dto.PaymentRequest;
import kr.hhplus.be.server.interfaces.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
@RequestMapping("/api/v1/payment")
public class PaymentController {

    @PostMapping("/")
    @Tag(name = "결제")
    @Operation(summary = "결제", description = "상품을 결제 합니다.")
    public ApiResponse<PaymentResponse> payInsert(@RequestBody PaymentRequest paymentRequest){

        PaymentResponse response = new PaymentResponse(1,1);
        log.info("response " +response);
        return ApiResponse.ok(response);
    }

    //결제 조회
    @GetMapping("/{userId}")
    @Tag(name = "결제 조회")
    @Operation(summary = "결제 조회", description = "결제 상품을 조회 합니다.")
    public ApiResponse<List<PaymentResponse>> paySelect(@RequestBody PaymentRequest paymentRequest){

        List<PaymentResponse> response = new ArrayList<>();

        response.add(new PaymentResponse(1,1));
        response.add(new PaymentResponse(2,2));
        response.add(new PaymentResponse(3,3));

        log.info("response " +response);
        return ApiResponse.ok(response);
    }

}
