package com.bucketstore.service;

import com.bucketstore.common.utils.PageRequestUtils;
import com.bucketstore.dto.ProductDTO;
import com.bucketstore.enumType.SortCondition;
import com.bucketstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> findProducts(int page, int size, List<SortCondition> conditions) {
        Pageable pageable = PageRequestUtils.of(page, size, conditions);

        return productRepository.findAll(pageable).stream()
                .map(ProductDTO::from)
                .toList();
    }

}
