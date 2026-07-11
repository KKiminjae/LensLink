package com.lenslink.domain.search.service.platform;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;

import java.util.List;

public interface SearchPlatform {
    List<ProductResponse> search(AnalyzeResponse analyzeResponse);
}
