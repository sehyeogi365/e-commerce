package kr.hhplus.be.server.domain.payment.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;

import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private  final PointRepository pointRepository;
    //결제하기
    @Transactional
    public PaymentResponse addPayment(Payment payment){//TODO - 상품 재고 확인/차감- 쿠폰 검증/사용 - 포인트 차감- 결제 정보 저장 시간나면 Facade패턴도 도입해보기
        // 상품 정보 확인 및 수량 차감
        // 상품 정보 조회 -> 데이터가 존재하면 차감 데이터가 없으면 예외처리 수량이 없어서 결제 실패
        Product product = productRepository.findById(payment.getProductId()).orElseThrow(()-> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        if(product.getQuantity() <= 0){
            new CustomException(ErrorCode.ITEM_QUANTITY_ZERO);
        }

        int originPrice = calculateOriginPrice(product.getQuantity(), product.getPrice());

        // 쿠폰 정보 확인 및 수량 차감
        int discountPrice = 0;

        if(payment.getCouponId() > 0){//쿠폰 적용 + 가격 감소
            //UserCoupon userCoupon = couponRepository.findUserCouponInfo(payment.getUserId(),payment.getCouponId()).orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));
            discountPrice = calculateDiscountPrice(originPrice, payment.getUserId(), payment.getCouponId());
            couponRepository.useCoupon(payment.getCouponId());
            //포인트 차감
            pointRepository.usePoint(payment.getUserId(), discountPrice);
        } else {
            pointRepository.usePoint(payment.getUserId(), originPrice);
        }
        //쿠폰 없을시 원래가격 계산
        //결제 정보 저장
        Payment newPayment = Payment.builder().id(payment.getId())
                .userId(payment.getUserId())
                .orderId(payment.getOrderId())
                .couponId(payment.getCouponId())
                .productId(payment.getProductId())
                .paymentStatus(payment.getPaymentStatus())
                .originPrice(originPrice)
                .discountPrice(discountPrice).build();

        Payment savedPayment = paymentRepository.save(newPayment);
        //상품수량 감소
        productRepository.productQuantityDecrease(savedPayment.getProductId());
        //판매량 수량 추가
        productRepository.ProductSalesIncrease(savedPayment.getProductId());

        return new PaymentResponse(savedPayment.getId(), savedPayment.getOrderId());
    }

    // 2. 쿠폰 및 포인트 처리,  3. 결제 정보 저장을 분리해보기 위의 메서드에서 나중에

    //원가 계산
    public Integer calculateOriginPrice(int quantity, int price) {
        return quantity * price;
    }

    //할인 가격 계산
    public Integer calculateDiscountPrice(int originPrice, int userId, int couponId) {

        UserCoupon userCoupon = couponRepository.findUserCouponInfo(userId, couponId)
                .orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        Coupon coupon = userCoupon.getCoupon();
        if (coupon == null) {
            throw new CustomException(ErrorCode.COUPON_NOT_FOUND); // coupon이 null인 경우 처리
        }
        return (originPrice * (100- coupon.getPercent()) / 100);
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
