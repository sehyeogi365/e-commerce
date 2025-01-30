package kr.hhplus.be.server.infra.product.repository;


import kr.hhplus.be.server.domain.product.entity.Product;
import kr.hhplus.be.server.domain.product.entity.ProductSale;
import kr.hhplus.be.server.domain.product.repository.ProductRepository;
import kr.hhplus.be.server.infra.product.jparepository.ProductJpaRepository;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final ProductJpaRepository productJpaRepository;

    //상품 조회
    @Override
    public List<Product> getProducts() {
      return productJpaRepository.findAll();
    }

    //상품 1행 정보 조회
    @Override
    public Product getById(long id) {
        return productJpaRepository.getById(id);
    }

    @Override
    public Optional<Product> findById(long id) {
        return productJpaRepository.findById(id);
    }

    //판매수량 증가
    @Override
    public void productSalesIncrease(int productId) {
        productJpaRepository.productSaleIncrease(productId);
    }

    //상품 갯수 차감
    @Override
    public void productQuantityDecrease(long id) {
        productJpaRepository.productQuantityDecrease(id);
    }

    //Top5
    @Override
    public List<ProductSale> getTop5List() {
        return productJpaRepository.findTop5();
    }
}
