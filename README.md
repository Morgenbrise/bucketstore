# BucketStore 과제 - 쇼핑몰 백엔드 API

본 프로젝트는 주문, 상품, 배송 기능을 포함한 쇼핑몰 백엔드 API 구현 과제입니다.  
Spring Boot 3.5, Java 20, JPA, QueryDSL, Swagger(OpenAPI 3)를 기반으로 개발되었으며, 테스트 코드는 JUnit5 기반입니다.

---

## 1. 개발 환경

| 항목           | 버전 및 도구              |
|----------------|---------------------------|
| Java           | 20                        |
| Spring Boot    | 3.5.x                     |
| Gradle         | 8.x                       |
| Database       | MySQL 8.x                 |
| ORM            | JPA (Hibernate 6.x)       |
| Query          | QueryDSL 5.x              |
| 테스트         | JUnit 5                   |
| 문서화         | springdoc-openapi v2      |

---

## 2. 주요 기능

### [상품 기능]
- 상품 목록 조회 (정렬 필드 다중 지원)
- 상품 정렬 필드: 등록일, 가격, 브랜드, 이름 등

### [주문 기능]
- 상품 코드, 옵션, 수량, 사이즈를 포함한 주문 등록
- 주문 상세 조회 (상품, 옵션, 상태 포함)
- 주문 항목 단건 또는 다건 취소
- 전체 항목 취소 시 주문 상태 변경 (예: PENDING → CANCELED)

---

## 3. 실행 방법

### 3.1 데이터베이스 설정

```sql
CREATE DATABASE [데이터베이스] CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER '[계정]'@'%' IDENTIFIED BY '[패스워드]';
GRANT ALL PRIVILEGES ON [데이터베이스].* TO '[계정]'@'%';
```
### 3.2 application.yml 예시
```yml
datasource:
  mysql:
    jdbc-url: jdbc: [주소]
    username:  [계정]
    password: [패스워드]
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 3.3 MySQL 백업 (Raw 링크)
- [MySQL 덤프 파일 다운로드](./dump/bucketstore_backup.sql)

### 3.4 실행
```bash
./gradlew bootRun
```

## 4 테스트

### 테스트 실행
```bash
./gradlew test
```

### 테스트 데이터
src/test/resources/data.sql에 초기 테스트용 데이터 작성
테스트 실행 시 자동 삽입

## 5. Swagger 문서

Swagger UI는 다음 URL에서 확인할 수 있습니다

http://localhost:8088/swagger-ui/index.html


### 문서화된 주요 API

| HTTP Method | Endpoint                      | 설명                |
|-------------|-------------------------------|---------------------|
| GET         | `/api/products`               | 상품 목록 조회       |
| POST        | `/api/orders`                 | 주문 생성            |
| DELETE      | `/api/orders/{id}/items`      | 주문 항목 취소       |
| GET         | `/api/orders/{id}`            | 주문 상세 조회       |
| GET         | `/api/orders`                 | 주문 목록 조회       |


