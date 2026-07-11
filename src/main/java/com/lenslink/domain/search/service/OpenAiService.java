package com.lenslink.domain.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.OpenAIRequest;
import com.lenslink.domain.search.dto.OpenAIResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
public class OpenAiService {

    private final WebClient openAiWebClient;
    private final ObjectMapper objectMapper;

    public OpenAiService(@Qualifier("openAiWebClient") WebClient openAiWebClient, ObjectMapper objectMapper) {
        this.openAiWebClient = openAiWebClient;
        this.objectMapper = objectMapper;
    }

    private String convertToBase64(MultipartFile image){
        try {
            byte[] bytes = image.getBytes();
            return Base64.getEncoder().encodeToString(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public AnalyzeResponse analyzeImage(MultipartFile image){

        String base64Image = convertToBase64(image);

        OpenAIRequest.Content textContent =
                new OpenAIRequest.InputText("""
                당신은 패션 상품을 분석하는 AI입니다.

                업로드된 이미지를 분석하여 아래 정보를 추출하세요.

                규칙
                - 반드시 JSON만 출력하세요.
                - 설명이나 Markdown(```)을 포함하지 마세요.
                - 브랜드를 알 수 없으면 "Unknown"을 입력하세요.
                - 상품명을 알 수 없으면 가장 적절한 일반적인 이름을 작성하세요.
                - confidence는 0~100 사이의 정수입니다.

                다음 JSON 형식으로만 응답하세요.

                {
                  "brand": "",
                  "productName": "",
                  "color": "",
                  "category": "",
                  "confidence": 0,
                  "similarProducts": [
                    {
                      "productName": "",
                      "confidence": 0
                    }
                  ]
                }
                """);
        String mimeType = image.getContentType();   // image/png 반환

        OpenAIRequest.Content imageContent =
                new OpenAIRequest.InputImage(
                        "data:" + mimeType + ";base64," + base64Image
                );

        OpenAIRequest.Input input =
        OpenAIRequest.Input.builder()
            .role("user")
            .content(List.of(textContent,imageContent))
            .build();
        OpenAIRequest request =
        OpenAIRequest.builder()
            .model("gpt-4.1-mini")
            .input(List.of(input))
            .build();

        OpenAIResponse response = openAiWebClient.post()
                .uri("/responses")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(OpenAIResponse.class)
                .block();

        String result = response.getOutput()
                .get(0)
                .getContent()
                .get(0)
                .getText();
        try {
            return objectMapper.readValue(result, AnalyzeResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
}
}
