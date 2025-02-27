package kr.hhplus.be.server.domain.payment.repository;

import kr.hhplus.be.server.domain.payment.entity.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PaymentRepository {

    //결제하기
    Payment save(Payment payment);

//    //OriginPrice 계산
//    Integer originPrice(int quantity, int price);
//
//    //DisCountPrice 계산
//    Integer discountPrice(int originPrice, int percent);

    //결제 내역 조회
    List<Payment> getPaymentList(long userId);
}
