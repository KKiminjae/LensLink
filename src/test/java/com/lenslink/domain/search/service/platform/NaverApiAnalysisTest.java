package com.lenslink.domain.search.service.platform;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
@SpringBootTest
public class NaverApiAnalysisTest {
    private WebClient webClient;

    public NaverApiAnalysisTest(@Qualifier("naverWebClient") WebClient webClient) {
        this.webClient = webClient;
    }

    @Test
    void PrintJson(){
        String response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/search/shop.json")
                        .queryParam("query", "디젤 진청 데님 워시드레귤러 크롭 진 남자 청바지 26 수지네 구제")
                        .queryParam("display", 5)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        System.out.println(response);
    }
}
