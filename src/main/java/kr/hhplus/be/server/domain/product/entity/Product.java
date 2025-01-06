package kr.hhplus.be.server.domain.product.entity;

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
public class Product extends BaseTimeEntity {

    private int id;

    private String name;

    private int price;

    private int quantity;

}
