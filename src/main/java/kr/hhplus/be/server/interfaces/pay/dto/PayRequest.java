package kr.hhplus.be.server.interfaces.pay.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PayRequest {
    private int id;
    private int orderId;
}
