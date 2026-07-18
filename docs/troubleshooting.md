# Troubleshooting

## 1.

### 문제

WebClient 요청 시

500 Internal Server Error

발생.

---

### 원인

브라우저 Header 부족.

---

### 해결

User-Agent

Accept

Referer

Accept-Language

추가.

HTML 수신 성공.

---

## 2.

### 문제

__NUXT_DATA__를 찾지 못함.

---

### 해결

HTML에서
```html
<script id = "__NUXT_DATA__">
 ```

추출 메서드 작성.

---


## 3.

### 문제

Nuxt 구조를 이해하기 어려움.

---

### 원인

Nuxt SSR Serialization.

Reference 기반 저장.

---

### 현재 상태

분석 진행 중.

## 4.

### 문제

무신사 API는 인증 및 내부 헤더(HMAC) 문제로 직접 호출이 어려움

---

### 해결

공식 NAVER Shopping Search API를 도입하여
안정적인 상품 검색 기능을 구현함.

---

### 배운 점

- WebClient를 이용한 외부 API 호출
- JSON → DTO 자동 변환
- DTO Mapping
- SearchPlatform 기반 확장 가능한 구조 설계

---

## 5.

### 문제

기존 SearchService와 SearchController는

List<ProductResponse>

를 반환하도록 구현되어 있었음.

SearchResponse를 도입하면서 반환 타입이 변경되어
Service와 Controller의 반환 타입이 서로 일치하지 않는 문제가 발생.

---

### 해결

SearchService의 반환 타입을 SearchResponse로 변경.

상품 검색 결과를

- newProducts
- usedProducts

로 분리한 뒤

SearchResponse에 담아 반환하도록 수정.

Controller 역시 반환 타입을 SearchResponse로 변경하여
API 응답 구조를 일치시킴.

## 6.

### 문제

Flutter에서 검색 결과를 파싱하는 과정에서 

```text
type 'int' is not a subtype of type 'String'
```

다음 오류가 발생하였다.

### 원인

Spring Boot의 ProductResponse는 price를 int로 반환하지만,
Flutter 모델은 String으로 선언되어 있었다.

### 해결

Flutter Product 모델의 price 타입을 int로 수정하고,
UI에서 문자열로 변환하여 출력하도록 변경하였다.