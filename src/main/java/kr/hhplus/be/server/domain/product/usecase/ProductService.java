package kr.hhplus.be.server.domain.product.usecase;

import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //상품 조회
    public Optional<Product> getProductList(int id){

        return productRepository.getProductList(id);
    }

    //Top5
    public Optional<Product> getTop5List(Product product){

        return productRepository.getTop5List(product);
    }


}
