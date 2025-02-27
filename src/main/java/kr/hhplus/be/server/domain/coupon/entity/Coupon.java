package kr.hhplus.be.server.domain.coupon.entity;

import jakarta.persistence.*;
import kr.hhplus.be.server.domain.common.BaseTimeEntity;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="coupon")
public class Coupon extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="percent")
    private int percent;

    @Column(name="quantity")
    private int quantity;

    @Column(name="expirationDate")
    private Date expirationDate;

}
