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

---

## 7.

### 문제

similarProducts.sort() 호출 시 다음 예외가 발생하였다.
```text
UnsupportedOperationException
```

### 원인

테스트 코드에서 List.of()를 사용하여 similarProducts를 생성하였다.

List.of()는 수정이 불가능한 Immutable List를 반환하므로 sort()를 호출할 수 없었다.

### 해결

정렬 전에 새로운 ArrayList를 생성하여 복사본을 만든 뒤 정렬하도록 수정하였다.

```java
List<AnalyzeResponse.SimilarProductResponse> similarProducts =
new ArrayList<>(analyzeResponse.getSimilarProducts());

similarProducts.sort(
Comparator.comparingInt(
AnalyzeResponse.SimilarProductResponse::getConfidence)
.reversed());
```

---

## 8.

### 문제

상품명이

```text
Nike Air Force 1
```

인데

예상 상품명이

```text
Air Force 1™
```

인 경우 검색 결과가 일치하지 않는 문제가 발생하였다.

또한 브랜드에 포함된

- ®
- ™

등의 특수문자 때문에 동일한 브랜드도 서로 다른 문자열로 인식하였다.

### 원인

단순 문자열 비교를 사용하고 있었기 때문이다.

### 해결

SearchNormalizer를 도입하여

- trim()
- toLowerCase()
- 특수문자 제거
- 공백 정규화

를 공통 적용하였다.

SearchCandidateGenerator와 SearchResultEvaluator 모두 동일한 정규화 규칙을 사용하도록 변경하였다.

---
## 9.

### 문제

검색 결과는 존재하지만 SearchResultEvaluator가 항상 false를 반환하였다.

로그

brandMatched=true

productMatched=false

원인

- OpenAI는 영문 상품명을 반환
- 네이버는 한글 상품명을 사용

또한 쇼핑몰마다 상품명 구성이 서로 달라 문자열 전체 비교가 실패하였다.

---

### 해결

1. OpenAI 프롬프트 수정

추가 필드

- brandKo
- productNameKo

2. SearchResultEvaluator 개선

- 영문/한글 브랜드 모두 비교
- 영문/한글 상품명 모두 비교
- 문자열 전체 비교 대신 토큰 기반 비교 적용

---

### 결과

brandMatched=true

productMatched=true

검색 품질이 개선되어 실제 검색 결과를 정상적으로 반환하였다.

___
## 10.

### 문제

검색 기록 저장 시 다음 오류가 발생하였다.
``` text
Field 'search_keyword' doesn't have a default value
```

### 원인 분석

원인 분석

ProductResponse.imageUrl

정상

SearchHistory.imageUrl

정상

Hibernate INSERT

정상 생성

MySQL에서 INSERT 실패

원인

Entity에서는 searchKeyword 필드를 삭제했지만 MySQL 테이블에는 search_keyword NOT NULL 컬럼이 그대로 남아 있었다.

Hibernate의 ddl-auto=update는 기존 컬럼을 자동으로 삭제하지 않는다.

### 해결

```sql
ALTER TABLE search_history
DROP COLUMN search_keyword;
``` 

### 배운점

* ddl-auto=update는 컬럼 삭제를 수행하지 않는다.
* 스키마 변경 시에는 직접 마이그레이션을 수행해야 한다.
* 로그를 API → DTO → Entity → Hibernate → DB 순서로 확인하면 원인을 빠르게 좁힐 수 있다.

---

## 11.

### 문제

Repository에서 Pageable을 사용하려고 했지만 오류가 발생하였다.

원인은 잘못된 import였다.

잘못된 코드

```java
import java.awt.print.Pageable;
```

---
