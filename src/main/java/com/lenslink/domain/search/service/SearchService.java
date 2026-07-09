package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final OpenAiService openAiService;

    private final SearchPlatformService searchPlatformService;

    public List<ProductResponse> search(MultipartFile image){
        AnalyzeResponse analyzeResponse = openAiService.analyzeImage(image);
        return searchPlatformService.search(analyzeResponse);
    }
}
