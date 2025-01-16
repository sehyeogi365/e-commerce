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
        Product product = productRepository.findById(payment.getProductId());

        if(product == null){
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        int originPrice = calculateOriginPrice(product.getQuantity(), product.getPrice());

        // 쿠폰 정보 확인 및 수량 차감
        int discountPrice = 0;

        if(payment.getCouponId() <=0){//쿠폰 적용 + 가격 감소
            Coupon coupon = couponRepository.getCouponInfo(payment.getCouponId());

            if (coupon == null) {
                throw new CustomException(ErrorCode.COUPON_NOT_FOUND);
            }
            discountPrice = calculateDiscountPrice(originPrice, coupon.getPercent());
            couponRepository.useCoupon(payment.getCouponId());
        }
        //쿠폰 없을시 원래가격 계산
        //결제 정보 저장
        Payment.builder().id(payment.getId())
                .userId(payment.getUserId())
                .orderId(payment.getOrderId())
                .couponId(payment.getCouponId())
                .productId(payment.getProductId())
                .statement(payment.getStatement())
                .originPrice(originPrice)
                .discountPrice(discountPrice).build();

        Payment savedPayment = paymentRepository.save(payment);

        //판매량 수량 추가
        productRepository.ProductSalesIncrease(savedPayment.getProductId());

        return new PaymentResponse(savedPayment.getId(), savedPayment.getOrderId());
    }

    //원가 계산
    public Integer calculateOriginPrice(int quantity, int price) {
        return quantity * price;
    }

    //할인 가격 계산
    public Integer calculateDiscountPrice(int originPrice, int percent) {
        return (originPrice * (100- percent) / 100);
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
