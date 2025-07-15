package com.bucketstore.common.dto;

import com.bucketstore.common.enums.SortDirection;
import com.bucketstore.ports.SortableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

import java.util.function.Function;

@Getter
@AllArgsConstructor
public class SortRequestCondition {

        @Schema(description = "정렬 필드 코드", example = "PRICE")
        private String code;

        @Schema(description = "정렬 방향", example = "ASC")
        private SortDirection direction;

        public <T extends SortableField> Sort.Order toOrder(Function<String, T> codeResolver) {
                return codeResolver.apply(code).toOrder(direction);
        }
}
