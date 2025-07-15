package com.bucketstore.repository.product;

import com.bucketstore.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductQueryRepository {
    Optional<Product> findByProductCode(String productCode);
}
