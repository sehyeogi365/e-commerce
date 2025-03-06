package kr.hhplus.be.server.interfaces.api.order.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import kr.hhplus.be.server.interfaces.api.order.dto.OrderRequest;
import kr.hhplus.be.server.interfaces.api.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/")
    @Tag(name = "주문")
    @Operation(summary = "주문", description = "상품을 주문 합니다.")
    public ApiResponse<OrderResponse> orderInsert(@RequestBody OrderRequest orderRequest){

        Order order = Order.builder()
                .id(orderRequest.getId())
                .userId(orderRequest.getUserId())
                .couponId(orderRequest.getCouponId())
                .productId(orderRequest.getProductId())
                .build();
        // 주문 처리order
        OrderResponse response = orderService.orderProduct(order);

        log.info("response " +response);
        return ApiResponse.ok(response);
    }

    @GetMapping("/{userId}")
    @Tag(name = "주문 조회")
    @Operation(summary = "주문 조회", description = "주문 상품을 조회 합니다.")
    public ApiResponse<List<OrderResponse>> orderSelect(@PathVariable("userId") int userId){

        List<OrderResponse> response = orderService.getOrderList(userId);

        log.info("response " +response);
        return ApiResponse.ok(response);
    }
}
