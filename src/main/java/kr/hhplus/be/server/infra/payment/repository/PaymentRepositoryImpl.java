package kr.hhplus.be.server.infra.payment.repository;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.infra.payment.jparepository.PaymentJpaRepository;
import kr.hhplus.be.server.infra.product.jparepository.ProductJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository{

    private final PaymentJpaRepository paymentJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    public PaymentRepositoryImpl(PaymentJpaRepository paymentJpaRepository, ProductJpaRepository productJpaRepository) {
        this.paymentJpaRepository = paymentJpaRepository;
        this.productJpaRepository = productJpaRepository;
    }

    //결제하기
    @Override
    public Payment save(Payment payment) {

        return paymentJpaRepository.save(payment);
    }

    //결제내역 조회
    @Override
    public Page<Payment> getPaymentList(int userId, Pageable pageable) {


        Page<Payment> payments = paymentJpaRepository.findByUserId(userId, pageable);

        if(payments == null){

            throw new IllegalArgumentException("PayHistory Not Found");
        }

        return payments;
    }
}
