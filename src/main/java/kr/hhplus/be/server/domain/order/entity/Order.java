package kr.hhplus.be.server.domain.order.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseTimeEntity;
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

    @Column(name="statement")
    private String statement;

    @Column(name="originPrice")
    private int originPrice;

    @Column(name="discountPrice")
    private int discountPrice;
}
