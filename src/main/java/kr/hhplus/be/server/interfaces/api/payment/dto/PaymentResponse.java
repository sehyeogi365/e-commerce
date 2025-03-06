package kr.hhplus.be.server.interfaces.api.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse {
    private long id;
    private long orderId;
}
