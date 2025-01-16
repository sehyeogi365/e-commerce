package kr.hhplus.be.server.infra.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.infra.order.jparepository.OrderJpaRepository;
import kr.hhplus.be.server.interfaces.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;


    //주문하기
    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }

    //상품 한행 정보
    @Override
    public Product findById(int id) {
        return orderJpaRepository.findById(id);
    }

    //주문 목록 조회
    @Override
    public List<Order> getOrders(int userId) {
        return orderJpaRepository.findByUserId(userId);
    }
}
