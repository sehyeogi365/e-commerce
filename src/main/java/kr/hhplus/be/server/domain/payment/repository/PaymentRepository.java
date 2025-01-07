package kr.hhplus.be.server.domain.payment.repository;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository {

    //결제하기
    Payment save(int userId, int orderId);

    //결제 내역 조회
    Optional<Payment> getPaymentList(int userId);
}
