package com.lenslink.domain.search.service.evaluator;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.util.SearchNormalizer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchResultEvaluator {
    public boolean isGoodResult(AnalyzeResponse analyzeResponse, List<ProductResponse> products){
        if(products == null || products.isEmpty()) {
            return false;
        }

        if(!isBrandMatched(analyzeResponse,products)) {
            return false;
        }

        if(!isProductMatched(analyzeResponse,products)) {
            return false;
        }

        return true;
    }

    private boolean isBrandMatched(AnalyzeResponse analyzeResponse, List<ProductResponse> products){
        String expectBrand = analyzeResponse.getBrand();

        if(SearchNormalizer.isUnknown(expectBrand)) {
            return true;
        }
        String normalizedExpectedBrand = SearchNormalizer.normalize(expectBrand);

        for(ProductResponse product: products){
            String actualBrand = product.getBrand();

            if(actualBrand == null || actualBrand.isBlank()) {
                continue;
            }

            String normalizedActualBrand = SearchNormalizer.normalize(actualBrand);

            if(normalizedExpectedBrand.equals(normalizedActualBrand)) {
                return true;
            }
        }

        return false;
    }

    private boolean isProductMatched(AnalyzeResponse analyzeResponse, List<ProductResponse> products){
        String expectProduct = analyzeResponse.getProductName();

        if(SearchNormalizer.isUnknown(expectProduct)) {
            return true;
        }
        String normalizedExpectProduct = SearchNormalizer.normalize(expectProduct);

        for (ProductResponse product : products) {
            String actualProduct = product.getProductName();

            if(actualProduct == null || actualProduct.isBlank()) {
                continue;
            }
            String normalizedActualProduct = SearchNormalizer.normalize(actualProduct);

            if(normalizedActualProduct.contains(normalizedExpectProduct)){
                return true;
            }
        }
        return false;
    }
}
