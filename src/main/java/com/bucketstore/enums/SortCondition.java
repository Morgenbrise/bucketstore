package com.bucketstore.enums;

import com.bucketstore.ports.SortField;

public record SortCondition(SortField field, SortDirection direction) {}

