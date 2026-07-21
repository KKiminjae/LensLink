package com.lenslink.domain.search.service.platform;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.NaverShoppingResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.service.candidate.SearchCandidateGenerator;
import com.lenslink.domain.search.service.evaluator.SearchResultEvaluator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.*;

@Slf4j
@Service
public class NaverShoppingService implements SearchPlatform{
    private final WebClient webClient;
    private final SearchResultEvaluator searchResultEvaluator;
    private final SearchCandidateGenerator candidateGenerator;

    public NaverShoppingService(
            @Qualifier("naverWebClient") WebClient webClient,
            SearchResultEvaluator searchResultEvaluator,
            SearchCandidateGenerator candidateGenerator) {
        this.webClient = webClient;
        this.searchResultEvaluator = searchResultEvaluator;
        this.candidateGenerator = candidateGenerator;
    }

    @Override
    public List<ProductResponse> search(AnalyzeResponse analyzeResponse) {
        List<String> candidates = candidateGenerator.createCandidates(analyzeResponse);

        for (String keyword : candidates) {
            List<ProductResponse> products = searchByKeyword(keyword);

            if(searchResultEvaluator.isGoodResult(analyzeResponse, products)){
                return products;
            }
        }
        return List.of();
    }

    private List<ProductResponse> searchByKeyword(String query){
        try {
            NaverShoppingResponse response = webClient.get()
                    .uri(UriBuilder -> UriBuilder
                            .path("/v1/search/shop.json")
                            .queryParam("query", query)
                            .queryParam("display", 5)
                            .build())
                    .retrieve()
                    .bodyToMono(NaverShoppingResponse.class) //잠깐수정
                    .block();
            if (response == null || response.getItems() == null) {
                return List.of();
            }

            List<ProductResponse> products = new ArrayList<>();

            for (NaverShoppingResponse.Item item : response.getItems()) {
                products.add(toProductResponse(item));
            }
            return products;
        } catch (WebClientResponseException e){
            log.error("네이버 API 응답 오류. status={}", e.getStatusCode(), e);
            return List.of();
        } catch (WebClientRequestException e){
            log.error("네이버 API 연결 실패. message={}", e.getMessage(), e);
            return List.of();
        }
    }

    private ProductResponse toProductResponse(NaverShoppingResponse.Item item){
        int productType = Integer.parseInt(item.getProductType());
        boolean used = productType>=4 && productType <6;

        return ProductResponse.builder()
                .brand(item.getBrand())
                .price(Integer.parseInt(item.getLprice()))
                .mall(item.getMallName())
                .platform("Naver Shopping")
                .productUrl(item.getLink())
                .imageUrl(item.getImage())
                .productName(removeHtml(item.getTitle()))
                .used(used)
                .build();
    }

    private String removeHtml(String text){
        return text.replaceAll("<[^>]*>","");
    }
}
