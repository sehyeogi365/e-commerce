package kr.hhplus.be.server.infra.order.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    //주문하기
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Order save(Order order);

    //상품 한행 정보
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM product p WHERE p.id = :id")
    Optional<Product> findById(@Param("id") long id);

    //주문 목록 조회
    Page<Order> findByUserId(@Param("userId") int userId, Pageable pageable);

}
