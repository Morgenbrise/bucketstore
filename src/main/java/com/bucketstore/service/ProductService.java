package com.bucketstore.service;

import com.bucketstore.dto.ProductDTO;
import com.bucketstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> findProducts(String sortBy, String direction, int limit) {
        Sort.Direction sortDirection = Sort.Direction.fromOptionalString(direction)
                .orElse(Sort.Direction.ASC); // 기본값 ASC

        Pageable pageable = PageRequest.of(0, limit, Sort.by(sortDirection, sortBy));

        return productRepository.findAll(pageable).stream()
                .map(ProductDTO::from)
                .toList();
    }

}
