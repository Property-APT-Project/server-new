# REST API 명세서

<br>

## member-controller
회원 컨트롤러

[1] 회원가입
- URI: /members/join
- HTTP Method: POST
- Response: 응답코드,  `String`

    ```string
        성공적으로 회원가입되었습니다.
    ```

<br>

[2] 회원정보수정
- URI: /members/profile/{memberId}
- HTTP Method: PUT
- Response: 응답코드,  `String`

    ```string
        성공적으로 회원정보수정되었습니다.
    ```

<br>

[3] 회원삭제
- URI: /members/leave/{memberId}
- HTTP Method: DELETE
- Response: 응답코드,  `String`

    ```string
        성공적으로 회원삭제되었습니다.
    ```

<br>

[4] 로그인
- URI: /members/login
- HTTP Method: POST
- Response: 응답코드,  `token`

    ```string
        {
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ1c2VyMTIzIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        }
    ```

<br>

[5] 로그아웃
- URI: /members/logout
- HTTP Method: GET
- Response: 응답코드,  `String`

    ```string
        성공적으로 로그아웃되었습니다.
    ```

<br>


## dong-code-rest-controller
동 코드 조회 컨트롤러

[1] 동 코드 조회 (전체 시도)
- URI: /api/v1/dong-code/dong-code/sido
- HTTP Method: GET
- Response: 응답코드,  `List<DongCodeDto>`

    ```json
    [
        {
            "dongCode": "11",
            "sidoName": "서울특별시",
            "gugunName": null,
            "dongName": null
        }, ...

    ]
    ```

<br>

[2] 동 코드 조회 (시도에 해당하는 구군)
- URI: /api/v1/dong-code/dong-code/gugun/{sido}
- HTTP Method: GET
- Response: 응답코드,  `List<DongCodeDto>`

    ```json
    [
        {
            "dongCode": "11110",
            "sidoName": null,
            "gugunName": "종로구",
            "dongName": null
        }, ...
    ]
    ```

<br>

[3] 동동 코드 조회 (구군에 해당하는 동)
- URI: /api/v1/dong-code/dong-code/dong/{gugun}
- HTTP Method: GET
- Response: 응답코드,  `List<DongCodeDto>`

    ```json
    [
        {
            "dongCode": "11110",
            "sidoName": null,
            "gugunName": "종로구",
            "dongName": null
        }, ...
    ]
    ```

<br>


## house-info-rest-controller
아파트 정보 조회 컨트롤러

[1] 아파트 정보 목록(키워드 해당)
- URI: /api/v1/house-info/keyword/{name}
- HTTP Method: GET
- Response: 응답코드,  `List<HouseInfoDto>`

    ```json
    [
        {
            "buildYear": 2000,
            "roadName": "전농로10길",
            "dong": "답십리동",
            "dongCode": "1123010500",
            "aptName": "답십리청솔우성",
            "jibun": "80",
            "lng": "127.060129124176",
            "lat": "37.5746094744702"
        }, ...

    ]
    ```

<br>

[2] 아파트 정보 목록(동 코드 해당)
- URI: /api/v1/house-info/dong-code/{code}
- HTTP Method: GET
- Response: 응답코드,  `List<HouseInfoDto>`

    ```json
    [
        {
            "buildYear": 2000,
            "roadName": "전농로10길",
            "dong": "답십리동",
            "dongCode": "1123010500",
            "aptName": "답십리청솔우성",
            "jibun": "80",
            "lng": "127.060129124176",
            "lat": "37.5746094744702"
        }, ...

    ]
    ```

<br>


## house-deal-rest-controller
아파트 거래 내역 목록 조회 컨트롤러

[1] 아파트 거래 목록 조회(아파트 이름)
- URI: /api/v1/house-deal/{aptName}
- HTTP Method: GET
- Response: 응답코드,  `List<HouseDealDto>`

    ```json
    [
        {
            "dealAmount": "6,200",
            "dealYear": 2022,
            "dealMonth": 4,
            "area": "49.65",
            "floor": "3",
            "apartmentName": "청솔"
        }, ...
    ]
    ```

<br>


[2] 아파트 거래 목록 조회(아파트 이름/ 년도)
- URI: /api/v1/house-deal/{aptName}/{year}
- HTTP Method: GET
- Response: 응답코드,  `List<HouseDealDto>`

    ```json
    [
        {
            "dealAmount": "6,200",
            "dealYear": 2022,
            "dealMonth": 4,
            "area": "49.65",
            "floor": "3",
            "apartmentName": "청솔"
        }, ...
    ]
    ```

<br>


[3] 아파트 거래 목록 조회(아파트 이름/ 년도 / 월)
- URI: /api/v1/house-deal/{aptName}/{year}/{month}
- HTTP Method: GET
- Response: 응답코드,  `List<HouseDealDto>`

    ```json
    [
        {
            "dealAmount": "6,200",
            "dealYear": 2022,
            "dealMonth": 4,
            "area": "49.65",
            "floor": "3",
            "apartmentName": "청솔"
        }, ...
    ]
    ```

<br>



## search-rest-controller
검색 키워드 컨트롤러

[1] 검색 키워드 검색 횟수 누적
- URI: /api/v1/search/{keyword}
- HTTP Method: POST
- Response: 응답코드

<br>


[2] 검색 횟수 Top 1 ~ rank까지 키워드 조회
- URI: /api/v1/search/rank/{rank}
- HTTP Method: GET
- Response: 응답코드,  `List<SearchDto>`

    ```json
    [
        {
            "keyword": "abc",
            "count": 9
        }, ...
    ]
    ```



## announcement-controller
공지사항 컨트롤러

[1] 공지사항 등록
- URI: /announcements/new-announcement
- HTTP Method: POST
- Response: 응답코드,  `String`

    ```string
        성공적으로 등록되었습니다.
    ```

<br>

[2] 공지사항 목록
- URI: /announcements
- HTTP Method: GET
- Response: 응답코드,  `List<Announcement>`

    ```json
    [
        {
            "articleNo": 0,
            "userId": "admin", 
            "subject": "hello",
            "content": "hello world",
            "hit": 5,
            "registerTime": "2024-04-28 20:03:20"
        }, ...
    ]
    ```

<br>

[3] 공지사항 수정
- URI: /announcements/{announcementId}
- HTTP Method: PUT
- Response: 응답코드,  `String`

    ```string
        성공적으로 수정되었습니다.
    ```

<br>

[4] 공지사항 삭제
- URI: /announcements/{announcementId}
- HTTP Method: DELETE
- Response: 응답코드,  `String`

    ```string
        성공적으로 삭제되었습니다.
    ```

<br>

[5] 공지사항 검색
- URI: /announcements/search
- HTTP Method: GET
- Response: 응답코드,  `List<Announcement>`

    ```json
    [
        {
            "articleNo": 0,
            "userId": "admin", 
            "subject": "hello",
            "content": "hello world",
            "hit": 5,
            "registerTime": "2024-04-28 20:03:20"
        }, ...
    ]
    ```

<br>
