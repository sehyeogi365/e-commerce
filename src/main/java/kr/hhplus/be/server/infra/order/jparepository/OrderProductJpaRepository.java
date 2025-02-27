package kr.hhplus.be.server.infra.order.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderProductJpaRepository extends JpaRepository<OrderProduct, Long> {


    // 주문 목록 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<OrderProduct> getByUserId(@Param("userId") long userId);
}
