package kr.hhplus.be.server.infra.product.repository;


import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.infra.product.jparepository.ProductJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    public ProductRepositoryImpl(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    //상품 조회
    @Override
    public Page<Product> getProducts(Pageable pageable) {

      Page<Product> products = productJpaRepository.findAll(pageable);
      if(products == null){
          throw new IllegalArgumentException("Product not found");
      }
      return productJpaRepository.findAll(pageable);
    }

    //상품 1행 정보 조회
    @Override
    public Optional<Product> findById(int id) {

        return  productJpaRepository.findById(id);
    }


    //Top5
    @Override
    public Optional<List<Product>> getTop5List() {

        //5개일때

        //5개미만일때 분기처리

        return null;
    }


}
