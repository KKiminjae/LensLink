package com.lenslink.domain.search.service.evaluator;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchResultEvaluatorTest {

    private SearchResultEvaluator searchResultEvaluator;

    @BeforeEach
    void setUp(){
        searchResultEvaluator = new SearchResultEvaluator();
    }
    @Test
    void 검색결과_없으면_false(){
        AnalyzeResponse analyzeResponse = AnalyzeResponse.builder()
                .brand("Nike")
                .productName("Air Force 1")
                .build();
        boolean result = searchResultEvaluator.isGoodResult(analyzeResponse, List.of());

        assertFalse(result);
    }

    @Test
    void 상품명_브랜드_모두일치_true(){
        AnalyzeResponse analyzeResponse = AnalyzeResponse.builder()
                .brand("Nike")
                .productName("Air Force 1")
                .build();
        ProductResponse product = ProductResponse.builder()
                .brand("NIKE")
                .productName("Nike Air Force 1 '07")
                .build();
        boolean result = searchResultEvaluator.isGoodResult(analyzeResponse,List.of(product));

        assertTrue(result);
    }

    @Test
    void 브랜드_일치x_false(){
        AnalyzeResponse analyzeResponse = AnalyzeResponse.builder()
                .brand("Nike")
                .productName("Air Force 1")
                .build();
        ProductResponse product = ProductResponse.builder()
                .brand("adidas")
                .productName("Nike Air Force 1 '07")
                .build();
        boolean result = searchResultEvaluator.isGoodResult(analyzeResponse,List.of(product));

        assertFalse(result);
    }
    @Test
    void 제품명_일치x_false(){
        AnalyzeResponse analyzeResponse = AnalyzeResponse.builder()
                .brand("Nike")
                .productName("Air Force 1")
                .build();
        ProductResponse product = ProductResponse.builder()
                .brand("Nike")
                .productName("Nike Duck Low")
                .build();
        boolean result = searchResultEvaluator.isGoodResult(analyzeResponse,List.of(product));

        assertFalse(result);
    }
    @Test
    void 브랜드_Unknow이면_브랜드검사_생략(){
        AnalyzeResponse analyzeResponse = AnalyzeResponse.builder()
                .brand("Unknown")
                .productName("Unknown")
                .build();
        ProductResponse product = ProductResponse.builder()
                .brand("Nike")
                .productName("Nike Duck Low")
                .build();
        boolean result = searchResultEvaluator.isGoodResult(analyzeResponse,List.of(product));

        assertTrue(result);
    }

    @Test
    void 제품명_Unknow이면_제품명검사_생략(){
        AnalyzeResponse analyzeResponse = AnalyzeResponse.builder()
                .brand("Nike")
                .productName("Unknown")
                .build();
        ProductResponse product = ProductResponse.builder()
                .brand("Nike")
                .productName("Nike Duck Low")
                .build();
        boolean result = searchResultEvaluator.isGoodResult(analyzeResponse,List.of(product));

        assertTrue(result);
    }
}