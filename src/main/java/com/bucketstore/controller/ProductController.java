package com.bucketstore.controller;

import com.bucketstore.dto.ProductDTO;
import com.bucketstore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getSortedProducts(
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(defaultValue = "10") int limit
    ) {
        List<ProductDTO> result = productService.findProducts(sortBy, direction, limit);
        return ResponseEntity.ok(result);
    }
}
