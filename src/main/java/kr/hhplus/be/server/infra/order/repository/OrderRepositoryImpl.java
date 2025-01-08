package kr.hhplus.be.server.infra.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.infra.order.jparepository.OrderJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    //주문하기
    @Override
    public Order save(Order order) {

        return orderJpaRepository.save(order);
    }

    //상품 한행 정보
    @Override
    public Optional<Product> findById(int id) {

        return orderJpaRepository.findById(id);
    }

    //주문 목록 조회
    @Override
    public Page<Order> getOrders(int userId, Pageable pageable) {

        Page<Order> orders = orderJpaRepository.findByUserId(userId, pageable);

        if(orders.isEmpty()){

            throw new IllegalArgumentException("Order not found");
        }
        return orders;
    }
}
