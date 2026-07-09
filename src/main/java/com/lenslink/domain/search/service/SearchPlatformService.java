package com.lenslink.domain.search.service;

import com.lenslink.domain.search.Mall;
import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchPlatformService {

    private String createKeyword(AnalyzeResponse analyzeResponse){

        return analyzeResponse.getBrand()
                + " "
                + analyzeResponse.getProductName();
    }

    private ProductResponse createProduct(Mall mall, AnalyzeResponse analyzeResponse, String encodeKeyword){
         return ProductResponse.builder()
                .productName(analyzeResponse.getProductName())
                .mall(mall.getDisplayName())
                .brand(analyzeResponse.getBrand())
                .productUrl(mall.getSearchUrl() + encodeKeyword)
                .build();
    }

    public List<ProductResponse> search(AnalyzeResponse analyzeResponse){
        String encodeKeyword = URLEncoder
                .encode(createKeyword(analyzeResponse), StandardCharsets.UTF_8);

        List<ProductResponse> products = new ArrayList<>();

        for(Mall mall:Mall.values()){
            products.add(createProduct(
                    mall,
                    analyzeResponse,
                    encodeKeyword
            ));
        }
        return products;
    }
}
