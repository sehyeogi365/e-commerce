package kr.hhplus.be.server.domain.order.outbox;

import kr.hhplus.be.server.domain.order.enums.OutboxStatus;
import kr.hhplus.be.server.domain.order.event.OrderEvent;

import java.util.List;

public interface OrderOutboxRepository {
    List<OrderOutbox> findByStatus(OutboxStatus status);
    OrderOutbox save(OrderOutbox orderOutbox);

    List<OrderOutbox> findAll();
    void delete(OrderOutbox event);
}
