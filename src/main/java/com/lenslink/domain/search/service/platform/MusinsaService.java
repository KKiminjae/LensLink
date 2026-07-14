package com.lenslink.domain.search.service.platform;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusinsaService implements SearchPlatform{

    @Override
    public List<ProductResponse> search(AnalyzeResponse analyzeResponse) {
        return List.of();
    }
}
