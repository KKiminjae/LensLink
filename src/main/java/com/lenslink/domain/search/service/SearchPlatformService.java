package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.service.platform.SearchPlatform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchPlatformService {

    private final List<SearchPlatform> platforms;

    public List<ProductResponse> search(AnalyzeResponse analyzeResponse){
        List<ProductResponse> result = new ArrayList<>();

        for (SearchPlatform platform : platforms) {
            try{
                result.addAll(platform.search(analyzeResponse));
            } catch (Exception e){
                log.error("플랫폼 검색 실패: {}",
                        platform.getClass().getSimpleName(),e);
            }
        }
        return result;
    }
}
