package com.bucketstore.common.dto;

import com.bucketstore.common.enums.SortDirection;
import com.bucketstore.ports.SortableField;

public record ResolvedSortCondition(SortableField field, SortDirection direction) {}

