package kr.hhplus.be.server.infra.product.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

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
    Optional<Product> findById(long id);

    //Top5
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM product p where p.id = :id")
    Optional<List<Product>> findTop5();

}
