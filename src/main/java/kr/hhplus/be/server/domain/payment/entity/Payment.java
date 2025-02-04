package kr.hhplus.be.server.domain.payment.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseTimeEntity;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatus;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="payment")
public class Payment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="userId")
    private long userId;

    @Column(name="orderId")
    private long orderId;

    @Column(name="productId")
    private long productId;

    @Column(name="couponId")
    private long couponId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name="originPrice")
    private int originPrice;

    @Column(name="discountPrice")
    private int discountPrice;
}
