package kr.hhplus.be.server.infra.order.repository;

import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @Override
    public Order save(int userId, int productId) {

        return save(userId, productId);
    }

    @Override
    public Optional<Order> getOrderList(int userId) {
        return Optional.empty();
    }
}
