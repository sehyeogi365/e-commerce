package kr.hhplus.be.server.domain.order.outbox;

import kr.hhplus.be.server.domain.order.event.OrderEvent;
import kr.hhplus.be.server.domain.order.event.OrderEventPublisher;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import kr.hhplus.be.server.domain.point.entity.Point;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderOutboxService {

    private final OrderOutboxRepository orderOutboxRepository;
    private final PointRepository pointRepository;
    private final PaymentRepository paymentRepository;

    @Lazy
    private final OrderEventPublisher eventPublisher;

    @Transactional
    public void pay(OrderEvent orderEvent, Point point) {
        pointRepository.usePoint(point.getUserId(), point.getPoint());// 유저 포인트 차감
                        // 주문 상태 변경 (없음)
        paymentRepository.save(new Payment()); // 결제 정보 저장

        eventPublisher.publish(orderEvent);
    }

    @Transactional
    public void saveOrderOutboxAndPublishEvent(String message_id, long orderId, String payload) {
        // Outbox 테이블에 저장
        OrderOutbox orderOutbox = new OrderOutbox();
        orderOutboxRepository.save(orderOutbox);

        // 이벤트 발행
        OrderEvent orderEvent = new OrderEvent(orderId, "주문이 완료되었습니다!");
        eventPublisher.publish(orderEvent);

        log.info("주문 완료 - Outbox 저장 및 이벤트 발행: {}", orderId);
    }
}
