package kr.hhplus.be.server.infra.payment.repository;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PaymentRepositoryImpl implements PaymentRepository{

    @Override
    public Payment save(int userId, int orderId) {

        return save(userId, orderId);
    }

    @Override
    public Optional<Payment> getPaymentList(int userId) {


        return getPaymentList(userId);
    }
}
