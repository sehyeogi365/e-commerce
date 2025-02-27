package kr.hhplus.be.server.domain.order.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderEvent {

    private long orderId;
    private String message;

    public OrderEvent(long orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }
}
