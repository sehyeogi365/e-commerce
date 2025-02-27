package kr.hhplus.be.server.domain.order.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name ="order_product")
public class OrderProduct extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="userId")
    private long userId;

    @Column(name="productId")
    private long productId;

    @Column(name="orderId")
    private long orderId;

    @Column(name="quantity")
    private int quantity;

    @Column(name="price")
    private int price;
}
