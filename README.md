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

#### DispatcherServlet

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
DispatcherServlet은 모든 요청을 받아들이는 Servlet
즉 앞단에서 모든 요청을 스프링이 받아들인 후에 URL에 대응되는 컨트롤러를 찾아가 특정 메서드를 실행
컨트롤러를 어떻게 찾아가는지, 어떤 메서드를 실행하는지는 어노테이션으로 명시
**DispatcherServlet이 모든 요청을 받아들여 적절한 메서드가 실행될 수 있도록 분기시킨다**

![img](./mdimg/img1.daumcdn.png)

#### Spring Servlet Configuration

> 스프링 컨테이너는 객체들을 관리하는 bean Factory

```xml
        <context:annotation-config />
        <context:component-scan base-package="com.victolee.springTest.controller"/>
```

1. `<context:annotation-config />`
어딘가( Application Context )에 이미 등록된 bean들의 어노테이션 활성화를 위해 사용
**즉 어딘가에서 미리 만들어 놓은 객체를 가져다 쓰기 위한 @Autowired 와 @Qualifier 같은 어노테이션을 해석할 것이라는 의미**

스프링 컨테이너는 객체들을 관리한다고 했었는데, 관리하는 방식은 어딘가에서 생성된 객체를 가져다 쓰는 방식입니다.
즉 미리 생성된 객체를 가져다 쓰기 위해서는 `@Autowired`라는 어노테이션을 사용하는데,
`<context:annotation-config />`는 이 어노테이션을 활성화 하겠다는 의미가 됩니다.

        DI는 이 과정을 통해 일어나는 것입니다.

2. `<context:component-scan>`
어노테이션을 활성화 하는 일을 수행
**`@Component`, @`Repository`, `@Service`, `@Controller`, `@RestController`, `@ControllerAdvice`, `@Configuration` 과 같은 어노테이션을 스캔 하겠다는 의미**
*추가적인 설정을 통해 사용자가 직접 작성한 어노테이션을 추가, 제거 할 수 있습니다.*

#### Hello World

spring-servlet.xml 파일에서 scan 범위( base-package ) 이름으로 패키지 생성
**어노테이션 스캔은 base-package 로 작성한 패키지 내에서만 이루어지기 때문에 패키지 이름이 다르면 스캔을 하지 않습니다.**

```java
package victoleespring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController {
	
	@RequestMapping
	public String hello() {
		return "/WEB-INF/views/hello.jsp";
	}
}
```
클래스 선언부 위에 `@Controller`를 작성하면
스프링은 이 클래스를 컨트롤러로 인식하여 다음의 과정을 수행합니다.

1) 모든 요청을 받아들이는 DispatcherServlet이 특정 요청을 처리할 수 있도록 `@Controller` 어노테이션이 작성된 클래스를 읽어들입니다.
2) 그 클래스의 많은 어노테이션 중 `@RequestMapping` 어노테이션을 읽어들입니다.
3) 어떤 URL이 왔을 때 어떤 메서드를 실행한 것인지를 **Mapping** 합니다.

**즉 위 예제의 내용은 /hello라는 요청이 오면 hello.jsp 페이지를 렌더링 하겠다는 의미입니다.**

        @Controller을 작성하면 스프링이 해당 클래스를 컨트롤러라고 인식을 하고,
        @RequestMapping을 작성하면 URL과 메서드가 매핑이 이루어진다고 이해하시면 됩니다.

#### Hello world View
```html
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
        <h1>Hello World</h1>
</body>
</html>
```
