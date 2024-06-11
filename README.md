# Newsfeed Project
1. Spring Boot의 Spring security를 사용하여 Jwt토큰 인증, 인가를 기반으로 회원가입, 로그인, 로그아웃, 회원탈퇴 로직
2. 게시글 작성, 조회, 수정, 삭제 CRUD 기능
3. 댓글 작성 기능
4. 기능을 가진 서버는 IntelliJ 프로그램과 Java언어로 사용하여 개발하였습니다.
5. 백엔드 데이버 베이스는 MySQL을 사용합니다.

# 기능 구현
 -김형석 팀장 : 스프링 시큐리티, 토큰 발행(access), 로그인 및 로그아웃 기능
 
 -장경진 팀원 : 게시물 CRUD기능, 게시물 생성, 조회, 삭제
 
 -윤일영 팀원 : 게시물 CRUD 기능, 전체 게시물 내림차순 조회
 
 -허건  팀원 : 탈퇴 기능, 프로필 수정 조회 기능(CRUD)
   

-User

 -회원가입, 회원탈퇴, 프로필조회, 프로필수정 API 구현

-NewsFeed

 -작성, 조회, 수정, 삭제 구현

-Comment
 
 -작성 구현
## 1. User 기능
-회원가입
    -username, password, name, email, introduce 를 입력하여 회원 가입
 
-로그인

    -username, password 를 입력하여 로그인
    
    -jwt access 토큰 발행
    
    -로그아웃

 -username, password 를 입력하여 로그인 <- 수정 예정
 
 -jwt access 토큰 필요
 
-회원탈퇴

 -username, password 를 입력하여 회원탈퇴 및 재가입 불가
 
 -jwt access 토큰 필요
## 2. NewsFeed 기능
-작성

  -로그인한 사용자가 작성이 가능하며 Access 토큰 검증
 
-조회

  -로그인하지 않은 모든 사용자가 조회가능하다
 
  -선택조회도 가능하다. 
 
-수정

  -로그인한 사용자가 작성한 newsfeed만 수정이 가능하며 Access 토큰 검증
 
-삭제

  -로그인한 사용자가 작성한 newsfeed만 수정이 가능하며 Access 토큰 검증
## 3. Comment 기능

-작성

 -로그인한 사용자가 작성되있는 newsfeed의 id를 request body 형식으로 작성하여 댓글 작성이 가능하다.

##  Wireframe
![와이어프레임](https://github.com/Hyungs0703/NewSfeed/assets/165638682/8c45c1f2-383c-4555-a976-75d2119385f2)

##  ERD
<img width="500" alt="ERD 다이어그램" src="https://github.com/Hyungs0703/NewSfeed/assets/165638682/3bb4cd90-b0fc-4c71-8cad-6d2842aa2d10">

##  API Document
<img width="698" alt="API 명세" src="https://github.com/Hyungs0703/NewSfeed/assets/165638682/f35d9ebb-87ec-4c56-841e-245169dc312d">
