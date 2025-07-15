package com.bucketstore.ports;

import com.bucketstore.enums.SortDirection;
import org.springframework.data.domain.Sort;

public interface DisplayableCode {
    String getCode();      // "BASE_PRICE"
    String getLabel();     // "가격"
}
