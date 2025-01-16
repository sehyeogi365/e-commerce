package kr.hhplus.be.server.interfaces.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCouponResponse {
    private int userId;
    private int couponId;
}
