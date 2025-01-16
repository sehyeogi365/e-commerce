package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.interfaces.order.dto.OrderResponse;
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

    //주문하기
    @Transactional
    public OrderResponse orderProduct(Order order){

        //상품 수량 확인후 주문 신청
        Product product = orderRepository.findById(order.getProductId());

        OrderResponse response = new OrderResponse(order.getId(), order.getUserId(), order.getCouponId(), order.getProductId());

        if (product == null) {
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        try{
          return response;
        }catch (Exception e){
            log.error("errormessage"+ e);
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }
    }

    //주문 목록 조회
    public List <OrderResponse> getOrderList(int userId){

        List<Order> orderList = orderRepository.getOrders(userId);

        List<OrderResponse> response = new ArrayList<>();

        for(Order order: orderList){
            response.add(new OrderResponse(order.getId(), order.getUserId(),order.getCouponId(), order.getProductId()));
        }

        try{
            return response;
        }catch (IllegalArgumentException e) {
            throw new IllegalStateException("No orders found for user ID: " + userId, e);
        }
    }

}
