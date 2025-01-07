package kr.hhplus.be.server.domain.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository {

    //주문하기
    Order save(int userId, int productId);

    //주문 목록 조회
    Optional<Order> getOrderList(int userId);
}
