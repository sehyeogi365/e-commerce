package kr.hhplus.be.server.domain.payment.service;

import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStatus;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;

import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.api.payment.dto.PaymentResponse;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.log;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    //TODO: 결제에 쿠폰 적용 빼기->주문으로 이동, 상품 수량 감소, 판매량 증가, 주문상태 변경, 포인트차감만 넣기
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CouponRepository couponRepository;
    private  final PointRepository pointRepository;
    // 결제하기
    @Transactional
    public PaymentResponse addPayment(Payment payment){//TODO - 상품 재고 확인/차감- 쿠폰 검증/사용 - 포인트 차감- 결제 정보 저장 시간나면 Facade패턴도 도입해보기
        // 상품 정보 확인 및 수량 차감
        // 상품 정보 조회 -> 데이터가 존재하면 차감 데이터가 없으면 예외처리 수량이 없어서 결제 실패
        Product product = productRepository.findById(payment.getProductId()).orElseThrow(()-> new CustomException(ErrorCode.ITEM_NOT_FOUND));

        if(product.getQuantity() <= 0){
            new CustomException(ErrorCode.ITEM_QUANTITY_ZERO);
        }

        //결제 정보 저장 -> 리퀘스트 파라미터로 변경후 빌더 부분 변경 해보기
        Payment newPayment = Payment.builder().id(payment.getId())
                .userId(payment.getUserId())
                .orderId(payment.getOrderId())
                .couponId(payment.getCouponId())
                .productId(payment.getProductId())
                .paymentStatus(payment.getPaymentStatus())
                .build();

        log(payment.getCouponId());
        log(payment.getProductId());

        Payment savedPayment = paymentRepository.save(newPayment);
        // 상품수량 감소 -> 결제에 유지
        productRepository.productQuantityDecrease(savedPayment.getProductId());
        // 판매량 수량 추가
        productRepository.productSalesIncrease(savedPayment.getProductId());
        // 주문 상태 변경
        List<Order> orders = orderRepository.getOrders(newPayment.getUserId());

        // 2. 조건에 따라 주문 선택 (예: 가장 최근 주문)
        Order targetOrder = orders.stream()
                .max(Comparator.comparing(Order::getCreatedAt)) // createdAt 기준 정렬
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        // 3. 상태 변경
        targetOrder.changeStatus(OrderStatus.PAYED);

        return new PaymentResponse(savedPayment.getId(), savedPayment.getOrderId());
    }

    // 2. 쿠폰 및 포인트 처리,  3. 결제 정보 저장을 분리해보기 위의 메서드에서 나중에

    //원가 계산
    public Integer calculateOriginPrice(int quantity, int price) {
        return quantity * price;
    }

    // 결제 내역 조회
    public List<PaymentResponse> getPaymentList(long userId) {
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
