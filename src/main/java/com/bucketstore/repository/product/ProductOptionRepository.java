package com.bucketstore.repository.product;

import com.bucketstore.domain.Product;
import com.bucketstore.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
    Optional<ProductOption> findByProductAndSize(Product product, String size);
}
