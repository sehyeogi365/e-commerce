package kr.hhplus.be.server.domain.order.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseTimeEntity;
import kr.hhplus.be.server.domain.order.enums.OrderStatus;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatus;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="order_history")
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="userId")
    private int userId;

    @Column(name="productId")
    private int productId;

    @Column(name="couponId")
    private int couponId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name="originPrice")
    private int originPrice;

    @Column(name="discountPrice")
    private int discountPrice;
}
