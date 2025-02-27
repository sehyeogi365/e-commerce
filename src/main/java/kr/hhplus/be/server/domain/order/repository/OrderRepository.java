package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.product.entity.Product;


import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    // 주문하기
    Order save(Order order);

    // 주문 목록 조회
    List<Order> getOrders(long userId);

    // 주문 목록 조회(수량포함)
    List<OrderProduct> getByUserId(long userId);

}
