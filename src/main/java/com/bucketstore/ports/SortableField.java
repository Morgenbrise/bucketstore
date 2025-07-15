package com.bucketstore.ports;

import com.bucketstore.enums.SortDirection;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;

public interface SortableField extends DisplayableCode {
    Sort.Order toOrder(SortDirection direction);
    OrderSpecifier<?> toOrderSpecifier(SortDirection direction);
}