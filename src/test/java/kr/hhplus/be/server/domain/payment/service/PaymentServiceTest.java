package kr.hhplus.be.server.domain.payment.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.enums.CouponStatus;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStatus;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatus;
import kr.hhplus.be.server.domain.payment.repository.PaymentRepository;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.api.payment.dto.PaymentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private PointRepository pointRepository;

    @InjectMocks
    private PaymentService paymentService;


    @Test
    @DisplayName("결제하기")
    void 결제하기()throws ParseException {// jpa에 직접의존해서 db에 연결하는 구조로 단테가 좀 힘듦 레포지터리 통합테스트로 해보기
        // given
        String dateString = "2026-04-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);
        long id = 1L;
        long userId = 1L;
        long orderId = 1L;
        long productId = 1L;
        long couponId = 1L;
        int originalPrice = 1000;
        int discountPercent = 20;
        int discountPrice = originalPrice * (100 - discountPercent) / 100;

        Product product = Product.builder()
                                .id(id)
                                .name("apple")
                                .price(1000)
                                .quantity(1)
                                .build();
        // 쿠폰 엔티티 선언
        Coupon coupon = Coupon.builder()
                                .id(couponId)
                                .percent(20)
                                .quantity(1)
                                .expirationDate(expirationDate)
                                .build();

        UserCoupon userCoupon = UserCoupon.builder()
                                        .id(id)
                                        .userId(userId)
                                        .couponId(couponId)
                                        .coupon(coupon)
                                        .couponStatus(CouponStatus.UNUSED)
                                        .build();
        Order order = Order.builder()
                            .id(orderId)
                            .userId(userId)
                            .productId(productId)
                            .couponId(couponId)
                            .orderStatus(OrderStatus.ORDERED) // 초기 상태
                            .build();

        Payment payment = Payment.builder()
                            .id(id)
                            .userId(userId)
                            .orderId(orderId)
                            .productId(productId)
                            .couponId(couponId)
                            .paymentStatus(PaymentStatus.PAYED)
                            .originPrice(originalPrice)
                            .discountPrice(discountPrice)
                            .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
//        when(couponRepository.findUserCouponInfo(userId, couponId)).thenReturn(Optional.of(userCoupon));
//        doNothing().when(couponRepository).useCoupon(couponId);
        when(orderRepository.getOrders(userId)).thenReturn(List.of(order));
//        when(pointRepository.usePoint(userId,discountPrice)).thenReturn(discountPrice);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        doNothing().when(productRepository).productQuantityDecrease(productId);
        doNothing().when(productRepository).productSalesIncrease(productId);
        // when
        PaymentResponse result = paymentService.addPayment(payment);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getOrderId()).isEqualTo(1L);
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.PAYED);
    }

    //시간나면 결제 실패테스트도 해보기
    @Test
    @DisplayName("결제 내역 조회")
    void 결제_내역_조회(){
        //given
        long id = 1L;
        long userId = 1L;
        long orderId = 1L;
        long productId = 1L;
        long couponId = 1L;

        Payment payment = Payment.builder()
                .id(id)
                .userId(userId)
                .orderId(orderId)
                .productId(productId)
                .couponId(couponId)
                .paymentStatus(PaymentStatus.PAYED)
                .build();

        List<Payment> mockPaymentList = List.of(payment);

        when(paymentRepository.getPaymentList(userId)).thenReturn(mockPaymentList);

        //when
        List<PaymentResponse> result = paymentService.getPaymentList(userId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1L);
    }
}