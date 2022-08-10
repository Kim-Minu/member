1. spec
   1. spring boot 2.7.2
   2. spring data jpa
   3. spring security
   4. kotlin 1.7
   5. jdk 11
   6. database - mariaDB 10.6.8
   7. api 명세 - swagger ui

2. API 명세
    http://localhost:8080/swagger-ui/index.html

3. API 기능 구현
   1. 회원 가입
      1. 이메일, 전화번호를 식별 가능한 정보로 지정 (중복을 허용하지 않음)
   2. 로그인
      1. JWT 인증 구현
         1. jwt token - body { email, id(회원 번호) } 정보 저장
      2. 이메일 또는 전화번호와 비밀번호로 로그인이 가능토록 구현
      3. 로그인 방법은 로그인 response 의 토큰을 동봉 된 로그인 방법.png 이미지 파일을 참조 하시면 됩니다.
   3. 내 정보 보기
      1. 로그인 후 JWT 정보로 해당 회원의 정보를 조회
   4. 비밀번호 찾기 (재설정)
   
4. 기타
   1. ExceptionHandler 구현
   2. 

5. 실행 방법
    1. 인텔리제이로 해당 프로젝트를 open 합니다.
    2. run MemberApplication.kt 실행 시킵니다.
    3. http://localhost:8080/swagger-ui/index.html로 접속 하시면 api 명세를 참고 하실 수 있습니다.
    