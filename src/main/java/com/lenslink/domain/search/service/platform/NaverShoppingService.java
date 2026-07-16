package com.lenslink.domain.search.service.platform;

import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.NaverShoppingResponse;
import com.lenslink.domain.search.dto.ProductResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NaverShoppingService implements SearchPlatform{
    private final WebClient webClient;

    public NaverShoppingService(@Qualifier("naverWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public List<ProductResponse> search(AnalyzeResponse analyzeResponse){
        try {
            String query = analyzeResponse.getSearchKeyword();

            NaverShoppingResponse response = webClient.get()
                    .uri(UriBuilder -> UriBuilder
                            .path("/v1/search/shop.json")
                            .queryParam("query", query)
                            .queryParam("display", 5)
                            .build())
                    .retrieve()
                    .bodyToMono(NaverShoppingResponse.class)
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
        return ProductResponse.builder()
                .brand(item.getBrand())
                .price(Integer.parseInt(item.getLprice()))
                .mall(item.getMallName())
                .productUrl(item.getLink())
                .imageUrl(item.getImage())
                .productName(removeHtml(item.getTitle()))
                .build();
    }

    private String removeHtml(String text){
        return text.replaceAll("<[^>]*>","");
    }
}
