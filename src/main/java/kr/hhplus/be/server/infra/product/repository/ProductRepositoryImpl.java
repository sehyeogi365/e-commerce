package kr.hhplus.be.server.infra.product.repository;


import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {


    //상품 조회
    @Override
    public Optional<Product> getProductList(int id) {

      return getProductList(id);
    }

    //Top5
    @Override
    public Optional<Product> getTop5List(Product product) {

        if (product == null) {
            return Optional.empty();
        }

        return Optional.of(product);
    }


}
