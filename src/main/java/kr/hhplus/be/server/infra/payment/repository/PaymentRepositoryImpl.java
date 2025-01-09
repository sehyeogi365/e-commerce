package kr.hhplus.be.server.infra.payment.repository;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;

import kr.hhplus.be.server.infra.payment.jparepository.PaymentJpaRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository{

    private final PaymentJpaRepository paymentJpaRepository;

    //결제하기
    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

    //결제내역 조회
    @Override
    public List<Payment> getPaymentList(int userId) {

        List<Payment> payments = paymentJpaRepository.findByUserId(userId);

        if(payments == null){
            throw new IllegalArgumentException("PayHistory Not Found");
        }

        return payments;
    }
}
