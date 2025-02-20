package kr.hhplus.be.server.interfaces.spring;

import kr.hhplus.be.server.domain.order.event.OrderEvent;
import org.springframework.context.event.EventListener;


public class OrderKafkaListener {

    @EventListener
    public void handleKafkaEvent(OrderEvent event) { // 내부 도메인 이벤트 처리
        System.out.println("Spring 내부에서 Kafka 이벤트 처리: " + event.getOrderId());
    }
}
