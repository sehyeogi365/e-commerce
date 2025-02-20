package kr.hhplus.be.server.infra.spring.order;


import kr.hhplus.be.server.domain.order.event.OrderEvent;
import kr.hhplus.be.server.domain.order.event.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OrderSpringEventPublisher implements OrderEventPublisher{
    private final OrderEventPublisher eventPublisher;

    @Override
    public void publish(OrderEvent orderEvent) {
        eventPublisher.publish(orderEvent);
        System.out.println("도메인 이벤트 발행됨: " + orderEvent.getOrderId());
    }
}
