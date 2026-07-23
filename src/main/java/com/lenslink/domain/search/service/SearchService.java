package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.dto.SearchResponse;
import com.lenslink.domain.search.entity.SearchHistory;
import com.lenslink.domain.search.repository.SearchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchService {

    private final OpenAiService openAiService;

    private final SearchPlatformService searchPlatformService;

    private final SearchHistoryRepository repository;

    public SearchResponse search(MultipartFile image){

        AnalyzeResponse analyzeResponse = openAiService.analyzeImage(image);

        System.out.println("브랜드 한국이름 = " + analyzeResponse.getBrandKo() + " , 제품 한국 검색: " + analyzeResponse.getProductNameKo());

        SearchHistory history = createSearchHistory(analyzeResponse);
        repository.save(history);

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

    private SearchHistory createSearchHistory(AnalyzeResponse analyzeResponse){
        return SearchHistory.builder()
                .brand(analyzeResponse.getBrand())
                .productName(analyzeResponse.getProductName())
                .searchKeyword(analyzeResponse.getSearchKeyword())
                .build();
    }
}
