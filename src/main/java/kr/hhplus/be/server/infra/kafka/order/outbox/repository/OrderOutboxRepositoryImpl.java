package kr.hhplus.be.server.infra.kafka.order.outbox.repository;

import kr.hhplus.be.server.domain.order.outbox.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.OrderOutboxRepository;
import kr.hhplus.be.server.infra.kafka.order.outbox.jparepository.OrderOutboxJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class OrderOutboxRepositoryImpl implements OrderOutboxRepository {

    private final OrderOutboxJpaRepository orderOutboxJpaRepository;


    @Override
    public OrderOutbox save(OrderOutbox orderOutbox) {
        return orderOutboxJpaRepository.save(orderOutbox);
    }

    @Override
    public List<OrderOutbox> findAll() {
        return orderOutboxJpaRepository.findAll();
    }

    @Override
    public void delete(OrderOutbox event) {
        orderOutboxJpaRepository.delete(event);
    }

}
