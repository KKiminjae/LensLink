package com.lenslink.domain.search.service.candidate;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.util.SearchNormalizer;
import org.springframework.stereotype.Service;

import java.nio.channels.SeekableByteChannel;
import java.util.*;

@Service
public class SearchCandidateGenerator {
    private static final int MIN_CONFIDENCE = 70;

    public List<String> createCandidates(AnalyzeResponse analyzeResponse){
        Set<String> candidates = new LinkedHashSet<>();

        String brand = analyzeResponse.getBrand();
        String productName = analyzeResponse.getProductName();

        String normalizedBrand = SearchNormalizer.normalize(brand);
        String normalizedProductName = SearchNormalizer.normalize(productName);

        if(!SearchNormalizer.isUnknown(normalizedBrand) && !SearchNormalizer.isUnknown(normalizedProductName)) candidates.add(normalizedBrand + " " + normalizedProductName);

        if(!SearchNormalizer.isUnknown(normalizedProductName)) candidates.add(normalizedProductName);


        List<AnalyzeResponse.SimilarProductResponse> similarProducts = analyzeResponse.getSimilarProducts();
        if(similarProducts != null){
            similarProducts = new ArrayList<>(similarProducts);

            similarProducts.sort(
                    Comparator.comparingInt(
                                    AnalyzeResponse.SimilarProductResponse::getConfidence)
                            .reversed());

            for (AnalyzeResponse.SimilarProductResponse similarProduct : similarProducts) {
                String normalizedSimilarProduct = SearchNormalizer.normalize(similarProduct.getProductName());

                if(similarProduct.getConfidence() >= MIN_CONFIDENCE && !SearchNormalizer.isUnknown(normalizedSimilarProduct)){
                    candidates.add(normalizedSimilarProduct);
                }
            }
        }

        if(!SearchNormalizer.isUnknown(normalizedBrand)) candidates.add(normalizedBrand);

        return new ArrayList<>(candidates);
    }
}
