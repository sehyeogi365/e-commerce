package kr.hhplus.be.server.infra.product.repository;


import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements ProductRepository {


    //상품 조회
    @Override
    public Optional<Product> getProductList(Product product) {


        return null;

    }

    //Top5
    @Override
    public Optional<Product> getTop5List(Product product) {
        return null;
    }





}
