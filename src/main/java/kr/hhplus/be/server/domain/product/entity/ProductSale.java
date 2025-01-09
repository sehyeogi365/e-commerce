package kr.hhplus.be.server.domain.product.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="product_sales")
public class ProductSale {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(name="productId")
    int productId;

    @Column(name="saleDate")
    LocalDateTime saleDate;

    @Column(name="quantitySold")
    int quantitySold;

}
