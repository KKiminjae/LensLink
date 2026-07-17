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