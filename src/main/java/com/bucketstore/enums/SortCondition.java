package com.bucketstore.enums;

import com.bucketstore.ports.DisplayableCode;
import com.bucketstore.ports.SortableField;

public record SortCondition(SortableField field, SortDirection direction) {}

