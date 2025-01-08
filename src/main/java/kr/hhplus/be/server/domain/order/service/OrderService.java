package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    //주문하기
    @Transactional
    public Order orderProduct(Order order){

        //상품 수량 확인후 주문 신청
        Optional<Product> productQuantity = orderRepository.findById(order.getProductId());

        try{
            return orderRepository.save(order);
        }catch (IllegalArgumentException e){
            throw new IllegalStateException("No Product Remain");
        }

    }

    //주문 목록 조회
    public Page<Order> getOrderList(int userId, Pageable pageable){

        try {
            return orderRepository.getOrders(userId, pageable);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("No orders found for user ID: " + userId, e);
        }

    }

}
