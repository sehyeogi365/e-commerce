package kr.hhplus.be.server.domain.product.repository;

import kr.hhplus.be.server.domain.product.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository {

    //상품 조회
    Optional<Product> getProductList(Product product);

    //Top5
    Optional<Product> getTop5List(Product product);

}
