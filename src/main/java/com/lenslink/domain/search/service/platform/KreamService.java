package com.lenslink.domain.search.service.platform;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class KreamService {
    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public KreamService(@Qualifier("kreamWebClient") WebClient webClient, ObjectMapper objectMapper) {
        this.webClient = webClient;
        this.objectMapper = objectMapper;
    }

    public String getHtml(String keyword) {

        String url =
                "https://kream.co.kr/search?keyword="
                        + URLEncoder.encode(keyword, StandardCharsets.UTF_8)
                        + "&tab=products";

        return webClient.get()
                .uri(url)
                .header(HttpHeaders.USER_AGENT,
                        "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/149.0.0.0 Safari/537.36")
                .header(HttpHeaders.ACCEPT,
                        "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8")
                .header(HttpHeaders.ACCEPT_LANGUAGE,
                        "ko-KR,ko;q=0.9")
                .header(HttpHeaders.REFERER,
                        "https://www.google.com/")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    public String extractNuxtData(String html){

        String startTag =
                "<script type=\"application/json\" data-nuxt-data=\"nuxt-app\" data-ssr=\"true\" id=\"__NUXT_DATA__\">";

        int start = html.indexOf(startTag);

        if (start == -1) {
            throw new RuntimeException("__NUXT_DATA__를 찾을 수 없습니다.");
        }

        start += startTag.length();

        int end = html.indexOf("</script>", start);

        return html.substring(start, end);

    }

    public void analyzedNuxtData(String nuxtData) throws Exception {
        JsonNode root = objectMapper.readTree(nuxtData);
        for (JsonNode node : root) {
            if (node.isObject() && node.has("product_id")){
                System.out.println("현재 객체");
                System.out.println(node);

                int index = node.get("product_id").asInt();

                System.out.println("1305번째 데이터");
                System.out.println(root.get(index));
                break;
            }
        }

    }
}
