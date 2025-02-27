package kr.hhplus.be.server.interfaces.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.order.event.OrderEvent;

import kr.hhplus.be.server.domain.order.outbox.OrderOutbox;
import kr.hhplus.be.server.domain.order.outbox.OrderOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import static java.rmi.server.LogStream.log;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderOutboxListener {

    private final OrderOutboxRepository orderOutboxRepository;
    private final ObjectMapper objectMapper;
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleOutboxEvent(OrderEvent event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            OrderOutbox outbox = new OrderOutbox(event.getOrderId(), "ORDER_CREATED");// 파라미터가 2갠데 그거에 맞는 생성자가 없어서 그렇습니다
            // OUT BOX 널확인
            if(outbox != null){
                // 로그 확인도 넣기 sout 대신 해보기
                orderOutboxRepository.save(outbox);
                log("Outbox 테이블에 저장됨: " + event.getOrderId());
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON 변환 오류", e);
        }
        // Kafka로 메시지 발행
    }
}
