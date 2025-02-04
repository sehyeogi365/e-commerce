package kr.hhplus.be.server.interfaces.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private long id;
    private long userId;
    private long couponId;
    private long productId;
}
