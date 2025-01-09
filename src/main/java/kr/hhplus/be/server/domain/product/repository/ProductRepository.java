package kr.hhplus.be.server.domain.product.repository;

import kr.hhplus.be.server.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface ProductRepository {

    //상품 조회
    Page<Product> getProducts(Pageable pageable);

    //상품 1행 정보 조회
    Optional<Product> findById(long id);

    //Top5
    Optional<List<Product>> getTop5List();

}
