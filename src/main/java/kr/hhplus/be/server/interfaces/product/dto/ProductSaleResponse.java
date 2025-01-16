package kr.hhplus.be.server.interfaces.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleResponse {
    long id;
    int productId;
    LocalDateTime saleDate;
    int quantitySold;
}
