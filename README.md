![header](https://capsule-render.vercel.app/api?text=JPA를%20활용한%20일정%20시스템&animation=fadeIn&type=venom&color=FFA7A7&fontColor=F15F5F)

## 🌟프로젝트 개요

이 프로젝트는 Spring MVC 패턴을 이용했고 JPA를 활용하여 Database를 관리합니다.

그리고 JWT를 활용하여 사용자 인증과 권한 부여를 통해 보안성을 높였습니다.

마지막으로 RestTemplate를 이용해 외부 API를 호출하고, 외부 시스템과 연동하여 데이터를 가져와서 이용하였습니다.

---

## 💻기술 스택

- Java 17
- Spring Boot 3.3.2
- Spring Boot Validation
- Spring Boot JPA
- Mysql
- Lombok
- JWT
- Git

---

## 🗂 API 명세서

### 일정 등록

일정을 등록합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`POST` 요청

##### Request fields

| Path       | Type         | Description | Required |
|------------|--------------|-------------|----------|
| `title`    | `String`     | 일정 제목    | 필수     |
| `content`  | `String`     | 일정 내용    | 필수     |
| `userId`   | `Long`       | 작성자       | 필수     |
| `userList` | `List<Long>` | 일정참여자    | 필수     |

##### Example request

``` http request
POST http://localhost:9090/api/v1/schedules
Content-Type: application/json

{
    "title" : "제목",
    "content" : "내용",
    "userId" : 1,
    "userList" :[2,3]
}
```

##### Response fields

| Path1    | Path2           | Type      | Description |
|----------|-----------------|-----------|-------------|
| `status` |                 | `int`     | HttpStatus  |
| `body`   |                 | `Object`  |             |
|          | `id`            | `Long`    | 일정 고유식별키    |
|          | `title`         | `String`  | 일정 제목       |
|          | `content`       | `String`  | 일정 내용       |
|          | `weather`       | `String`  | 날씨          |
|          | `userId`        | `Long`    | 유저 고유식별키    |
|          | `userNm`        | `String`  | 유저명         |
|          | `userEmail`     | `String`  | 유저이메일       |
|          | `refUserIds`    | `String`  | 참조유저 식별키    |
|          | `refUserNms`    | `String`  | 참조유저명       |
|          | `refUserEmails` | `String`  | 참조유저이메일     |
|          | `createdAt`     | `String`  | 등록일         |
|          | `modifiedAt`    | `String`  | 수정일         |
| `msg`    |                 | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 8,
        "title": "제목",
        "content": "내용",
        "weather": "Smoky",
        "userId": 1,
        "userNm": "유저",
        "userEmail": "user@test.com",
        "refUserIds": "2,3",
        "refUserNms": "유저1,유저12",
        "refUserEmails": "user1@test.com,user12@test.com",
        "createdAt": "2024년 08월 23일 17시 01분",
        "modifiedAt": "2024년 08월 23일 17시 01분"
    },
    "msg": "성공적으로 등록완료했습니다."
}
```

### 일정 단건조회

일정을 단건 조회합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`GET` 요청

##### Request fields

| Path       | Type         | Description | Required |
|------------|--------------|-------------|----------|
| `id`       | `Long`       | 일정 고유식별키| 필수     |

##### Example request

``` http request
GET http://localhost:9090/api/v1/schedules/8
Content-Type: application/json
```

##### Response fields

| Path1    | Path2           | Type      | Description |
|----------|-----------------|-----------|-------------|
| `status` |                 | `int`     | HttpStatus  |
| `body`   |                 | `Object`  |             |
|          | `id`            | `Long`    | 일정 고유식별키    |
|          | `title`         | `String`  | 일정 제목       |
|          | `content`       | `String`  | 일정 내용       |
|          | `weather`       | `String`  | 날씨          |
|          | `userId`        | `Long`    | 유저 고유식별키    |
|          | `userNm`        | `String`  | 유저명         |
|          | `userEmail`     | `String`  | 유저이메일       |
|          | `refUserIds`    | `String`  | 참조유저 식별키    |
|          | `refUserNms`    | `String`  | 참조유저명       |
|          | `refUserEmails` | `String`  | 참조유저이메일     |
|          | `createdAt`     | `String`  | 등록일         |
|          | `modifiedAt`    | `String`  | 수정일         |
| `msg`    |                 | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 8,
        "title": "제목",
        "content": "내용",
        "weather": "Smoky",
        "userId": 1,
        "userNm": "유저",
        "userEmail": "user@test.com",
        "refUserIds": "2,3",
        "refUserNms": "유저1,유저12",
        "refUserEmails": "user1@test.com,user12@test.com",
        "createdAt": "2024년 08월 23일 17시 01분",
        "modifiedAt": "2024년 08월 23일 17시 01분"
    },
    "msg": "성공적으로 등록완료했습니다."
}
```

### 일정 전체조회(페이징기능)

일정을 전체조회합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`GET` 요청

##### Request fields

| Path       | Type      | Description | Required |
|------------|-----------|-------------|----------|
| `page`     | `Integer` | 페이지번호    | 선택       |
| `size`     | `Integer` | 페이지크기    | 선택       |

##### Example request

``` http request
GET http://localhost:9090/api/v1/schedules?page=1&size=10
Content-Type: application/json
```

##### Response fields

| Path1    | Path2          | Type     | Description |
|----------|----------------|----------|-------------|
| `status` |                | `int`    | HttpStatus  |
| `body`   |                | `Object` |             |
|          | `title`        | `String` | 일정 제목       |
|          | `content`      | `String` | 일정 내용       |
|          | `commentCount` | `String` | 댓글 수        |
|          | `createAt`     | `String` | 등록일         |
|          | `modifiedAt`   | `Long`   | 수정일         |
| `msg`    |                | `String` | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "totalElements": 8,
        "totalPages": 1,
        "size": 10,
        "content": [
            {
                "title": "제목",
                "content": "내용",
                "commentCount": 0,
                "createAt": "2024년 08월 23일 17시 01분",
                "modifiedAt": "2024년 08월 23일 17시 01분"
            },
            {
                "title": "제목",
                "content": "내용",
                "commentCount": 0,
                "createAt": "2024년 08월 23일 16시 35분",
                "modifiedAt": "2024년 08월 23일 16시 35분"
            }
        ],
        "number": 0,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "first": true,
        "last": true,
        "numberOfElements": 8,
        "pageable": {
            "pageNumber": 0,
            "pageSize": 10,
            "sort": {
                "empty": false,
                "sorted": true,
                "unsorted": false
            },
            "offset": 0,
            "paged": true,
            "unpaged": false
        },
        "empty": false
    },
    "msg": "성공적으로 조회완료했습니다."
}
```

### 일정 수정

일정을 수정합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`PATCH` 요청

##### Request fields

| Path       | Type        | Description | Required |
|------------|-------------|-------------|----------|
| `id`       | `Long`      | 일정 고유식별키    | 필수     |
| `title`    | `String`    | 일정 제목    | 필수     |
| `content`  | `String`    | 일정 내용    | 필수     |
| `userList` | `List<Long>` | 일정참여자    | 필수     |

##### Example request

``` http request
PATCH http://localhost:9090/api/v1/schedules/8
Content-Type: application/json

{
    "title" : "제목3",
    "content" : "내용3",
    "userList" :[2,3]
}
```

##### Response fields

| Path1    | Path2           | Type      | Description |
|----------|-----------------|-----------|-------------|
| `status` |                 | `int`     | HttpStatus  |
| `body`   |                 | `Object`  |             |
|          | `id`            | `Long`    | 일정 고유식별키    |
|          | `title`         | `String`  | 일정 제목       |
|          | `content`       | `String`  | 일정 내용       |
|          | `weather`       | `String`  | 날씨          |
|          | `userId`        | `Long`    | 유저 고유식별키    |
|          | `userNm`        | `String`  | 유저명         |
|          | `userEmail`     | `String`  | 유저이메일       |
|          | `refUserIds`    | `String`  | 참조유저 식별키    |
|          | `refUserNms`    | `String`  | 참조유저명       |
|          | `refUserEmails` | `String`  | 참조유저이메일     |
|          | `createdAt`     | `String`  | 등록일         |
|          | `modifiedAt`    | `String`  | 수정일         |
| `msg`    |                 | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 8,
        "title": "제목3",
        "content": "내용3",
        "weather": "Smoky",
        "userId": 1,
        "userNm": "유저",
        "userEmail": "user@test.com",
        "refUserIds": "2,3",
        "refUserNms": "유저1,유저12",
        "refUserEmails": "user1@test.com,user12@test.com",
        "createdAt": "2024년 08월 23일 17시 01분",
        "modifiedAt": "2024년 08월 23일 17시 01분"
    },
    "msg": "성공적으로 등록완료했습니다."
}
```

### 일정 삭제

일정을 삭제합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`DELETE` 요청

##### Request fields

| Path       | Type   | Description | Required |
|------------|--------|-------------|----------|
| `id`       | `Long` | 일정 고유식별키    | 필수     |

##### Example request

``` http request
DELETE http://localhost:9090/api/v1/schedules/1
Content-Type: application/json
```

##### Response fields

| Path1    | Path2           | Type     | Description |
|----------|-----------------|----------|-------------|
| `status` |                 | `int`    | HttpStatus  |
| `body`   |                 | `String` |             |
| `msg`    |                 | `String` | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": "",
    "msg": "성공적으로 삭제완료했습니다."
}
```

### 댓글 저장

댓글을 저장합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`POST` 요청

##### Request fields

| Path         | Type     | Description | Required |
|--------------|----------|------------|----------|
| `content`    | `String` | 일정 내용      | 필수     |
| `userId`     | `Long`   | 작성자고유식별키   | 필수     |
| `scheduleId` | `Long`   | 일정고유식별키    | 필수     |

##### Example request

``` http request
POST http://localhost:9090/api/v1/comments
Content-Type: application/json

{
    "content" : "내용",
    "userId" : 1,
    "scheduleId" : 2
}
```

##### Response fields

| Path1    | Path2            | Type      | Description |
|----------|------------------|-----------|-------------|
| `status` |                  | `int`     | HttpStatus  |
| `body`   |                  | `Object`  |             |
|          | `id`             | `Long`    | 일정 고유식별키    |
|          | `content`        | `String`  | 일정 내용       |
|          | `userNm`         | `String`  | 유저명         |
|          | `scheduleId`     | `Long`    | 일정 고유식별키    |
|          | `scheduleTitle`  | `String`  | 일정제목        |
|          | `scheduleContent` | `String`  | 일정내용        |
|          | `createdAt`      | `String`  | 등록일         |
|          | `modifiedAt`     | `String`  | 수정일         |
| `msg`    |                  | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 2,
        "content": "내용",
        "user_nm": "유저",
        "scheduleId": 2,
        "scheduleTitle": "제목",
        "scheduleContent": "내용",
        "createdAt": "2024년 08월 23일 17시 31분",
        "modifiedAt": "2024년 08월 23일 17시 31분"
    },
    "msg": "성공적으로 등록완료했습니다."
}
```

### 댓글 단건조회

댓글을 단건조회합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`GET` 요청

##### Request fields

| Path         | Type   | Description | Required |
|--------------|--------|-------------|----------|
| `id`         | `Long` | 댓글고유식별키| 필수     |

##### Example request

``` http request
GET http://localhost:9090/api/v1/comments/1
Content-Type: application/json
```

##### Response fields

| Path1    | Path2            | Type      | Description |
|----------|------------------|-----------|-------------|
| `status` |                  | `int`     | HttpStatus  |
| `body`   |                  | `Object`  |             |
|          | `id`             | `Long`    | 일정 고유식별키    |
|          | `content`        | `String`  | 일정 내용       |
|          | `userNm`         | `String`  | 유저명         |
|          | `scheduleId`     | `Long`    | 일정 고유식별키    |
|          | `scheduleTitle`  | `String`  | 일정제목        |
|          | `scheduleContent` | `String`  | 일정내용        |
|          | `createdAt`      | `String`  | 등록일         |
|          | `modifiedAt`     | `String`  | 수정일         |
| `msg`    |                  | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 1,
        "content": "내용",
        "user_nm": "유저",
        "scheduleId": 2,
        "scheduleTitle": "제목",
        "scheduleContent": "내용",
        "createdAt": "2024년 08월 23일 17시 26분",
        "modifiedAt": "2024년 08월 23일 17시 26분"
    },
    "msg": "성공적으로 조회완료했습니다."
}
```

### 댓글 전체조회

댓글을 전체조회합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`GET` 요청

##### Example request

``` http request
GET http://localhost:9090/api/v1/comments
Content-Type: application/json
```

##### Response fields

| Path1    | Path2            | Type     | Description |
|----------|------------------|----------|-------------|
| `status` |                  | `int`    | HttpStatus  |
| `body`   |                  | `List`   |             |
|          | `id`             | `Long`   | 일정 고유식별키    |
|          | `content`        | `String` | 일정 내용       |
|          | `userNm`         | `String` | 유저명         |
|          | `scheduleId`     | `Long`   | 일정 고유식별키    |
|          | `scheduleTitle`  | `String` | 일정제목        |
|          | `scheduleContent` | `String` | 일정내용        |
|          | `createdAt`      | `String` | 등록일         |
|          | `modifiedAt`     | `String` | 수정일         |
| `msg`    |                  | `String` | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": [
        {
            "id": 1,
            "content": "내용",
            "user_nm": "유저",
            "scheduleId": 2,
            "scheduleTitle": "제목",
            "scheduleContent": "내용",
            "createdAt": "2024년 08월 23일 17시 26분",
            "modifiedAt": "2024년 08월 23일 17시 26분"
        },
        {
            "id": 2,
            "content": "내용",
            "user_nm": "유저",
            "scheduleId": 2,
            "scheduleTitle": "제목",
            "scheduleContent": "내용",
            "createdAt": "2024년 08월 23일 17시 31분",
            "modifiedAt": "2024년 08월 23일 17시 31분"
        }
    ],
    "msg": "성공적으로 조회완료했습니다."
}
```

### 댓글 수정

댓글을 수정합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`PATCH` 요청

##### Request fields

| Path      | Type     | Description | Required |
|-----------|----------|-------------|----------|
| `id`      | `Long`   | 댓글고유식별키     | 필수     |
| `content` | `String` | 댓글 내용       | 필수     |

##### Example request

``` http request
PATCH http://localhost:9090/api/v1/comments/1
Content-Type: application/json

{
    "content" : "이게 댓글내용"
}
```

##### Response fields

| Path1    | Path2            | Type      | Description |
|----------|------------------|-----------|-------------|
| `status` |                  | `int`     | HttpStatus  |
| `body`   |                  | `Object`  |             |
|          | `id`             | `Long`    | 일정 고유식별키    |
|          | `content`        | `String`  | 일정 내용       |
|          | `userNm`         | `String`  | 유저명         |
|          | `scheduleId`     | `Long`    | 일정 고유식별키    |
|          | `scheduleTitle`  | `String`  | 일정제목        |
|          | `scheduleContent` | `String`  | 일정내용        |
|          | `createdAt`      | `String`  | 등록일         |
|          | `modifiedAt`     | `String`  | 수정일         |
| `msg`    |                  | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 1,
        "content": "이게 댓글내용",
        "user_nm": "유저",
        "scheduleId": 2,
        "scheduleTitle": "제목",
        "scheduleContent": "내용",
        "createdAt": "2024년 08월 23일 17시 26분",
        "modifiedAt": "2024년 08월 23일 17시 26분"
    },
    "msg": "성공적으로 수정완료했습니다."
}
```

### 댓글 삭제

댓글을 삭제합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`DELETE` 요청

##### Request fields

| Path       | Type   | Description | Required |
|------------|--------|-------------|----------|
| `id`       | `Long` | 일정 고유식별키    | 필수     |

##### Example request

``` http request
DELETE http://localhost:9090/api/v1/comments/1
Content-Type: application/json
```

##### Response fields

| Path1    | Path2           | Type     | Description |
|----------|-----------------|----------|-------------|
| `status` |                 | `int`    | HttpStatus  |
| `body`   |                 | `String` |             |
| `msg`    |                 | `String` | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": "",
    "msg": "성공적으로 삭제완료했습니다."
}
```

### 유저 등록

유저를 등록합니다.

##### Request method

`POST` 요청

##### Request fields

| Path         | Type      | Description | Required |
|--------------|-----------|-------------|----------|
| `name`       | `String`  | 유저명         | 필수     |
| `pw`         | `String`  | 비밀번호        | 필수     |
| `email`      | `String`  | 이메일         | 필수     |
| `admin`      | `boolean` | 관리자여부       | 필수     |
| `adminToken` | `String`  | 관리자토큰       | 필수     |

##### Example request

``` http request
POST http://localhost:9090/api/v1/users/signup
Content-Type: application/json

{
    "name" : "유저12",
    "pw"   : "1234",
    "email": "user12@test.com",
    "admin": true,
    "adminToken" : "관리자 토큰"
}
```

##### Response fields

| Path1    | Path2        | Type      | Description |
|----------|--------------|-----------|-------------|
| `status` |              | `int`     | HttpStatus  |
| `body`   |              | `Object`  |             |
|          | `id`         | `Long`    | 유저고유식별키   |
|          | `name`       | `String`  | 유저명         |
|          | `email`      | `String`  | 유저이메일      |
|          | `createdAt`  | `String`  | 등록일         |
|          | `modifiedAt` | `String`  | 수정일         |
|          | `token`      | `String`  | 토큰          |
| `msg`    |              | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 3,
        "name": "유저12",
        "email": "user12@test.com",
        "createdAt": "2024년 08월 23일 16시 57분",
        "modifiedAt": "2024년 08월 23일 16시 57분",
        "token": "토큰값!"
    },
    "msg": "성공적으로 등록완료했습니다."
}
```

### 유저 단건조회

유저를 단건조회합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`GET` 요청

##### Request fields

| Path         | Type      | Description | Required |
|--------------|-----------|-------------|----------|
| `id`         | `Long`    | 유저고유식별키| 필수     |

##### Example request

``` http request
GET http://localhost:9090/api/v1/users/1
Content-Type: application/json
```

##### Response fields

| Path1    | Path2        | Type      | Description |
|----------|--------------|-----------|-------------|
| `status` |              | `int`     | HttpStatus  |
| `body`   |              | `Object`  |             |
|          | `id`         | `Long`    | 유저고유식별키   |
|          | `name`       | `String`  | 유저명         |
|          | `email`      | `String`  | 유저이메일      |
|          | `createdAt`  | `String`  | 등록일         |
|          | `modifiedAt` | `String`  | 수정일         |
| `msg`    |              | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 3,
        "name": "유저12",
        "email": "user12@test.com",
        "createdAt": "2024년 08월 23일 16시 57분",
        "modifiedAt": "2024년 08월 23일 16시 57분"
    },
    "msg": "성공적으로 등록완료했습니다."
}
```

### 유저 전체조회

유저를 전체조회합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`GET` 요청

##### Request fields

| Path         | Type      | Description | Required |
|--------------|-----------|-------------|----------|
| `id`         | `Long`    | 유저고유식별키| 필수     |

##### Example request

``` http request
GET http://localhost:9090/api/v1/users
Content-Type: application/json
```

##### Response fields

| Path1    | Path2        | Type     | Description |
|----------|--------------|----------|-------------|
| `status` |              | `int`    | HttpStatus  |
| `body`   |              | `List`   |             |
|          | `id`         | `Long`   | 유저고유식별키   |
|          | `name`       | `String` | 유저명         |
|          | `email`      | `String` | 유저이메일      |
|          | `createdAt`  | `String` | 등록일         |
|          | `modifiedAt` | `String` | 수정일         |
| `msg`    |              | `String` | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": [
        {
            "id": 1,
            "name": "유저",
            "email": "user@test.com",
            "createdAt": "2024년 08월 23일 16시 23분",
            "modifiedAt": "2024년 08월 23일 16시 23분"
        },
        {
            "id": 2,
            "name": "유저1",
            "email": "user1@test.com",
            "createdAt": "2024년 08월 23일 16시 57분",
            "modifiedAt": "2024년 08월 23일 16시 57분"
        },
        {
            "id": 3,
            "name": "유저12",
            "email": "user12@test.com",
            "createdAt": "2024년 08월 23일 16시 57분",
            "modifiedAt": "2024년 08월 23일 16시 57분"
        }
    ],
    "msg": "성공적으로 조회완료했습니다."
}
```

### 유저 수정

유저를 수정합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`PATCH` 요청

##### Request fields

| Path   | Type     | Description | Required |
|--------|----------|-------------|----------|
| `id`   | `Long`   | 유저고유식별키 | 필수     |
| `name` | `String` | 유저명        | 필수     |

##### Example request

``` http request
PATCH http://localhost:9090/api/v1/users/3
Content-Type: application/json
{
    "name" : "이름변경"
}
```

##### Response fields

| Path1    | Path2        | Type      | Description |
|----------|--------------|-----------|-------------|
| `status` |              | `int`     | HttpStatus  |
| `body`   |              | `Object`  |             |
|          | `id`         | `Long`    | 유저고유식별키   |
|          | `name`       | `String`  | 유저명         |
|          | `email`      | `String`  | 유저이메일      |
|          | `createdAt`  | `String`  | 등록일         |
|          | `modifiedAt` | `String`  | 수정일         |
| `msg`    |              | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "id": 3,
        "name": "이름변경",
        "email": "user12@test.com",
        "createdAt": "2024년 08월 23일 16시 57분",
        "modifiedAt": "2024년 08월 23일 16시 57분"
    },
    "msg": "성공적으로 수정완료했습니다."
}
```

### 유저 삭제

유저를 삭제합니다.

##### Request Header

| Path            | Description | Required |
|-----------------|------------|----------|
| `Authorization` | 토큰         | 필수     |

##### Request method

`DELETE` 요청

##### Request fields

| Path   | Type     | Description | Required |
|--------|----------|-------------|----------|
| `id`   | `Long`   | 유저고유식별키 | 필수     |

##### Example request

``` http request
DELETE http://localhost:9090/api/v1/users/3
Content-Type: application/json
```

##### Response fields

| Path1    | Path2        | Type     | Description |
|----------|--------------|----------|-------------|
| `status` |              | `int`    | HttpStatus  |
| `body`   |              | `String` |             |
| `msg`    |              | `String` | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": "",
    "msg": "성공적으로 삭제완료했습니다."
}
```

### 로그인

주어진 ID와 PW를 검증하여 로그인 합니다.

##### Request method

`POST` 요청

##### Request fields

| Path         | Type      | Description | Required |
|--------------|-----------|-------------|----------|
| `email`      | `String`  | 이메일         | 필수     |
| `pw`         | `String`  | 비밀번호        | 필수     |

##### Example request

``` http request
POST http://localhost:9090/api/v1/users/login
Content-Type: application/json

{
    "email" : "user@test.com",
    "pw"    : "1234"
}
```

##### Response fields

| Path1    | Path2        | Type      | Description |
|----------|--------------|-----------|-------------|
| `status` |              | `int`     | HttpStatus  |
| `body`   |              | `Object`  |             |
|          | `token`      | `String`  | 토큰          |
| `msg`    |              | `String`  | 응답 메세지      |


##### Example response

``` http response
HTTP/1.1 200 
Content-Type: application/json
Transfer-Encoding: chunked
Keep-Alive: timeout=60
Connection: keep-alive
{
    "status": 200,
    "body": {
        "token": "토큰값!"
    },
    "msg": "성공적으로 로그인했습니다."
}
```

---

## ⚙ ERD

![image](https://github.com/user-attachments/assets/e2e68d67-6b44-4c7d-b71c-61ecd7990cb4)

---

## ⚙ SQL

```SQL
CREATE DATABASE IF NOT EXISTS `nbc_spring_jpa_task`
USE `nbc_spring_jpa_task`;


CREATE TABLE IF NOT EXISTS `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) NOT NULL,
  `schedule_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsy51iks4dgapu66gfj3mnykch` (`schedule_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKsy51iks4dgapu66gfj3mnykch` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE IF NOT EXISTS `schedule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `content` varchar(500) NOT NULL,
  `weather` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa50n59y1j4a6qwa42p8jiguds` (`user_id`),
  CONSTRAINT `FKa50n59y1j4a6qwa42p8jiguds` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;

CREATE TABLE IF NOT EXISTS `schedule_mapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `schedule_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqkb0l5xlk5icoexmwyncuf1py` (`schedule_id`),
  KEY `FKqukv88s2bvxs8mvme397usi2m` (`user_id`),
  CONSTRAINT `FKqkb0l5xlk5icoexmwyncuf1py` FOREIGN KEY (`schedule_id`) REFERENCES `schedule` (`id`),
  CONSTRAINT `FKqukv88s2bvxs8mvme397usi2m` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;


CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `pw` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `role` enum('ADMIN','USER') NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `modified_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_general_ci;
```