package kr.hhplus.be.server.infra.kafka.order;

import kr.hhplus.be.server.domain.order.event.OrderEvent;
import kr.hhplus.be.server.domain.order.event.OrderEventPublisher;
import kr.hhplus.be.server.domain.order.outbox.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

import static java.rmi.server.LogStream.log;

@Component // KafkaProducer 구현체는 굳이 없어도 됨
@Slf4j
@RequiredArgsConstructor
public class OrderKafkaEventPublisher {// 바깥 (카프카) 에 메세지를 프로듀스 하는 친구 KafkaTemplate<String, Object> 주입 받음.
    private final KafkaTemplate<String, Object> kafkaTemplate;// 따로 프로듀서 구현체 없이 이렇게 하기
    private final OrderEventPublisher eventPublisher;
    private final OrderOutboxRepository orderOutboxRepository;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendOrderEvent(OrderEvent event) {
        String message = "주문 ID: " + event.getOrderId() + ", 상품명: " + event.getMessage();
        kafkaTemplate.send("order-topic", message);
        log("Kafka에 메시지 전송: " + message);
    }

    @Scheduled(fixedDelay = 5000) // 5초마다 실행
    @Transactional
    public void publishOutboxEvents() {
        List<OrderOutbox> outboxEvents = orderOutboxRepository.findAll();
        for (OrderOutbox event : outboxEvents) {
            kafkaTemplate.send("order-topic", event.getPayload());
            orderOutboxRepository.delete(event); // 전송 후 삭제
            log("Kafka로 메시지 전송됨: " + event.getMessage_id());
        }
    }
}
