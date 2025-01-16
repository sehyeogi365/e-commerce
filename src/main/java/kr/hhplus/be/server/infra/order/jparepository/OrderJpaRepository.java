package kr.hhplus.be.server.infra.order.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<Order, Long> {

    //주문하기
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Order save(Order order);

    //상품 한행 정보
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Product p WHERE p.id = :id")
    Product findById(@Param("id") long id);

    //주문 목록 조회
    List<Order> findByUserId(@Param("userId") int userId);

}
