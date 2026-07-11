package com.lenslink.domain.search.service.platform;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;

import java.util.List;

public class KreamApiService implements SearchPlatform{
    @Override
    public List<ProductResponse> search(AnalyzeResponse analyzeResponse) {
        return List.of();
    }
}
