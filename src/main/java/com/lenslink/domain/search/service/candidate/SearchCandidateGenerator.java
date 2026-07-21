package com.lenslink.domain.search.service.candidate;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchCandidateGenerator {
    private static final int MIN_CONFIDENCE = 70;

    public List<String> createCandidates(AnalyzeResponse analyzeResponse){
        Set<String> candidates = new LinkedHashSet<>();

        String brand = analyzeResponse.getBrand();
        String productName = analyzeResponse.getProductName();

        if(isSearchableKeyword(brand) && isSearchableKeyword(productName)) candidates.add(brand + " " + productName);

        if(isSearchableKeyword(productName)) candidates.add(productName);


        List<AnalyzeResponse.SimilarProductResponse> similarProducts = analyzeResponse.getSimilarProducts();
        if(similarProducts != null){
            similarProducts = new ArrayList<>(similarProducts);

            similarProducts.sort(
                    Comparator.comparingInt(
                                    AnalyzeResponse.SimilarProductResponse::getConfidence)
                            .reversed());

            for (AnalyzeResponse.SimilarProductResponse similarProduct : similarProducts) {

                if(similarProduct.getConfidence() >= MIN_CONFIDENCE && isSearchableKeyword(similarProduct.getProductName())){
                    candidates.add(similarProduct.getProductName());
                }
            }
        }

        if(isSearchableKeyword(brand)) candidates.add(brand);

        return new ArrayList<>(candidates);
    }

    private boolean isSearchableKeyword(String keyword){
        return keyword != null && !keyword.isBlank() && !keyword.equalsIgnoreCase("Unknown");
    }
}
