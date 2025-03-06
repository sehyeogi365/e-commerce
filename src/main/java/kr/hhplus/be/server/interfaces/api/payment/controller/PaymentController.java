package kr.hhplus.be.server.interfaces.api.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.service.PaymentService;
import kr.hhplus.be.server.interfaces.api.payment.dto.PaymentRequest;
import kr.hhplus.be.server.interfaces.api.payment.dto.PaymentResponse;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;
    @PostMapping("/")
    @Tag(name = "결제")
    @Operation(summary = "결제", description = "상품을 결제 합니다.")
    public ApiResponse<PaymentResponse> payInsert(@RequestBody PaymentRequest paymentRequest){

        Payment payment = Payment.builder()
                .id(paymentRequest.getId())
                .orderId(paymentRequest.getOrderId())
                .build();

        PaymentResponse response = paymentService.addPayment(payment);

        log.info("response " + response);
        return ApiResponse.ok(response);
    }

    // 결제 조회
    @GetMapping("/{userId}")
    @Tag(name = "결제 조회")
    @Operation(summary = "결제 조회", description = "결제 상품을 조회 합니다.")
    public ApiResponse<List<PaymentResponse>> paySelect(@PathVariable("userId") int userId){

        List<PaymentResponse> response = paymentService.getPaymentList(userId);

        log.info("response " + response);
        return ApiResponse.ok(response);
    }

}
