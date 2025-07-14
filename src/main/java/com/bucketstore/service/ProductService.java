package com.bucketstore.service;

import com.bucketstore.common.utils.PageRequestUtils;
import com.bucketstore.dto.ProductDTO;
import com.bucketstore.enumType.ProductSortField;
import com.bucketstore.enumType.SortDirection;
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
        ProductSortField sortField = ProductSortField.from(direction);
        Pageable pageable = PageRequestUtils.of(0, limit, SortDirection.from(sortBy), sortField);

        return productRepository.findAll(pageable).stream()
                .map(ProductDTO::from)
                .toList();
    }

}
