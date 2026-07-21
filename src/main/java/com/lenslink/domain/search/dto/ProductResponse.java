package com.lenslink.domain.search.dto;

import com.lenslink.domain.search.Mall;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ProductResponse {
    private String brand;

    private String productName;

    private int price;

    private String platform;

    private String mall;

    private String productUrl;

    private String imageUrl;

    private boolean used;
}
