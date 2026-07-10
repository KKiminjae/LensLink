# LensLink

이미지를 업로드하면 OpenAI가 의류 정보를 분석하고,
여러 쇼핑몰에서 동일하거나 유사한 상품을 검색하는
이미지 기반 상품 검색 백엔드 프로젝트입니다.

# 기술 스택
- Java 21
- Spring Boot
- Spring WebFlux(WebClient)
- OpenAI API
- Jackson

# 프로젝트 구조

Image Upload
        │
        ▼
OpenAI API
        │
        ▼
AnalyzeResponse
        │
        ▼
SearchPlatformService
        │
 ┌──────┴────────┐
 ▼               ▼
Musinsa      Kream(예정)

# 구현 현황
- [x] 이미지 업로드
- [x] OpenAI 이미지 분석
- [x] 브랜드 추출
- [x] 상품명 추출
- [x] 검색 키워드 생성
- [x] 무신사 검색 서비스 구조 작성
- [ ] 무신사 상품 조회 API 연동
- [ ] 크림 API 연동

# Troubleshooting
## 무신사 HTML 크롤링 실패
### 문제
처음에는 Jsoup를 이용하여 무신사 검색 페이지를 크롤링하려고 했다.

```java
Document document = Jsoup.connect(url).get();
```
페이지 제목은 정상적으로 가져왔지만 상품 목록은 가져오지 못했다.

### 확인

```java
document.title()
```
DIESEL | 무신사 추천 상품

```java
document.select("li").size()
```
0

```java
document.select("li").first()
```
null

# 원인 분석
무신사는 Next.js 기반으로 동작한다.
하지만 브라우저는 

HTML
   ↓
JavaScript 실행
   ↓
상품 API 호출
   ↓
상품 목록 렌더링

순서로 화면을 만든다.

Jsoup는 HTML만 가져올 뿐 JavaScript를 실행하지 않는다.

따라서 브라우저에서 JavaScript가 실행된 이후 생성되는
상품 목록은 HTML에 존재하지 않아 크롤링할 수 없었다.

# 해결 방향
브라우저의 Network 탭을 분석하여
실제 상품 목록을 반환하는 API를 확인하였다.

HTML 크롤링 대신
Spring WebClient를 이용해 해당 API를 직접 호출하는 방식으로
구현 방향을 변경하였다.


# 배운 점
- HTML과 API 기반 서비스의 차이를 이해하였다.
- Next.js와 CSR(Client Side Rendering)의 동작 방식을 이해하였다.
- 브라우저 Network 분석을 통해 실제 데이터 흐름을 추적하는 방법을 익혔다.








