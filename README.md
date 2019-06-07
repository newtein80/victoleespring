# victoleespring
victoleespringblog2018

# 20180324.01

메이븐 ( Maven )은 war 또는 jar 파일을 빌드( build ), 라이브러리 의존성( dependency ) 해결, 컴파일( compile ) , 배포 ( deploy ) 등을 해결해주는 도구

> Tip) IDE가 이클립스라면 복붙 하신 다음에 ctrl + shift + f 를 누르시면 자동 줄맞춤이 됩니다.

Maven이 라이브러리를 제대로 받아오지 못할 경우
maven은 중앙( central )에서 라이브러리를 받아오는데, 잘못 받아오는 경우가 있어서 제대로 동작하지 않을 때가 있습니다.
그럴 때는 아래의 경로에 가셔서 repository 폴더를 지우고 중앙으로부터 다시 받는 것이 좋습니다.

사용자경로\.m2\repository

그리고 이클립스에서 프로젝트를 우클릭
-> maven
-> Update project 를 클릭하여 라이브러리를 다시 가져올 수 있도록 refresh를 할 수 있습니다.

# 20180314.02

```xml
<!-- Dispatcher Servlet(Front controller) -->
<servlet>
        <servlet-name>spring</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
</servlet>
<servlet-mapping>
        <servlet-name>spring</servlet-name>
        <url-pattern>/</url-pattern>
</servlet-mapping>
```

![img](./mdimg/img1.daumcdn.png)