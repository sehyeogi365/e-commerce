package kr.hhplus.be.server.interfaces.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.product.service.ProductService;
import kr.hhplus.be.server.interfaces.product.dto.ProductResponse;
import kr.hhplus.be.server.interfaces.product.dto.ProductSaleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductService productService;


    @Test
    @DisplayName("상품 조회")
    void 상품_조회() throws Exception{

        // given
        long id = 1L;
        String name = "apple";
        int price = 1000;
        int quantity = 1;

        ProductResponse productResponse = new ProductResponse(id, name, price, quantity);

        List<ProductResponse> mockProductList = List.of(productResponse);

        when(productService.getProducts()).thenReturn(mockProductList);

        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/select")  // GET 요청 + Path Variable 적용
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }

    @Test
    @DisplayName("상품 조회 실패 케이스")
    void 상품_조회2() throws Exception{
        // given
        long id = 1L;
        String name = "apple";
        int price = 1000;
        int quantity = 1;

        ProductResponse productResponse = new ProductResponse(id, name, price, quantity);

        List<ProductResponse> mockProductList = List.of(productResponse);
        when(productService.getProducts()).thenReturn(Collections.emptyList());

        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/select")  // GET 요청 + Path Variable 적용
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }

    @Test
    @DisplayName("Top5 조회")
    void Top5_조회() throws Exception{
        // given
        String dateString = "2026-04-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date saleDate = dateFormat.parse(dateString);

        List<ProductSaleResponse> mockTop5List = List.of(
                                                new ProductSaleResponse(1L, 1L, saleDate, 1),
                                                new ProductSaleResponse(2L, 2L, saleDate, 2),
                                                new ProductSaleResponse(3L, 3L, saleDate, 3),
                                                new ProductSaleResponse(4L, 4L, saleDate, 4),
                                                new ProductSaleResponse(5L, 5L, saleDate, 5)
                                                );

        when(productService.getTop5List()).thenReturn(mockTop5List);

        // when&then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/rank")  // GET 요청 + Path Variable 적용
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").value(1L));

    }

}