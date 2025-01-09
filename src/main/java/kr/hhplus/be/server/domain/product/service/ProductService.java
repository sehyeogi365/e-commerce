package kr.hhplus.be.server.domain.product.service;

import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    //상품 조회
    public Page<Product> getProducts(Pageable pageable){

        Page<Product> products = productRepository.getProducts(pageable);

        try{
            return productRepository.getProducts(pageable);
        } catch (IllegalArgumentException e){
             throw new IllegalArgumentException("Product not found");
        }
    }

    //상품 갯수 차감

    //Top5
    public Optional<List<Product>> getTop5List(){
        return productRepository.getTop5List();
    }

}
