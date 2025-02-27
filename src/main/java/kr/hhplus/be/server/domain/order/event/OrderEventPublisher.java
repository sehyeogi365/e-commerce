package kr.hhplus.be.server.domain.order.event;

public interface OrderEventPublisher {// 이벤트 발행 인터페이스
    void publish(OrderEvent orderEvent);
}
