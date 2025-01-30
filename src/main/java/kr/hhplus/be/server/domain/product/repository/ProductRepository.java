package kr.hhplus.be.server.domain.product.repository;

import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.ProductSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface ProductRepository {

    //상품 조회
    List<Product> getProducts();

    //상품 1행 정보 조회
    Product getById(long id);

    Optional<Product> findById(long id);

    //판매수량 증가
    void productSalesIncrease(int productId);

    //상품 갯수 차감
    void productQuantityDecrease(long id);

    //Top5
    List<ProductSale> getTop5List();

}
