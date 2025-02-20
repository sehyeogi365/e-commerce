package kr.hhplus.be.server.domain.order.outbox;

import java.util.List;

public interface OrderOutboxRepository {
    OrderOutbox save(OrderOutbox orderOutbox);

    List<OrderOutbox> findAll();
    void delete(OrderOutbox event);
}
