package kr.hhplus.be.server.interfaces.order.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderResponse {
    private int id;
    private int userId;
    private int couponId;
    private int productId;
}
