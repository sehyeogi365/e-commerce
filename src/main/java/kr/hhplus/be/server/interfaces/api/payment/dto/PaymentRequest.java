package kr.hhplus.be.server.interfaces.api.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private long id;
    private long orderId;
    private long productId;
    private long couponId;
}
