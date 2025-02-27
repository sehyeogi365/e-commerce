package kr.hhplus.be.server.interfaces.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.order.entity.Order;
import kr.hhplus.be.server.domain.order.enums.OrderStatus;
import kr.hhplus.be.server.domain.order.service.OrderService;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.interfaces.order.dto.OrderRequest;
import kr.hhplus.be.server.interfaces.order.dto.OrderResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private OrderService orderService;

    @Test
    @DisplayName("주문 하기")
    void 주문_하기() throws Exception{
        // given
        long productId = 1L;
        Order order = Order.builder()
                            .id(1L)
                            .userId(1L)
                            .productId(productId)
                            .couponId(1L)
                            .orderStatus(OrderStatus.ORDERED)
                            .originPrice(1000)
                            .discountPrice(500)
                            .build();

        OrderResponse orderResponse = new OrderResponse(1L, 1L, 1L, 1L);
        //OrderRequest orderRequest = new OrderRequest(1L, 1L, 1L, 1L);

        when(orderService.orderProduct(any(Order.class))).thenReturn(orderResponse);

        // when&then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/")
                        .content(objectMapper.writeValueAsString(orderResponse))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @DisplayName("주문 하기 실패케이스 : 수량부족")
    void 주문_하기2() throws Exception{
        // given
        long productId = 1L;
        Product product = Product.builder()
                .id(productId)
                .name("apple")
                .price(1000)
                .quantity(0)
                .build();

        Order order = Order.builder()
                .id(1L)
                .userId(1L)
                .productId(1L)
                .couponId(1L)
                .orderStatus(OrderStatus.ORDERED)
                .originPrice(1000)
                .discountPrice(500)
                .build();

        OrderResponse orderResponse = new OrderResponse(1L, 1L, 1L, 1L);
        //OrderRequest orderRequest = new OrderRequest(1L, 1L, 1L, 1L);

        //when(orderService.orderProduct(any(Order.class))).thenReturn(orderResponse);
        when(orderService.orderProduct(any(Order.class)))
                .thenThrow(new CustomException(ErrorCode.ITEM_QUANTITY_ZERO));
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/orders/")
                        .content(objectMapper.writeValueAsString(orderResponse))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("해당 상품 수량이 부족합니다.")); // 예외 코드 검증
    }



    @Test
    @DisplayName("주문 목록 조회")
    void 주문_목록_조회() throws Exception{
        // given
        Long userId = 1L;

        OrderResponse orderResponse = new OrderResponse(1L, 1L, 1L, 1L);

        List<OrderResponse> mockOrderList = List.of(
                                                    new OrderResponse(1L, 1L, 1L, 1L),
                                                    new OrderResponse(2L, 1L, 2L, 2L),
                                                    new OrderResponse(3L, 1L, 3L, 3L)
                                                    );

        when(orderService.getOrderList(userId)).thenReturn(mockOrderList);
        // when&then

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/orders/{userId}", userId)  // GET 요청 + Path Variable 적용
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }

}