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

@Table(name ="order_history")
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private int productId;

    private int couponId;

    private String statement;

    private int originPrice;

    private int discountPrice;

}
