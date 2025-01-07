package kr.hhplus.be.server.domain.payment.usecase;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    //결제하기
    @Transactional
    public Payment addPayment(Payment payment){

        return paymentRepository.save(payment);
    }

    //결제 내역 조회
    public Optional<Payment> getPayment(int userId){

        return paymentRepository.getPaymentList(userId);
    }

}
