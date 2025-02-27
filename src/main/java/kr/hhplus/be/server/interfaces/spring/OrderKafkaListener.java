package kr.hhplus.be.server.interfaces.spring;

import kr.hhplus.be.server.domain.order.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import static java.rmi.server.LogStream.log;

@Slf4j
public class OrderKafkaListener {

    @EventListener
    public void handleKafkaEvent(OrderEvent event) { // 내부 도메인 이벤트 처리
        log("Spring 내부에서 Kafka 이벤트 처리: " + event.getOrderId());
    }
}
