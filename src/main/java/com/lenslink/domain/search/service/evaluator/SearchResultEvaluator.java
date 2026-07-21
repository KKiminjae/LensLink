package com.lenslink.domain.search.service.evaluator;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchResultEvaluator {
    public boolean isGoodResult(AnalyzeResponse analyzeResponse, List<ProductResponse> products){
        if(products.isEmpty()) {
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

        if(expectBrand == null || expectBrand.isBlank() || expectBrand.equalsIgnoreCase("Unknown"))
        {
            return true;
        }
        for(ProductResponse product: products){
            String actualBrand = product.getBrand();

            if(actualBrand == null || actualBrand.isBlank()) {
                continue;
            }

            if(expectBrand.equalsIgnoreCase(actualBrand)) {
                return true;
            }
        }

        return false;
    }

    private boolean isProductMatched(AnalyzeResponse analyzeResponse, List<ProductResponse> products){
        String expectProduct = analyzeResponse.getProductName();

        if(expectProduct == null || expectProduct.isBlank() || expectProduct.equalsIgnoreCase("Unknown"))
        {
            return true;
        }
        for (ProductResponse product : products) {
            String actualProduct = product.getProductName();

            if(actualProduct == null || actualProduct.isBlank()) {
                continue;
            }

            if(actualProduct.toLowerCase().contains(expectProduct.toLowerCase())){
                {
                    return true;
                }
            }
        }
        return false;
    }
}
