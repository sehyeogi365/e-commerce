package kr.hhplus.be.server.domain.payment.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseTimeEntity;
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
    private int userId;

    @Column(name="orderId")
    private int orderId;

    @Column(name="productId")
    private int productId;

    @Column(name="couponId")
    private int couponId;

    @Column(name="statement")
    private String statement;

    @Column(name="originPrice")
    private int originPrice;

    @Column(name="discountPrice")
    private int discountPrice;
}
