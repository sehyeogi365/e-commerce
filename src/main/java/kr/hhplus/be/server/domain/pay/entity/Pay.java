package kr.hhplus.be.server.domain.pay.entity;

import jakarta.persistence.Entity;
import kr.hhplus.be.server.domain.common.BaseTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pay extends BaseTimeEntity {

    private int id;

    private int orderId;

    private String statement;

    private int originPrice;

    private int discountPrice;
}
