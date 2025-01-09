package kr.hhplus.be.server.infra.product.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.ProductSale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductJpaRepository extends JpaRepository<Product, Long> {

    //상품 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM product p ")
    Page<Product> findAll(Pageable pageable);

    //상품 1행정보 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM product p where p.id = :id")
    Optional<Product> findById(@Param("id") long id);

    //판매 수량 증가
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Query("UPDATE product_sales p SET p.quantitySold = p.quantitySold+1 where p.productId = :productId")
    void productSaleIncrease(@Param("productId") int productId);

    //상품 갯수 차감
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Modifying
    @Query("UPDATE product p SET p.quantity = p.quantity-1 where p.id = :id")
    void productQuantityDecrease(@Param("id") long id);

    //Top5
    @Query(value= "SELECT p FROM product_sales p WHERE p.salesDate >= CURRENT_DATE - 3 ORDER BY DESC quantitySold LIMIT 5", nativeQuery = true)
    List<ProductSale> findTop5();

}
