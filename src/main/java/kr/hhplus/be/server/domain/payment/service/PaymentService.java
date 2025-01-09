package kr.hhplus.be.server.domain.payment.service;


import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;


import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PaymentService {


    private final PaymentRepository paymentRepository;

    //결제하기
    @Transactional
    public Payment addPayment(Payment payment){

        // 상품 정보 확인 및 수량 차감
        // 상품 정보 조회 -> 데이터가 존재하면 차감 데이터가 없으면 예외처리 수량이 없어서 결제 실패

        //Optional<Product> product = productRepository.findById(payment.get);

        // 쿠폰 정보 확인 및 수량 차감

        //결제 정보 저장
        return paymentRepository.save(payment);
    }

    //결제 내역 조회
    public Page<Payment> getPayment(int userId, Pageable pageable){

        try{
            return paymentRepository.getPaymentList(userId, pageable);
        }catch (IllegalArgumentException e){
            throw new IllegalStateException("PayHistory not found");
        }
    }

}
