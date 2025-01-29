package kr.hhplus.be.server.domain.order.service;

import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStatus;
import kr.hhplus.be.server.domain.order.repository.OrderRepository;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.order.dto.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    @DisplayName("주문 생성")
    void 주문_생성(){
        //given
        long id = 1L;
        int userId = 1;
        int productId = 1;
        int couponId = 1;

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

        //when
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        OrderResponse result = orderService.orderProduct(order);

        //then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1);
        assertThat(result.getProductId()).isEqualTo(1);
    }

    @Test
    @DisplayName("주문 실패: 수량부족")
    void 주문_생성2(){
        //given
        long id = 1L;
        int userId = 1;
        int productId = 1;
        int couponId = 1;

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

        //when
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        //then
        assertThatThrownBy(() -> orderService.orderProduct(order)) // 예외가 발생해야 함
                .isInstanceOf(CustomException.class) // CustomException 발생 예상
                .hasMessage(ErrorCode.ITEM_QUANTITY_ZERO.getMessage());
    }

    @Test
    @DisplayName("주문 실패: 상품 부재")
    void 주문_생성3(){
        //given
        long id = 1L;
        int userId = 1;
        int productId = 1;
        int couponId = 1;

        Order order = Order.builder()
                            .id(id)
                            .userId(userId)
                            .productId(productId)
                            .couponId(couponId)
                            .orderStatus(OrderStatus.ORDERED)
                            .build();

        //when
        when(productRepository.findById(productId)).thenReturn(Optional.empty());
        //OrderResponse result = orderService.orderProduct(order);

        //then
        assertThatThrownBy(() -> orderService.orderProduct(order)) // 예외가 발생해야 함
                .isInstanceOf(CustomException.class) // CustomException 발생 예상
                .hasMessage(ErrorCode.ITEM_NOT_FOUND.getMessage());
    }
    @Test
    @DisplayName("주문 조회")
    void 주문_조회(){
        //given
        long id = 1L;
        int userId = 1;
        int productId = 1;
        int couponId = 1;

        Order order = Order.builder()
                            .id(id)
                            .userId(userId)
                            .productId(productId)
                            .couponId(couponId)
                            .orderStatus(OrderStatus.ORDERED)
                            .build();

        List<Order> mockOrderList = List.of(order);
        //when
        when(orderRepository.getOrders(userId)).thenReturn(mockOrderList);
        List<OrderResponse> result = orderService.getOrderList(userId);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

}