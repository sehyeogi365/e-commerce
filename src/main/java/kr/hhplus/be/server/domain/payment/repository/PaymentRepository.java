package kr.hhplus.be.server.domain.payment.repository;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository {

    //결제하기
    Payment save(Payment payment);

    //결제 내역 조회
    Page<Payment> getPaymentList(int userId, Pageable pageable);
}
