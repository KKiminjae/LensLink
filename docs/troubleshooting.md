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

무신사 API는 인증 및 내부 헤더(HMAC) 문제로 직접 호출이 어려웠다.

---

### 해결

공식 NAVER Shopping Search API를 도입하여
안정적인 상품 검색 기능을 구현하였다.

---

### 배운 점

- WebClient를 이용한 외부 API 호출
- JSON → DTO 자동 변환
- DTO Mapping
- SearchPlatform 기반 확장 가능한 구조 설계