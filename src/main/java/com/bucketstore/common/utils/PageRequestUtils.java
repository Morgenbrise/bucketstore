package com.bucketstore.common.utils;

import com.bucketstore.enums.sort.ProductSort;
import com.bucketstore.common.enums.SortCondition;
import com.bucketstore.common.enums.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PageRequestUtils {

    private PageRequestUtils() {} // 인스턴스화 방지

    /**
     * 입력된 정렬 조건에 따라 PageRequest 객체를 생성한다.
     *
     * @param page         페이지 번호 (0부터 시작)
     * @param size         페이지 사이즈
     * @param direction    정렬 방법
     * @return PageRequest
     */
    public static Pageable of(int page, int size, SortDirection direction) {
        if (page < 0) throw new IllegalArgumentException("page must be 0 or greater");
        if (size <= 0) throw new IllegalArgumentException("size must be greater than 0");

        return PageRequest.of(page, size);
    }

    /**
     * 입력된 정렬 조건에 따라 PageRequest 객체를 생성한다.
     *
     * @param page         페이지 번호 (0부터 시작)
     * @param size         페이지 사이즈
     * @param sortConditions    정렬 조건 리스트
     * @return PageRequest
     */
    public static Pageable of(int page, int size, List<SortCondition> sortConditions) {
        if (sortConditions == null || sortConditions.isEmpty()) {
            // fallback: 기본 정렬
            return PageRequest.of(page, size, Sort.by(ProductSort.CREATED.toOrder(SortDirection.DESC)));
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