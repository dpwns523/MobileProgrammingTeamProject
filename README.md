# MobileProgramming Team Project
---
## Activity 순서
1. MainActivity
2. LoginActivity -> FindActivity or SignUpActivity
3. HomeActivity\
-> (운동방법 선택했을경우) 4. main_exercise_way
---

# 커뮤니티 구성 
커뮤니티 자체에서 하나씩 아이콘을 누르게 되면, 해당 게시판의 메인으로 이동
그 이후 글 쓰기 or 리스트뷰를 이용한 리스트를 클릭하면 해당 화면으로 넘어감.

1. 자유게시판
    ->free_talking.xml : 자유게시판 아이콘
    ->free_talking_main.xml : 자유게시판 메인화면
    ->free_talking_read.xml : 자유게시판 내용 보고, 댓글 보기
    ->free_talking_writepage.xml : 자유게시판 글 작성

2. 비포에프터 변화게시판
    ->before_after.xml : 비포에프터 아이콘
    ->before_after_main.xml : 비포에프터 메인화면
    ->before_after_read.xml : 비포에프터 내용 보고, 댓글 보기
    ->before_after_write.xml : 비포에프터 글 작성

3. 자극사진, 동기부여 사진 
    ->stimulus_picture.xml : 자극사진, 동기부여사진 아이콘
    ->stimulus_picture_main.xml : 자극사진, 동기부여사진 메인화면
    ->stimulus_picture_read.xml : 자극사진, 동기부여사진 내용 보고, 댓글 보기
    ->stimulus_picture_write.xml : 자극사진, 동기부여사진 글 작성

4. 궁금한점, 질문
    ->questions.xml : 질문 아이콘
    ->questions_main.xml : 질문 메인화면
    ->questions_read.xml : 질문 내용 보고, 댓글 보기
    ->questions_write.xml : 질문 글 작성

5. 운동 팁
    ->tips.xml : 운동 팁 아이콘
    ->tips_main.xml : 운동 팁 메인화면
    ->tips_read.xml : 운동 팁 내용 보고, 댓글 보기
    ->tips_write.xml : 운동 팁 글 작성

6. 식단 공유
    ->food.xml : 식단공유 아이콘
    ->food_main.xml : 식단공유 메인화면
    ->food_read.xml : 식단공유 내용 보고, 댓글 보기
    ->food_write.xml : 식단공유 글 작성

7. 검색기능
8. 메인에서부터 있는 기능이지만 자신의 기록, 스펙 적는 란

# 타이머
1. INTERVAL 버튼 : 누르면 1분 30초 입력되야 함.
2. 30SEC : 이름 바꿔야될 거 같음. 30초 쉬는시간
3. 1MIN : 이름 바꿔야 될 거 같음. 1분 쉬는시간으로 사용 or 운동 기록시간
4. 재생버튼 : 타이머 시작