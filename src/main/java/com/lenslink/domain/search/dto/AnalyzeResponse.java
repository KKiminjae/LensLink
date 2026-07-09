package com.lenslink.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyzeResponse {

    private String brand;

    private String productName;

    private String color;

    private String category;

    private int confidence;


    private List<SimilarProductResponse> similarProducts;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimilarProductResponse {
        private String productName;
        private int confidence;
    }
}
