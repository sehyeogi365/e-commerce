package kr.hhplus.be.server.interfaces.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private int id;
    private String name;
    private int price;
    private int quantity;
}
