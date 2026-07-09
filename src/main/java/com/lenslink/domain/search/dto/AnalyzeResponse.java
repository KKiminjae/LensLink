package com.lenslink.domain.search.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public class AnalyzeResponse {

    private String brand;

    private String productName;

    private String color;

    private String category;

    private int confidence;


    private List<SimilarProductResponse> similarProducts;

    @Getter
    @Builder
    public static class SimilarProductResponse {
        private String productName;
        private int confidence;
    }
}
