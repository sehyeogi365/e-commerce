package kr.hhplus.be.server.interfaces.product.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.ProductSale;
import kr.hhplus.be.server.domain.product.service.ProductService;
import kr.hhplus.be.server.interfaces.common.ApiResponse;
import kr.hhplus.be.server.interfaces.product.dto.ProductRequest;
import kr.hhplus.be.server.interfaces.product.dto.ProductResponse;
import kr.hhplus.be.server.interfaces.product.dto.ProductSaleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RestControllerAdvice
@Slf4j
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/select")
    @Tag(name = "상품 조회")
    @Operation(summary = "상품 조회", description = "모든 상품을 조회 합니다.")
    public ApiResponse<List<ProductResponse>> productSelect(@RequestBody ProductRequest productRequest){

        List<ProductResponse> response = productService.getProducts();

        log.info("response " +response);
        return ApiResponse.ok(response);
    }

    @GetMapping("/rank")
    @Tag(name = "Top5 조회")
    @Operation(summary = "Top5 상품 조회", description = "최근3일간 5순위 상품을 조회 합니다.")
    public ApiResponse<List<ProductSaleResponse>> top5Select(@RequestBody ProductRequest productRequest){

        List<ProductSaleResponse> response = productService.getTop5List();

        log.info("response " +response);
        return ApiResponse.ok(response);
    }
}
