package kr.hhplus.be.server.interfaces.api.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponResponse {
    private long userId;
    private long couponId;
}
