package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.Optional;

public interface OrderRepository {

    //주문하기
    Order save(Order order);

    //상품 한행 정보
    Optional<Product> findById(int id);

    //주문 목록 조회
    Page<Order> getOrders(int userId, Pageable pageable);
}
