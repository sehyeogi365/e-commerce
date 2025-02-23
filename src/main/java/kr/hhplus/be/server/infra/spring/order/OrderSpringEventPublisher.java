package kr.hhplus.be.server.infra.spring.order;

import kr.hhplus.be.server.domain.order.event.OrderEvent;
import kr.hhplus.be.server.domain.order.event.OrderEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static java.rmi.server.LogStream.log;


@Component
@Slf4j
@RequiredArgsConstructor
public class OrderSpringEventPublisher implements OrderEventPublisher{// applicationevntpublisher 사용

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(OrderEvent orderEvent) {
        applicationEventPublisher.publishEvent(orderEvent);
        log("도메인 이벤트 발행됨: " + orderEvent.getOrderId());
    }
}
