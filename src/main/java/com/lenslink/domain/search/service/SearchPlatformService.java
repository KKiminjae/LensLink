package com.lenslink.domain.search.service;

import com.lenslink.domain.search.Mall;
import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SearchPlatformService {

    private String createKeyword(AnalyzeResponse analyzeResponse){

        return analyzeResponse.getBrand()
                + " "
                + analyzeResponse.getProductName();
    }

    public List<ProductResponse> search(AnalyzeResponse analyzeResponse){
        String encodeKeyword = URLEncoder.encode(createKeyword(analyzeResponse), StandardCharsets.UTF_8);

        ProductResponse musinsa = ProductResponse.builder()
                .brand(analyzeResponse.getBrand())
                .price(59000)
                .productName(analyzeResponse.getProductName())
                .mall(Mall.MUSINSA.getDisplayName())
                .productUrl(Mall.MUSINSA.getSearchUrl() + encodeKeyword)
                .imageUrl("http://...")
                .soldOut(false)
                .build();
        ProductResponse kream = ProductResponse.builder()
                .brand(analyzeResponse.getBrand())
                .price(61000)
                .productName(analyzeResponse.getProductName())
                .mall(Mall.KREAM.getDisplayName())
                .productUrl(Mall.KREAM.getSearchUrl() + encodeKeyword)
                .imageUrl("http://...")
                .soldOut(false)
                .build();
        ProductResponse fruitsFamily = ProductResponse.builder()
                .brand(analyzeResponse.getBrand())
                .productName(analyzeResponse.getProductName())
                .mall(Mall.FRUITS_FAMILY.getDisplayName())
                .productUrl(Mall.FRUITS_FAMILY.getSearchUrl() + encodeKeyword)
                .imageUrl("http://...")
                .soldOut(true)
                .build();
        return List.of(musinsa,kream,fruitsFamily);
    }
}
