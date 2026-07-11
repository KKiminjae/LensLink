package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.service.platform.SearchPlatform;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPlatformService {

    private final List<SearchPlatform> platforms;

    public List<ProductResponse> search(AnalyzeResponse analyzeResponse){
        List<ProductResponse> products = new ArrayList<>();

        for (SearchPlatform platform : platforms) {
            products.addAll(platform.search(analyzeResponse));
        }
        return products;
    }
}
