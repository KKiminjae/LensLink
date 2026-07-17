package com.lenslink.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SearchResponse {
    private AnalyzeResponse analysis;

    private List<ProductResponse> newProducts;

    private List<ProductResponse> usedProducts;
}
