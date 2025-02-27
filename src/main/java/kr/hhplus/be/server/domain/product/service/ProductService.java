package kr.hhplus.be.server.domain.product.service;

import kr.hhplus.be.server.domain.error.CustomException;
import kr.hhplus.be.server.domain.error.ErrorCode;
import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.ProductSale;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.interfaces.product.dto.ProductResponse;
import kr.hhplus.be.server.interfaces.product.dto.ProductSaleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //상품 조회
    public List<ProductResponse> getProducts(){

        List<Product> productList = productRepository.getProducts();

        if(productList.isEmpty()){
            throw new CustomException(ErrorCode.ITEM_NOT_FOUND);
        }

        List<ProductResponse> response = new ArrayList<>();

        for(Product product: productList){
            response.add(new ProductResponse(product.getId(), product.getName(), product.getPrice(), product.getQuantity()));
        }
        return response;
    }

    //Top5
    public List<ProductSaleResponse> getTop5List(){

        List<ProductSale> productSaleList = productRepository.getTop5List();

        List<ProductSaleResponse> response = new ArrayList<>();

        for(ProductSale product : productSaleList){
            response.add(new ProductSaleResponse(product.getId(), product.getProductId(), product.getSaleDate(),product.getQuantitySold()));
        }
        return response;
    }

}
