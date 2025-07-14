package com.bucketstore.common.utils;

import com.bucketstore.enumType.ProductSortField;
import com.bucketstore.enumType.SortCondition;
import com.bucketstore.enumType.SortDirection;
import com.bucketstore.inter.SortField;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageRequestUtils {

    private PageRequestUtils() {} // 인스턴스화 방지

    public static Pageable of(int page, int size, SortDirection direction, SortField sortField) {
        if (page < 0) throw new IllegalArgumentException("page must be 0 or greater");
        if (size <= 0) throw new IllegalArgumentException("size must be greater than 0");

        return PageRequest.of(page, size, Sort.by(sortField.toOrder(direction)));
    }

    // 복수 정렬 필드용
    public static Pageable of(int page, int size, List<SortCondition> sortConditions) {
        if (sortConditions == null || sortConditions.isEmpty()) {
            // fallback: 기본 정렬
            return PageRequest.of(page, size, Sort.by(ProductSortField.CREATED.toOrder(SortDirection.DESC)));
        }

        List<Sort.Order> orders = sortConditions.stream()
                .map(sc -> sc.field().toOrder(sc.direction()))
                .toList();

        return PageRequest.of(page, size, Sort.by(orders));
    }

    public static Pageable of(int page, int size, SortCondition... sortConditions) {
        return of(page, size, List.of(sortConditions));
    }

}