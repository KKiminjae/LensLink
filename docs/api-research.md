# api-research.md

이 문서는 **조사 결과만** 적는다.

# Platform API Research

## 조사 목적

상품 검색 기능 구현을 위해
각 쇼핑 플랫폼의 공식 API 및 내부 API를 조사하였다.

---

# Musinsa

## 공식 API

없음

## 내부 API

Chrome DevTools Network를 통해 확인.

검색 요청 존재.

### 결과

브라우저 환경에서는 정상 동작.

Java(WebClient)에서는 정상 호출 실패.

원인

- 내부 인증
- Header 검증
- Bot 방지 정책

결론

보류

---

# KREAM

## 공식 API

공개되지 않음.

## 조사

검색 페이지 분석

HTML 수집 성공.

__NUXT_DATA__ 발견.

상품 데이터 존재 확인.

Nuxt 직렬화 구조 사용.

결론

추가 분석 진행.

---

# Naver Shopping

## 공식 API

제공

REST API 형태로 쇼핑 검색 기능 지원.

## 조사 결과

- 공식 Search API 사용 가능
- Client ID / Client Secret 기반 인증
- JSON 응답 제공
- 상품명, 가격, 이미지, 쇼핑몰 정보 제공
- 호출 제한 및 사용량 정책 존재

## 선택 이유

- 공식 API 제공
- 안정적인 호출 가능
- 유지보수가 용이
- 프로젝트 요구사항에 적합

## 결론

공식 Search API를 채택하여 상품 검색 기능을 구현하기로 결정.

---

## OpenAI Vision Prompt 개선 실험

### 목적
이미지에서 브랜드와 제품명을 정확하게 추출하여 검색 정확도를 높인다.

### 실험
- Prompt 개선
- searchKeyword 생성 방식 변경
- gpt-4.1-mini ↔ gpt-4.1 비교
- 동일 이미지 반복 테스트

### 결과
- 일부 이미지에서는 브랜드를 맞추기도 했지만
- 브랜드를 잘못 추론하거나 존재하지 않는 브랜드명을 생성하는 사례가 확인됨.
- Prompt만으로는 Google Lens 수준의 상품 식별 정확도를 달성하기 어려움.

### 결론
OpenAI Vision은 검색 보조 도구로 활용하고,
상품 식별 정확도는 추가적인 검색 전략이나 다른 서비스 활용을 검토.

---

## OpenAI 분석 결과 개선

### 문제

OpenAI는 브랜드와 상품명을 영문으로 반환하는 경우가 많았다.

예시

brand
- Rick Owens

productName
- Drkshdw Low-Top Distressed Canvas Sneaker

반면 네이버 쇼핑 검색 결과는 대부분 한글 브랜드와 상품명을 사용하였다.

예시

브랜드
- 릭오웬스

상품명
- 릭오웬스 DRKSHDW 스니커즈 블랙밀크

이로 인해 검색 결과는 존재하지만 품질 평가(SearchResultEvaluator)에서 실패하는 문제가 발생했다.

---

### 조사 결과

OpenAI 프롬프트를 수정하여 한국어 정보를 함께 반환하도록 변경하였다.

추가 필드

- brandKo
- productNameKo

예시

brand
- Rick Owens

brandKo
- 릭오웬스

productName
- Drkshdw Low-Top Distressed Canvas Sneaker

productNameKo
- DRKSHDW 로우탑 디스트레스드 캔버스 스니커즈

이를 통해 한국 쇼핑몰 검색 결과와의 비교 정확도를 향상시켰다.

---