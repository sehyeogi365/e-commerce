package kr.hhplus.be.server.interfaces.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleResponse {
    long id;
    int productId;
    Date saleDate;
    int quantitySold;
}
