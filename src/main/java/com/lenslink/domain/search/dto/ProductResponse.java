package com.lenslink.domain.search.dto;

import lombok.Builder;
import lombok.Getter;

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
