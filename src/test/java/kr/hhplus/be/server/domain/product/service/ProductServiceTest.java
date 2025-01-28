package kr.hhplus.be.server.domain.product.service;

import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.ProductSale;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.product.dto.ProductResponse;
import kr.hhplus.be.server.interfaces.product.dto.ProductSaleResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    @DisplayName("상품 조회")
    void 상품_조회(){
        //given
        long id = 1L;
        String name = "사과";
        int price = 1000;
        int quantity = 1;
        Product product = Product.builder()
                                .id(id)
                                .name(name)
                                .price(price)
                                .quantity(quantity)
                                .build();

        List<Product> mockProduct = List.of(product);

        //when
        when(productRepository.getProducts()).thenReturn(mockProduct);
        List<ProductResponse> result = productService.getProducts();

        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Top5 상품 조회")
    void Top5_상품_조회()throws ParseException {
        //given
        long id = 1L;
        int productId = 1;
        String dateString = "2026-04-02";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date saleDate = dateFormat.parse(dateString);

        List<ProductSale> mockTop5List = List.of(
        ProductSale.builder()
                .id(1L)
                .productId(1)
                .saleDate(saleDate)
                .quantitySold(100) // 1번 상품 판매량
                .build(),
                ProductSale.builder()
                        .id(2L)
                        .productId(2)
                        .saleDate(saleDate)
                        .quantitySold(90) // 2번 상품 판매량
                        .build(),
                ProductSale.builder()
                        .id(3L)
                        .productId(3)
                        .saleDate(saleDate)
                        .quantitySold(80) // 3번 상품 판매량
                        .build(),
                ProductSale.builder()
                        .id(4L)
                        .productId(4)
                        .saleDate(saleDate)
                        .quantitySold(70) // 4번 상품 판매량
                        .build(),
                ProductSale.builder()
                        .id(5L)
                        .productId(5)
                        .saleDate(saleDate)
                        .quantitySold(60) // 5번 상품 판매량
                        .build()
        );
        //List<ProductSale> mockTop5List = List.of(productSale);
        //when
        when(productRepository.getTop5List()).thenReturn(mockTop5List);

        List<ProductSaleResponse> result = productService.getTop5List();
        //then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(5);
    }

}