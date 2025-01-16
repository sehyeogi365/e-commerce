package kr.hhplus.be.server.interfaces.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    private long id;
    private int userId;
    private int couponId;
    private int productId;
}
