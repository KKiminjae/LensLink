package com.lenslink.domain.search.service.candidate;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.util.SearchNormalizer;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class SearchCandidateGenerator {
    private static final int MIN_CONFIDENCE = 70;

    public List<String> createCandidates(AnalyzeResponse analyzeResponse){
        Set<String> candidates = new LinkedHashSet<>();

        String brand = analyzeResponse.getBrand();
        String productName = analyzeResponse.getProductName();
        productName = removeDuplicatedBrand(brand,productName);

        String normalizedBrand = SearchNormalizer.normalize(brand);
        String normalizedProductName = SearchNormalizer.normalize(productName);

        //첫 번째 조건 제품이름만
        if(!SearchNormalizer.isUnknown(normalizedProductName)) candidates.add(normalizedProductName);

        //두 번째 조건 유사 제품처리
        List<AnalyzeResponse.SimilarProductResponse> similarProducts = analyzeResponse.getSimilarProducts();
        if(similarProducts != null){
            similarProducts = new ArrayList<>(similarProducts);

            similarProducts.sort(
                    Comparator.comparingInt(
                                    AnalyzeResponse.SimilarProductResponse::getConfidence)
                            .reversed());

            for (AnalyzeResponse.SimilarProductResponse similarProduct : similarProducts) {
                String normalizedSimilarProduct = SearchNormalizer.normalize(similarProduct.getProductName());

                //70이상만
                if(similarProduct.getConfidence() >= MIN_CONFIDENCE && !SearchNormalizer.isUnknown(normalizedSimilarProduct)){
                    candidates.add(normalizedSimilarProduct);
                }
            }
        }
        //세 번째 조건 브랜드와 제품이름 모두
        if(!SearchNormalizer.isUnknown(normalizedBrand) && !SearchNormalizer.isUnknown(normalizedProductName)) candidates.add(normalizedBrand + " " + normalizedProductName);

        System.out.println("Candidate = " +  candidates);
        return new ArrayList<>(candidates);
    }

    private String removeDuplicatedBrand(String brand, String productName){
        if(SearchNormalizer.isUnknown(brand)
        || productName == null
        || productName.isBlank()){
            return productName;
        }
        if(productName.length() < brand.length()){
            return productName;
        }
        if(productName.regionMatches(
                true,
                0,
                brand,
                0,
                brand.length())){
            return productName.substring(brand.length()).trim();
        }
        return productName;
    }
}
