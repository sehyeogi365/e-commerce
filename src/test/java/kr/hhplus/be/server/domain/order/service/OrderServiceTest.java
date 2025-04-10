package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.coupon.entity.Coupon;
import kr.hhplus.be.server.domain.coupon.entity.UserCoupon;
import kr.hhplus.be.server.domain.coupon.enums.CouponStatus;
import kr.hhplus.be.server.domain.coupon.repository.CouponRepository;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStatus;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.point.repository.PointRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.api.order.dto.OrderResponse;
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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private PointRepository pointRepository;
    @Mock
    private CouponRepository couponRepository;
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문 생성")
    void 주문_생성() throws ParseException {
        //given
        long id = 1L;
        long userId = 1L;
        long productId = 1L;
        long couponId = 1L;
        int originalPrice = 1000;
        int discountPercent = 20;
        int discountPrice = originalPrice * (100 - discountPercent) / 100;

        String dateString = "2026-04-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date expirationDate = dateFormat.parse(dateString);

        Product product = Product.builder()
                                .id(productId)
                                .name("apple")
                                .price(1000)
                                .quantity(1)
                                .build();

        Order order = Order.builder()
                            .id(id)
                            .userId(userId)
                            .productId(productId)
                            .couponId(couponId)
                            .orderStatus(OrderStatus.ORDERED)
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

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(couponRepository.findUserCouponInfo(userId, couponId)).thenReturn(Optional.of(userCoupon));
        doNothing().when(couponRepository).useCoupon(couponId);
        when(pointRepository.usePoint(userId,discountPrice)).thenReturn(discountPrice);

        // when
        OrderResponse result = orderService.orderProduct(order);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.getProductId()).isEqualTo(1);
    }

    @Test
    @DisplayName("주문 실패: 수량부족")
    void 주문_생성2(){
        //given
        long id = 1L;
        long userId = 1L;
        long productId = 1L;
        long couponId = 1L;

        Product product = Product.builder()
                .id(productId)
                .name("apple")
                .price(1000)
                .quantity(0)
                .build();

        Order order = Order.builder()
                            .id(id)
                            .userId(userId)
                            .productId(productId)
                            .couponId(couponId)
                            .orderStatus(OrderStatus.ORDERED)
                            .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        //when&then
        assertThatThrownBy(() -> orderService.orderProduct(order)) // 예외가 발생해야 함
                .isInstanceOf(CustomException.class) // CustomException 발생 예상
                .hasMessage(ErrorCode.ITEM_QUANTITY_ZERO.getMessage());
    }

    @Test
    @DisplayName("주문 실패: 상품 부재")
    void 주문_생성3(){
        //given
        long id = 1L;
        long userId = 1L;
        long productId = 1L;
        long couponId = 1L;

        Order order = Order.builder()
                            .id(id)
                            .userId(userId)
                            .productId(productId)
                            .couponId(couponId)
                            .orderStatus(OrderStatus.ORDERED)
                            .build();

        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        //OrderResponse result = orderService.orderProduct(order);

        //when&then
        assertThatThrownBy(() -> orderService.orderProduct(order)) // 예외가 발생해야 함
                .isInstanceOf(CustomException.class) // CustomException 발생 예상
                .hasMessage(ErrorCode.ITEM_NOT_FOUND.getMessage());
    }
    @Test
    @DisplayName("주문 조회")
    void 주문_조회(){
        //given
        long id = 1L;
        long userId = 1L;
        long productId = 1L;
        long couponId = 1L;

        Order order = Order.builder()
                            .id(id)
                            .userId(userId)
                            .productId(productId)
                            .couponId(couponId)
                            .orderStatus(OrderStatus.ORDERED)
                            .build();

        List<Order> mockOrderList = List.of(order);

        when(orderRepository.getOrders(userId)).thenReturn(mockOrderList);

        //when
        List<OrderResponse> result = orderService.getOrderList(userId);

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

}