package com.bucketstore.repository.product;

import com.bucketstore.dto.product.ProductDTO;
import com.bucketstore.dto.product.ProductSearchRequest;

import java.util.List;

public interface ProductQueryRepository {
    List<ProductDTO> findProducts(ProductSearchRequest request);
}
