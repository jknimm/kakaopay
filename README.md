# pay


## Spec
- Framework: `Spring boot 2.3.1.RELEASE` / `Spring Data JPA`
- Build: Gradle
- DataBase: `H2 Database` / `Memory DB`
- Schema 구성 : `Flyway` <br>
  ※ 어플리케이션 실행 시 메모리 DB에 자동으로 Schema를 생성하므로, 추가 DB 구성은 필요하지 않습니다 <br>
  ※ Http 실행포트 8080

※ 테스트 실행시에는
```
test 용 프로파일을 사용해서 자동으로 실행되며,
사전에 정의해놓은 테스트를 위한 데이터가 자동으로 DB에 저장되어 동작합니다.
``` 

## 문제 해결 전략
**1. 핵심로직**
 - 분배금액 : 분배 회차별, 총금액 80%범위에서, 추가 랜덤비율(1~99%) 계산하여 인원별 배분
 - 마지막 분배 회차 : 총금액에서 이전회차 금액SUM을 빼고, 남은금액으로 편성
 - 뿌리기 시 SEND_MONEY 테이블에 1건, RCV_MONEY 테이블에 분배인원수 만큼 데이터 INSERT
 
<br>**2. 스키마 구성**
 1) SEND_MONEY (뿌리기 서비스)
   - 뿌기리한 사용자 및 뿌린정보(뿌린 총금액, 분배인원) 관리
 2) RCV_MONEY (받기 서비스)
   - 뿌리기 분배인원에 맞게 금액분배한 데이터
   - 받기 처리시, user_Id 업데이트 하여 관리 

<br>**3. API 구성**
 1) 뿌리기 API
 ```
POST api/v1/money/{money}/{numberOfPeople}
```
 2) 받기 API
 ```
GET /api/v1/money/{token}
```
 3) 조회 API
 ````
 GET /api/v1/money/history/{token}
````

## API Spec
#### ERROR Response 포맷
>IETF  RFC 7807 기반 (https://tools.ietf.org/html/rfc7807)
`````
{
"timestamp": "2020-06-27T04:20:03.282+00:00", // 발생시간
"type": "2001", // 에러코드
"title": "DUPLICATED_RCV", // 에러 요약
"detail": "받기는 한번만 가능합니다.", // 에러 설명
"instance": null // error 발생 uri (미구현)
}
`````
#### ERROR CODE 정의

|  <center>type</center> |  <center>title</center> |  <center>detail</center> |
|:--------|:--------:|--------:|
|**1001** | <center>NOT_EXIST_TOKEN</center> |<center>The token which you request not exist.</center>  |
|**2001** | <center>DUPLICATED_RCV </center> |<center>받기는 한번만 가능합니다.</center>  |
|**2002** | <center>MY_MONEY </center> |<center>자신의 뿌리기 건은 받을 수 없습니다.</center>  |
|**2003** | <center>NOT_MEMBER </center> |<center>뿌리기된 대화방의 사용자가 아닙니다.</center>  |
|**2004** | <center>EXPIRED </center> |<center>뿌린 후 10분 이내만 유효합니다.</center>  |
|**3001** | <center>NOT_MY_NOMEY </center> |<center>뿌린사람 자신만 조회 가능합니다.</center>  |
|**3002** | <center>BEFORE_WEEKS </center> |<center>뿌린 후 7일간만 조회 가능합니다.</center>  |
|**9999** | <center>INVALID_REQUEST </center> |<center>서비스 에러입니다.</center>  |
