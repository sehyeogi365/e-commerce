package kr.hhplus.be.server.infra.kafka.order.outbox.jparepository;

import kr.hhplus.be.server.domain.order.outbox.OrderOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderOutboxJpaRepository extends JpaRepository<OrderOutbox, String> {

    // 발행

    // 조회
    @Query("SELECT o FROM OrderOutbox o")
    List<OrderOutbox> findAll();

    // 삭제
    void delete(OrderOutbox event);


}
