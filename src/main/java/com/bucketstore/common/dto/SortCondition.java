package com.bucketstore.common.enums;

import com.bucketstore.ports.SortableField;

public record SortCondition(SortableField field, SortDirection direction) {}

