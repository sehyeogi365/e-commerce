package kr.hhplus.be.server.domain.payment.service;


import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;


import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    //결제하기
    @Transactional
    public PaymentResponse addPayment(Payment payment){
        // 상품 정보 확인 및 수량 차감
        // 상품 정보 조회 -> 데이터가 존재하면 차감 데이터가 없으면 예외처리 수량이 없어서 결제 실패
        Optional<Product> product = productRepository.findById(payment.getProductId());

        PaymentResponse response = new PaymentResponse(payment.getId(), payment.getOrderId());

        productRepository.productQuantityDecrease(
                product.orElseThrow(() -> new IllegalStateException("Product not found")).getId()
        );

        if(product.isEmpty()){
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        // 쿠폰 정보 확인 및 수량 차감
        Coupon coupon = couponRepository.getCouponInfo(payment.getCouponId());

        if(coupon == null){
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
        }

        //쿠폰 적용시 가격 감소

        //판매량 수량 추가
        productRepository.ProductSalesIncrease(payment.getProductId());

        //결제 정보 저장
        return response;
    }


    //결제 내역 조회
    public List<PaymentResponse> getPaymentList(int userId) {
        List<Payment> paymentList = paymentRepository.getPaymentList(userId);
        List<PaymentResponse> response = new ArrayList<>();

        for (Payment payment : paymentList) {
            response.add(new PaymentResponse(payment.getId(), payment.getOrderId()));
        }

        if (response.isEmpty()) {
            throw new CustomException(ErrorCode.PAYMENT_NOT_FOUND);
        }
        return response;
    }
}
