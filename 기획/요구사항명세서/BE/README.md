# BE 요구사항 명세서

# 1.DB

## 테이블

### user

- 칼럼
    - id : Integer(Pk)
    - department : String
    - name : String
    - user_id : String
    - password : String

### user_conference

- 칼럼
    - id : Integer(Pk)
    - conference_id : Integer
    - user_id : Integer

### conference

- 칼럼
    - id : Integer(PK)
    - owner_id : Integer
    - conference_category : Integer
    - call_start_tile : DateTime
    - thumbnail_url : String
    - title : String
    - description : String
    - is_active : Bollean

### conference_category

- 칼럼
    - id : Integer(PK)
    - name : String

### conference_history

- 칼럼
    - id : Integer(PK)
    - conference_id : Integer
    - user_id : Integer
    - action : SmallInteger(CREATE,JOIN,EXIT)
    - inserted_time : DateTime

# 2.ERD

![Untitled](BE%20%E1%84%8B%E1%85%AD%E1%84%80%E1%85%AE%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%20%E1%84%86%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A6%E1%84%89%E1%85%A5%201660eb027c284fc7bb236e95e52715d7/Untitled.png)

![Untitled](BE%20%E1%84%8B%E1%85%AD%E1%84%80%E1%85%AE%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%20%E1%84%86%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A6%E1%84%89%E1%85%A5%201660eb027c284fc7bb236e95e52715d7/Untitled%201.png)

# 3.JPA

- Entity
    - 각 테이블의 Entity를 구현한다.
- Repository
    - 각 테이블 별 Repository와 RepositorySupport를 구현한다.

# 4.API

![Untitled](BE%20%E1%84%8B%E1%85%AD%E1%84%80%E1%85%AE%E1%84%89%E1%85%A1%E1%84%92%E1%85%A1%E1%86%BC%20%E1%84%86%E1%85%A7%E1%86%BC%E1%84%89%E1%85%A6%E1%84%89%E1%85%A5%201660eb027c284fc7bb236e95e52715d7/Untitled%202.png)

## 인증

### 로그인

- [POST] /auth/login
    - ID와 Password를 요청 받아 인증 및 JWT 토큰을 발급하는 api 제공
    - 에러처리
        - 로그인 시도하려는 회원이 존재하지 않음.
            - 응답 코드 : 404
            - 응답 메시지 : 존재하지 않는 계정입니다.
        - Password가 맞지 않음.
            - 응답 코드 : 401
            - 응답 메시지 : 잘못된 비밀번호입니다.

## 유저

### 회원가입

- [POST] /users
- Request
    - Body
    
    ```json
    {
    	"department" : "String",
    	"position" : "String",
    	"name" : "String",
    	"user_id" : "String",
    	"password" : "Stirng"
    }
    ```
    
- Response
    - 응답 코드 : 201
    
    ```json
    {
    	"message" : "Success",
    }
    ```
    
- 권한
    - 모두 사용 가능

### 내 프로필

- [GET] /users/me
- Request
    - 없음
- Response
    - Body
        - 200

```json
{
	"department" : "String",
	"position" : "String",
	"name" : "String",
	"user_id" : "String"
}
```

- 권한
    - 로그인 한 사용자
    

### 유저 정보(존재하는 회원 확인용)

- [GET] /users/<string:userId>
- Request
    - URL
- Response
    - Body
        - 로그인 한 사용자가 아닌 경우에만 응답
        - 409: 이미 존재하는 유저

```json
{
"message" : "이미 존재하는 사용자 ID 입니다."
}
```

- 권한
    - 모두 사용 가능

### 유저 정보 수정

- [PATCH] /users/<string:userId>
- Request
    - URL
    - Body

```json
{
	"department" : "String",
	"position" : "String",
	"name" : "String"
}
```

- Response
    - 200 : 성공
- 권한
    - 로그인 한 사용자
    

### 유저 정보 삭제(탈퇴)

- [DELETE] /users/<string:userId>
- Request
    - URL
- Response
    - 204 : 성공

```json
{
	"message" : "Success"
}
```

- 기능
    - 해당 유저가 생성한 방을 모두 삭제한다.
    - 해당 유저의 지난 회의 이력을 모두 삭제한다.
    - 해당 유저 정보를 삭제한다.
- 권한
    - 로그인 한 사용자

## 회의실(방)

### 방 카테고리 조회

- [GET] /conference-categories/
- Response
    - 200 : 성공

```json
{
	"id" : 1,
	"name" : "업무",
},
{
	"id" : 2,
	"name" : "교육",
},
{
	"id" : 3,
	"name" : "기타",
}
```

- 권한
    - 로그인 한 사용자
    

### 방 생성

- [POST] /conferences
- Request
    - FormData
        - file : “png, jpg 등의 이미지 파일”

```json
{
	"conferenceCategoryId" : 1,
	"title" : "방 제목",
	"descriptrion" : "방 설명",
}
```

- Response
    - 201 : 성공

```json
{
	"status" : 201,
	"message" : "Success",
	"conferenceId" : 1
}
```

- 기능
    - call_start_time은 방 생성 시,  insert 한 시점을 저장한다.
    - call_end_time은 방장의 방 종료 시점을 저장한다.
    - 이미지 파일은 임의의 디렉토리를 생성하여 방 ID/이미지 파일명으로 저장한다.
- 권한
    - 로그인 한 사용자
    
- Response
    - 200 Body

```json
{
	"content": [
		{
			"id" : 1,
			"conference_category" : "교육",
			"JoinUserNum" : 5,
			"owner" : "User-1",
			"callStartTime" : DateTime,
			"thumbnail_url" : "/1/image.png",
			"title" : "방 제목",
			"description" : "방 설명",
			"is_active" : true
		}	
	],
	"empty" : true,
	"first" : true,
	"last" : true,
	"number" : 0,
	"numberOfElements" : 0,
	"pageable" : {
		"offset" : 0,
		"pageNumber" : 0,
		"pageSize" : 0,
		"paged" : true,
		"sort" : {
			"empty" : true,
			"sorted" : true,
			"unsorted" : true
		},
		"unpaged" : true 
	},
	"size" : 0,
	"sort" : {
		"empty" : true,
		"sorted" : true,
		"unsorted" : true
	},
	"totalElements" : 0,
	"totalPages" : 0
}
```

### 방 목록

- [GET] /conferences
- Request
    - Query Parameter
        - title
            - title로 검색하는 기능
        - sort
            - asc, desc로 call_start_time 오름차순/내림차순 정렬 기능
            - asc, desc로 title 오름차순/내림차순 정렬 기능
            - API 호출 예 : sort=call_start_time,asc&sort=title,desc
        - page
            - 조회할 페이지
        - size
            - 조회할 페이지의 아이템 개수
        - conference_category
            - 방 목록 카테고리로 필터하기 위한 쿼리 파라미터
            - ex : /conferences?conference_category=1 → 업무 카테고리로 목록을 필터링
- Response
    - 응답코드 200
    - Body
    
    ```json
    {
    	"content" : [
    		{
    			"id": 1,
    			"conference_category" : "교육",
    			"joinUsersNum": 5,
    			"owner": "User-1",
    			"callStartTime": DateTime,
    			"thumbnail_url": "/1/image.png",
    			"title": "방 제목",
    			"description": "방 설명",
    			"is_active": true
    		}
    	],
    	"empty": true,
    	"first": true,
    	"last": true,
    	"number": 0,
    	"numberOfElements": 0,
    	"pageable": {
    		"offset": 0,
    		"pageNumber": 0,
    		"pageSize": 0,
    		"paged": true,
    		"sort": {
    			"empty": true,
    			"sorted": true,
    			"unsorted": true
    		},
    		"unpaged": true
    	},
    	"size": 0,
    	"sort": {
    		"empty": true,
    		"sorted": true,
    		"unsorted": true
    	},
    	"totalElemets": 0,
    	"totalPages": 0
    }
    ```
    
- 기능
    - Pagenation을 지원한다.
        - Spring Data Core의 Page, Pageable을 활용한 Pagenation 응답을 지원한다.
        - 참고 자료 : [https://www.baeldung.com/spring-data-jpa-pagination-sorting](https://www.baeldung.com/spring-data-jpa-pagination-sorting)
    - title로 검색하는 기능을 지원한다.
    - title, call_start_time으로 정렬하는 기능을 지원한다.
- 권한
    - 모든 사용자

### 방 상세 정보 조회

- [GET] /conferences/<int:conference_id>
- Request
    - Body
        - URL
- Response
    - 응답코드 200
    - Body
    
    ```json
    {
    	"id" : 1,
    	"conference_category" : "교육",
    	"owner" : "User-1",
    	"callStartTime" : DateTime,
    	"thumbnail_url" : "/1/image.png",
    	"title" : "방 제목",
    	"description" : "방 설명",
    	"is_active" : true,
    	"users" : [
    		{
    			"user_id" : "test-1",
    			"name" : "참여자 1"
    		},
    		{
    			"user_id" : "test-2",
    			"name" : "참여자 2"
    		}
    	]
    }
    ```
    
- 권한
    - 로그인 한 사용자

### 방 정보 수정

- [PATCH] /conferences/<int:conference_id>
- Request
    - Body
    
    ```json
    {
    	"conference_category" : 1,
    	"title" : "방 제목",
    	"description" : "방 설명"
    }
    ```
    
- Response
    - 응답코드 200
    - Body
    
    ```json
    {
    	"statusCode" : 200,
    	"message" : "Success"
    }
    ```
    
    - FormData
        - file : “png, jpg 등의 이미지 파일”
- 기능
    - 이미지 파일은 임의의 디렉토리를 생성하여 방 ID/이미지 파일명으로 저장한다.
- 권한
    - 로그인 한 사용자
    - 해당 방을 생성한 방장

# 5.Websocket

## 웹소켓 채팅 서버

- 채팅방 기능 구현을 위한 웹 소켓 기능을 구현한다.
- SockJS와 Stomp를 사용해 SimpleBroker를 생성하여 구현한다.
- 설계가 완료되면 Front-end 담당자에게 해당 동작에 대한 메시지 흐름과 규격에 대해 가이드하고 공유하는 문서를 업데이트한다.
- 참고 문서
    - [https://supawer0728.github.id/2018/03/30/spring-websocket/](https://supawer0728.github.io/2018/03/30/spring-websocket/)
    - [https://hongjuzzang.github.io/springboot/springboot_stomp2/](https://hongjuzzang.github.io/springboot/springboot_stomp2/)
    - [http://jmesnil.net/stomp-websocket/doc/](http://jmesnil.net/stomp-websocket/doc/)
    - [https://velog.io/@tlatldms/Spring-Boot-STOMP-JWT-Socket-%EC%9D%B8%EC%A6%9D%ED%95%98%EA%B8%B0](https://velog.io/@tlatldms/Spring-Boot-STOMP-JWT-Socket-%EC%9D%B8%EC%A6%9D%ED%95%98%EA%B8%B0)

# 6.WebRTC

## Kurento

- 설치
    - Docker를 통해 Kurento를 설치하고 실행한다.
    - STUN/TURN 서버를 설치하고 Kurento를 설정한다.
    - 참고자료
        - [https://doc-kurento.readthedocs.io/en/latest/user/installation.html](https://doc-kurento.readthedocs.io/en/latest/user/installation.html)
        - Java 예제 - [https://github.com/Kurento/kurento-tutorial-java](https://github.com/Kurento/kurento-tutorial-java)
        - Javascript 예제 - [https://github.com/Kurento/kurento-tutorial-js](https://github.com/Kurento/kurento-tutorial-js)
- GroupCall 구현 및 통합
    - 참고 자료(Github) 내 Front-end 코드를 분석한다.
    - 참고 자료(Tutorial)를 참고한다.
    - 참고 자료를 통해 완료 프로젝트의 그룹 화상 회의를 위한 WebRTC 기능을 구현한다.
    - 참고자료
        - [https://doc-kurento.readthedocs.io/en/stable/user/tutorials.html#webrtc-many-to-many-video-call-group-call](https://doc-kurento.readthedocs.io/en/stable/user/tutorials.html#webrtc-many-to-many-video-call-group-call)
        - [https://github.com/Kurento/kurento-tutorial-java/tree/master/kurento-group-call](https://github.com/Kurento/kurento-tutorial-java/tree/master/kurento-group-call)
- 심화 기능
    - 참고 자료(Tutorial)를 통해 심화 기능들을 구현한다.
    - 스티커 기능 구현하기
        - 방 상세 보기에서 참여자가 카메라를 통해 송출하는 화상 화면에 자신의 얼굴에 스티커 이미지를 덮어 씌워 화상을 송출하는 기능을 Kurento의 FaceOverlayFilter를 활용하여 구현한다.
        - WebRTC Magic Mirror Back-end,Front-end 참고자료
            - [https://doc-kurento.readthedocs.io/en/stable/tutorials/java/tutorial-magicmirror.html](https://doc-kurento.readthedocs.io/en/stable/tutorials/java/tutorial-magicmirror.html)
            - [https://github.com/Kurento/kurento-tutorial-java/tree/master/kurento-magic-mirror](https://github.com/Kurento/kurento-tutorial-java/tree/master/kurento-magic-mirror)
    - 녹화 기능 구현하기
        - 방 상세 보기에서 참여자가 특정 참여자의 영상을 녹화하는 기능을 Kurento 를 활용하여 구현한다.
        - WebRTC Recording Back-end 참고자료
            - [https://doc-kurento.readthedocs.io/en/stable/tutorials/java/tutorial-recorder.html](https://doc-kurento.readthedocs.io/en/stable/tutorials/java/tutorial-recorder.html)
            - [https://github.com/Kurento/kurento-tutorial-java/tree/master/kurento-hello-world-recording](https://github.com/Kurento/kurento-tutorial-java/tree/master/kurento-hello-world-recording)
        - WebRTC Recording Front-end 참고자료
            - [https://doc-kurento.readthedocs.io/en/stable/tutorials/js/tutorial-recorder.html](https://doc-kurento.readthedocs.io/en/stable/tutorials/js/tutorial-recorder.html)
            - [https://github.com/Kurento/kurento-tutorial-js/tree/master/kurento-recorder](https://github.com/Kurento/kurento-tutorial-js/tree/master/kurento-recorder)