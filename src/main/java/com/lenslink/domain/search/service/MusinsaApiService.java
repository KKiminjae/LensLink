package com.lenslink.domain.search.service;

import com.lenslink.domain.search.dto.ProductResponse;
import com.lenslink.domain.search.dto.MusinsaResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class MusinsaApiService {

    public List<ProductResponse> search(String keyword) {

        String url = "https://www.musinsa.com/search/goods?keyword="
                + URLEncoder.encode(keyword, StandardCharsets.UTF_8);

        Document document = null;
        try {
            document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(document.title());
        System.out.println(document.select("li").size());
        System.out.println(
                document.select("li").first()
        );

        return null;
    }
}
