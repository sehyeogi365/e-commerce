package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.api.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;

    // 주문하기
    @Transactional
    public OrderResponse orderProduct(Order order){
        // 상품 수량 확인후 주문 신청
        Product product = productRepository.findById(order.getProductId()).orElseThrow(() -> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        OrderResponse response = new OrderResponse(order.getId(), order.getUserId(), order.getCouponId(), order.getProductId());

        if(product.getQuantity() <= 0){
            throw new CustomException(ErrorCode.ITEM_QUANTITY_ZERO);
        }

        // 주문에 쿠폰 적용


        return response;
    }

    // 주문 목록 조회
    public List <OrderResponse> getOrderList(long userId){
        List<Order> orderList = orderRepository.getOrders(userId);

        if(orderList.isEmpty()){
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }
        List<OrderResponse> response = new ArrayList<>();

        for(Order order: orderList){
            response.add(new OrderResponse(order.getId(), order.getUserId(),order.getCouponId(), order.getProductId()));
        }

        return response;
    }
}
