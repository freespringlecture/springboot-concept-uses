# 스프링 웹 MVC 6부: index 페이지와 파비콘
## 웰컴 페이지
> Application `/` 요청했을 때 호출되는 페이지  
> 지정이 안되어있으면 SpringBoot의 기본적인 ErrorHandler가 보여주는 Error 페이지가 나타남  
> 정적페이지(index.html)를 보여주는 방법과 동적페이지(템플릿 엔진 사용)를 보여주는 방법 두가지가 있음  
- 기본 리소스 위치에 index.html 찾아 보고 있으면 제공
- index.템플릿 찾아 보고 있으면 제공
- 둘 다 없으면 에러 페이지

## favicon
1. 파비콘 만들기 ​https://favicon.io/
2. favicon.ico 파일을 static 폴더에 복사 후 Build Project
3. 파비콘이 안바뀔 때 - Build - Rebuild Project  
  https://stackoverflow.com/questions/2208933/how-do-i-force-a-favicon-refresh