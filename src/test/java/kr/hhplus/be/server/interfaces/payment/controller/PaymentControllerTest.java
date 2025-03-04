package kr.hhplus.be.server.interfaces.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.payment.entity.Payment;
import kr.hhplus.be.server.domain.payment.enums.PaymentStatus;
import kr.hhplus.be.server.domain.payment.service.PaymentService;
import kr.hhplus.be.server.interfaces.payment.dto.PaymentResponse;
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
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PaymentService paymentService;

    @Test
    @DisplayName("결제")
    void 결제() throws Exception{
        // given
        long userId = 1L;
        long orderId = 1L;
        long productId = 1L;
        long couponId = 1L;

        Payment payment = Payment.builder()
                                    .id(1L)
                                    .userId(userId)
                                    .orderId(orderId)
                                    .productId(productId)
                                    .couponId(couponId)
                                    .paymentStatus(PaymentStatus.PAYED)
                                    .originPrice(1000)
                                    .discountPrice(500)
                                    .build();

        PaymentResponse paymentResponse = new PaymentResponse(1L, orderId);

        when(paymentService.addPayment(any(Payment.class))).thenReturn(paymentResponse);
        // when&then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/payment/")
                        .content(objectMapper.writeValueAsString(paymentResponse))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    @DisplayName("결제 조회")
    void 결제_조회() throws Exception{
        // given
        long userId = 1L;
        long orderId = 1L;
        PaymentResponse paymentResponse = new PaymentResponse(userId, orderId);

        List<PaymentResponse> mockPayList = List.of(new PaymentResponse(1L, 1L)
                                                    , new PaymentResponse(1L, 2L)
                                                    , new PaymentResponse(1L, 3L)
                                                    , new PaymentResponse(1L, 4L)
                                                    );

        when(paymentService.getPaymentList(userId)).thenReturn(mockPayList);

        // when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/payment/{userId}", userId)  // GET 요청 + Path Variable 적용
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));

    }
}