package com.bucketstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springdoc.core.annotations.ParameterObject;

import java.util.List;

@Getter
@ParameterObject
@AllArgsConstructor
public class ProductSearchRequest {

    @Schema(description = "페이지 번호", example = "0")
    private final int page;

    @Schema(description = "페이지 사이즈", example = "10")
    private final int size;

    @Schema(description = "정렬 조건 리스트")
    private final List<SortConditionDTO> sort;

}
