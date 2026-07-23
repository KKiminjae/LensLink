package com.lenslink.domain.search.dto;

import com.lenslink.domain.search.entity.SearchHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SearchHistoryResponse {

    private Long id;

    private String brand;

    private String productName;

    private String imageUrl;

    private LocalDateTime createdAt;

    public static SearchHistoryResponse from(SearchHistory history){
        return SearchHistoryResponse.builder()
                .id(history.getId())
                .brand(history.getBrand())
                .productName(history.getProductName())
                .imageUrl(history.getImageUrl())
                .createdAt(history.getCreatedAt())
                .build();
    }
}
