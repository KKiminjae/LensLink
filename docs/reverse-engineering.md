# Reverse Engineering

## 목적

공식 API가 없는 플랫폼의
검색 데이터를 획득하기 위한 조사 과정.

---

# Musinsa

## 1차

DevTools Network 분석.

검색 API 발견.

브라우저에서는 정상 동작.

---

## 2차

Header 하나씩 제거.

hmacId 제거 시 요청 실패.

필수 Header 존재 확인.

---

## 3차

Java WebClient로 요청 재현.

브라우저 없이 호출 실패.

### 결론

브라우저 의존성 확인.

보류.

---

# KREAM

## HTML 수집

WebClient 사용.

HTML 정상 수신.

---

## __NUXT_DATA__

SSR 데이터 확인.

상품 데이터 존재 확인.

contains()

- product_id
- image_url
- price

모두 true.

---

## JSON 분석

ObjectMapper

JsonNode

Nuxt 참조 구조 확인.

ShallowReactive

Reference 기반 구조 확인.