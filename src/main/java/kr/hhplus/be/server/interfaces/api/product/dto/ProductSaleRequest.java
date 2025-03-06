package kr.hhplus.be.server.interfaces.api.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleRequest {
    long id;
    long productId;
    LocalDateTime saleDate;
    int quantitySold;
}
