package com.lenslink.domain.search.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lenslink.domain.search.dto.AnalyzeResponse;
import com.lenslink.domain.search.dto.OpenAi.OpenAIRequest;
import com.lenslink.domain.search.dto.OpenAi.OpenAIResponse;
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

        // request는 우리가 openAi로 보내는 데이터 (요청)
        // 우리는 json을 모르기에 객체로 일단 만듦
        OpenAIRequest.Content textContent =
                new OpenAIRequest.InputText("""
                        당신은 패션 상품을 식별하는 AI가 아닙니다.
                        
                        당신의 목표는 온라인 쇼핑몰에서 동일 상품을 가장 정확하게 검색할 수 있도록
                        공식 브랜드명, 공식 제품명, 모델명을 최대한 추론하는 것입니다.
                        
                        상품의 모델명을 찾는 것이 가장 중요한 목표입니다.
                        
                        브랜드는 반드시 이미지에서 확인 가능한 정보만 사용하세요.
                        
                        브랜드를 추측하거나 만들어내지 마세요.
                        
                        브랜드명이 명확하게 읽히지 않으면
                        Unknown을 반환하세요.
                        
                        제품의 특징(로고, 프린트, 토캡, 끈, 밑창, 실루엣, 포켓, 스티치 등)을
                        활용하여 가장 가능성이 높은 모델명을 추론하세요.
                        
                         규칙
                        
                         - 반드시 JSON만 출력하세요.
                         - Markdown이나 설명은 출력하지 마세요.
                         - 브랜드는 최대한 정확하게 추론하세요.
                         - 브랜드를 알 수 없으면 "Unknown"을 입력하세요.
                         - 제품명은 가능한 공식 상품명을 추론하세요.
                         - 모델명이나 컬렉션명이 보이면 반드시 포함하세요.
                         - 색상은 검색 정확도 향상에 도움이 될 경우만 포함하세요.
                         - confidence는 0~100 사이의 정수입니다.
                         - searchKeyword는 쇼핑몰 검색창에 그대로 입력할 수 있는 가장 정확한 검색어 하나만 생성하세요.
                         - searchKeyword에는 브랜드와 제품명을 반드시 포함하세요.
                         - 일반적인 표현(남성 의류, 캐주얼 티셔츠 등)은 사용하지 마세요.
                         - 제품명은 일반적인 이름이 아니라
                         - 공식 모델명을 최대한 추론하세요.
                         - 모델명이 확실하지 않다면
                           브랜드에서 실제 판매되는 모델명을 추론하세요.
                         - 로고, 밑창, 끈, 토캡, 실루엣 등
                           제품의 특징을 적극적으로 활용하세요.
                         - similarProducts에는
                           가능성이 높은 모델명을 confidence 순으로 최대 5개까지 제시하세요.
                        
                         다음 JSON 형식으로만 응답하세요.
                        
                         {
                           "brand": "",
                           "productName": "",
                           "color": "",
                           "category": "",
                           "confidence": 0,
                           "searchKeyword": "",
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
            .model("gpt-4.1")
            .input(List.of(input))
            .build();

        OpenAIResponse response = openAiWebClient.post()
                .uri("/responses")
                .bodyValue(request) //여기서 json으로 변경
                .retrieve()
                .bodyToMono(OpenAIResponse.class) // json을 OpenAIResponse로 변경
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
