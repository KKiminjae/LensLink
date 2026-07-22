# Architecture

## 프로젝트 목적

LensLink는 이미지를 업로드하면 AI가 상품 정보를 분석하고,
여러 쇼핑 플랫폼에서 동일하거나 유사한 상품을 검색하는 백엔드 프로젝트이다.

---

## 전체 흐름
```
Flutter
      │
      ▼
POST /api/searches/analyze
      │
      ▼
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
      ├──────────────┐
      ▼              │
SearchHistory 저장    │
(MySQL)              │
      │              │
      ▼              │
SearchPlatformService
      │
      ▼
NaverShoppingService
      │
      ├── SearchCandidateGenerator
      │       │
      │       ▼
      │   검색 후보 생성
      │
      ├── Naver Shopping API 호출
      │
      ├── SearchResultEvaluator
      │       │
      │       ▼
      │   검색 결과 평가
      │
      ▼
List<ProductResponse>
      │
      ▼
Flutter ResultPage
```

---


## 설계 목표

- 플랫폼별 검색 로직 분리
- 새로운 플랫폼 추가가 쉬운 구조
- SearchPlatform 인터페이스 기반 확장
- OpenAI와 검색 로직 분리
- 검색 후보 생성과 결과 평가 책임 분리
- 문자열 정규화 규칙을 공통으로 관리
- 공통 SearchResponse 반환
- SearchService에서 새상품과 중고 상품을 분리하여 프론트에 전달
- 검색 이력을 DB에 저장하여 추후 조회 및 분석 기능 확장 가능

---

## 핵심 컴포넌트

| 컴포넌트 | 역할 |
|----------|------|
| SearchService | 전체 검색 흐름을 조합하고 검색 이력을 저장 |
| SearchPlatform | 플랫폼별 검색 인터페이스 |
| SearchPlatformService | 등록된 플랫폼을 순회하며 검색 수행 |
| SearchCandidateGenerator | OpenAI 분석 결과를 기반으로 검색 후보 생성 |
| SearchNormalizer | 검색어와 검색 결과 문자열 정규화 |
| SearchResultEvaluator | 검색 결과의 품질을 평가하여 재검색 여부 결정 |
| SearchHistory | 검색 이력 저장 Entity |
| SearchHistoryRepository | 검색 이력 저장 및 조회 |

## Recent Search API

## 전체 흐름

```
Flutter
        │
        ▼
GET /api/searches/history
        │
        ▼
SearchController
        │
        ▼
SearchHistoryService
        │
        ▼
SearchHistoryRepository
        │
        ▼
MySQL
        │
        ▼
List<SearchHistory>
        │
        ▼
SearchHistoryResponse
        │
        ▼
JSON Response
```