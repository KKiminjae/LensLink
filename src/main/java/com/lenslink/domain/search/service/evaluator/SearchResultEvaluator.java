package com.lenslink.domain.search.service.evaluator;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.util.SearchNormalizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SearchResultEvaluator {
    public boolean isGoodResult(AnalyzeResponse analyzeResponse, List<ProductResponse> products){
        //products가 없으면
        if(products == null || products.isEmpty()) {
            return false;
        }
        boolean brandMatched = isBrandMatched(analyzeResponse, products);
        boolean productMatched = isProductMatched(analyzeResponse, products);
        log.info("brandMatched={}, productMatched={}", brandMatched, productMatched);
        return brandMatched && productMatched;
    }

    private boolean isBrandMatched(AnalyzeResponse analyzeResponse, List<ProductResponse> products){
        String expectBrand = SearchNormalizer.normalize(analyzeResponse.getBrand());
        String expectBrandKo = SearchNormalizer.normalize(analyzeResponse.getBrandKo());

        //브랜드를 모르면 통과
        if(SearchNormalizer.isUnknown(expectBrand) && SearchNormalizer.isUnknown(expectBrandKo)) {
            return true;
        }
        for(ProductResponse product: products){
            String actualBrand = SearchNormalizer.normalize(product.getBrand());

            if(actualBrand == null || actualBrand.isBlank()) continue;

            if(matches(actualBrand,expectBrand)
            || matches(actualBrand, expectBrandKo)) {
                return true;
            }
        }
        return false;
    }

    private boolean isProductMatched(AnalyzeResponse analyzeResponse, List<ProductResponse> products){
        String expectProduct = SearchNormalizer.normalize(analyzeResponse.getProductName());
        String expectProductKo = SearchNormalizer.normalize(analyzeResponse.getProductNameKo());
        //제품명을 모르면 통과
        if(SearchNormalizer.isUnknown(expectProduct) && SearchNormalizer.isUnknown(expectProductKo)) {
            return true;
        }
        for (ProductResponse product : products) {
            String actualProduct = SearchNormalizer.normalize(product.getProductName());
            if(actualProduct == null || actualProduct.isBlank()) {
                continue;
            }

            if(containKeywords(actualProduct, expectProduct)
            || containKeywords(actualProduct,expectProductKo)) {
                return true;
            }
        }
        return false;
    }

    private boolean matches(String actual, String expected){
        return !SearchNormalizer.isUnknown(expected)
                && actual.contains(expected);
    }

    private boolean containKeywords(String actual, String expected){

        if(SearchNormalizer.isUnknown(expected)){
            return false;
        }
        int matched = 0;

        for(String token: expected.split("\\s+")){
            if(token.length() < 3){
                continue;
            }
            if(actual.contains(token)){
                matched ++;
            }
        }
        return matched >=2;
    }
}
