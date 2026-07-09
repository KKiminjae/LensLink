package com.lenslink.domain.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ProductResponse {
    private String brand;

    private String productName;

    // private long price;

    private String mall;

    private String productUrl;

    // private String imageUrl;

    // private boolean soldOut;
}
