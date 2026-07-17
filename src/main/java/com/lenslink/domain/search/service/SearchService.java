package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.dto.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final OpenAiService openAiService;

    private final SearchPlatformService searchPlatformService;

    public SearchResponse search(MultipartFile image){

        AnalyzeResponse analyzeResponse = openAiService.analyzeImage(image);

        List<ProductResponse> products = searchPlatformService.search(analyzeResponse);

        List<ProductResponse> newProducts = products.stream()
                .filter(product -> !product.isUsed())
                .toList();
        List<ProductResponse> usedProducts = products.stream()
                .filter(ProductResponse::isUsed)
                .toList();
        return new SearchResponse(
                analyzeResponse,
                newProducts,
                usedProducts
        );
    }
}
