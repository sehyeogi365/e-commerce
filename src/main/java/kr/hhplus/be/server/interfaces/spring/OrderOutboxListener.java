package kr.hhplus.be.server.interfaces.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.order.event.OrderEvent;

import kr.hhplus.be.server.domain.order.outbox.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class OrderOutboxListener {

    private final OrderOutboxRepository orderOutboxRepository;
    private final ObjectMapper objectMapper;
    @TransactionalEventListener
    public void handleOutboxEvent(OrderEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            OrderOutbox outbox = new OrderOutbox(event.getOrderId(), "ORDER_CREATED");// 파라미터가 2갠데 그거에 맞는 생성자가 없어서 그렇습니다
            orderOutboxRepository.save(outbox);
            System.out.println("Outbox 테이블에 저장됨: " + event.getOrderId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 오류", e);
        }
        // Kafka로 메시지 발행
    }
}
