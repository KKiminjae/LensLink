package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchPlatformService {

    private final MusinsaApiService musinsaApiService;

    private String createKeyword(AnalyzeResponse analyzeResponse){

        return analyzeResponse.getBrand();
    }

    public List<ProductResponse> search(AnalyzeResponse analyzeResponse){
        String keyword = createKeyword(analyzeResponse);
        return musinsaApiService.search(keyword);
    }
}
