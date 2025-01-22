package kr.hhplus.be.server.infra.payment.jparepository;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    //결제하기
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Payment save(Payment payment);

    //OriginPrice 계산
//    Integer originPrice(int quantity, int price);
//
//
//    //DisCountPrice 계산
//    Integer discountPrice(int originPrice, int percent);

    //결제내역 조회
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Payment> findByUserId(int userId);
}
