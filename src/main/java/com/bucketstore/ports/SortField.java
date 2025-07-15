package com.bucketstore.ports;

import com.bucketstore.enums.SortDirection;
import org.springframework.data.domain.Sort;

public interface SortField {
    String getCode();      // "BASE_PRICE"
    String getLabel();     // "가격"
    Sort.Order toOrder(SortDirection direction);  // 실제 정렬 조건

}
