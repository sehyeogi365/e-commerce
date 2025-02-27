package kr.hhplus.be.server.infra.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.infra.order.jparepository.OrderJpaRepository;
import kr.hhplus.be.server.infra.order.jparepository.OrderProductJpaRepository;
import kr.hhplus.be.server.interfaces.order.dto.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderProductJpaRepository orderProductJpaRepository;

    //주문하기
    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(order);
    }


    @Override
    public List<Order> getOrders(long userId) {
        return orderJpaRepository.getByUserId(userId);
    }

    @Override
    public List<OrderProduct> getByUserId(long userId) {
        return orderProductJpaRepository.getByUserId(userId);
    }
}
