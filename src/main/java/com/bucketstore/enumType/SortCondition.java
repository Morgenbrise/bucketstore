package com.bucketstore.enumType;

import com.bucketstore.inter.SortField;

public record SortCondition(SortField field, SortDirection direction) {}

