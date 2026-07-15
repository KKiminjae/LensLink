# Architecture

## 프로젝트 목적

LensLink는 이미지를 업로드하면 AI가 상품 정보를 분석하고,
여러 쇼핑 플랫폼에서 동일하거나 유사한 상품을 검색하는 백엔드 프로젝트이다.

---

## 전체 흐름
```
SearchController
       │
       ▼
SearchService
       │
       ▼
OpenAIService
       │ 
       ▼
AnalyzeResponse
       │
       ▼
SearchPlatformService
       │
┌──────┬───────┐
▼      ▼       ▼
NAVER  KREAM  Musinsa
```

---

## 설계 목표

- 플랫폼별 검색 로직 분리
- 새로운 플랫폼 추가가 쉬운 구조
- SearchPlatform 인터페이스 기반 확장
- OpenAI와 검색 로직 분리
- 공통 ProductResponse 반환

---

## 핵심 인터페이스

SearchPlatform

```java
List<ProductResponse> search(AnalyzeResponse response);