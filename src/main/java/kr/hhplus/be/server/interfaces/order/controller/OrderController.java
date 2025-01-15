package kr.hhplus.be.server.interfaces.order.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import kr.hhplus.be.server.interfaces.order.dto.OrderRequest;
import kr.hhplus.be.server.interfaces.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {


    @PostMapping("/")
    @Tag(name = "주문")
    @Operation(summary = "주문", description = "상품을 주문 합니다.")
    public ApiResponse<OrderResponse> orderInsert(@RequestBody OrderRequest orderRequest){

        OrderResponse response = new OrderResponse();
        log.info("response " +response);
        return ApiResponse.ok(response);
    }

    @GetMapping("/{userId}")
    @Tag(name = "주문 조회")
    @Operation(summary = "상품 조회", description = "주문 상품을 조회 합니다.")
    public ApiResponse<OrderResponse> orderSelect(@RequestBody OrderRequest orderRequest){

        OrderResponse response = new OrderResponse();
        log.info("response " +response);
        return ApiResponse.ok(response);
    }
}
