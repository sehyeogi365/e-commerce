package kr.hhplus.be.server.infra.payment.repository;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;

import kr.hhplus.be.server.infra.payment.jparepository.PaymentJpaRepository;

import lombok.RequiredArgsConstructor;
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

    //OriginPrice 계산
//    @Override
//    public Integer originPrice(int quantity, int price){
//       return paymentJpaRepository.originPrice(quantity, price);
//    }
//    //DisCountPrice 계산
//    @Override
//    public Integer discountPrice(int originPrice, int percent){
//        return paymentJpaRepository.discountPrice(originPrice, percent);
//    }

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
