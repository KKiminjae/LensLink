package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.service.platform.SearchPlatform;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchPlatformServiceTest {
    private SearchPlatform platform1;
    private SearchPlatform platform2;
    private SearchPlatformService searchPlatformService;

    @BeforeEach
    void setUp(){
        platform1 = mock(SearchPlatform.class);
        platform2 = mock(SearchPlatform.class);

        searchPlatformService = new SearchPlatformService(List.of(platform1,platform2));
    }

    @Test
    void search_allPlatforms(){
        AnalyzeResponse analyzeResponse = new AnalyzeResponse();
        ProductResponse product1 = ProductResponse.builder()
                .brand("Nike")
                .productName("T-shirt")
                .build();
        ProductResponse product2 = ProductResponse.builder()
                .brand("Adidas")
                .productName("Hoodie")
                .build();
        ProductResponse product3 = ProductResponse.builder()
                .brand("Diesel")
                .productName("Jeans")
                .build();
        when(platform1.search(analyzeResponse))
                .thenReturn(List.of(product1,product2));
        when(platform2.search(analyzeResponse))
                .thenReturn(List.of(product3));

        List<ProductResponse> result = searchPlatformService.search(analyzeResponse);

        Assertions.assertThat(result).hasSize(3);
        Assertions.assertThat(result)
                .extracting(ProductResponse::getBrand)
                .containsExactly("Nike","Adidas","Diesel");
        verify(platform1).search(analyzeResponse);
        verify(platform2).search(analyzeResponse);
    }

}