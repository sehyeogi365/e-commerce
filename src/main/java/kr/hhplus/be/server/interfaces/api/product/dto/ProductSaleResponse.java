package kr.hhplus.be.server.interfaces.api.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductSaleResponse {
    long id;
    long productId;
    Date saleDate;
    int quantitySold;
}
