# Technical Decisions

## SearchPlatform 인터페이스를 만든 이유

플랫폼마다 검색 방식, 응답 구조, API가 모두 다르다.

공통 인터페이스를 사용하여
플랫폼이 추가되어도 기존 코드를 수정하지 않는
확장 가능한 구조를 선택하였다.

---

## Musinsa를 보류한 이유

공식 API는 제공되지 않았으며,
내부 API는 브라우저 환경 의존성이 높았다.

유지보수 비용과 안정성을 고려하여
우선순위를 낮추었다.

---

## KREAM을 조사한 이유

HTML에 SSR 데이터(__NUXT_DATA__)가 포함되어 있었다.

공식 API가 없어도
검색 기능 구현 가능성이 있다고 판단하였다.

---

## 공식 API 우선 전략

역공학 기반 구현보다

공식 API를 우선 연동하여

안정성과 유지보수성을 확보하기로 결정하였다.

---

## NAVER Shopping API를 채택한 이유

NAVER Shopping Search API는
공식적으로 지원되는 REST API이며,

WebClient를 이용한 연동이 쉽고,

현재 프로젝트의 SearchPlatform 구조와도
자연스럽게 통합할 수 있었다.

따라서 상품 검색 기능은
NAVER Shopping API를 우선 적용하기로 결정하였다.