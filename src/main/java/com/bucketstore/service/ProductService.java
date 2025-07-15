package com.bucketstore.service;

import com.bucketstore.dto.product.ProductDTO;
import com.bucketstore.dto.product.ProductSearchRequest;
import com.bucketstore.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> findProducts(ProductSearchRequest request) {
        return productRepository.findProducts(request);
    }

}
