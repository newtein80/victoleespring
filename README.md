# victoleespring
victolee spring blog 2018

https://victorydntmd.tistory.com/

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

# 20180324.02

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

# 20180324.03

#### 톰캣 버튼을 실행했을 때 일어나는 동작

![img](./mdimg/02.png)
1. 톰캣을 실행하기 전에 개발자는 톰캣의 설정파일인 `web.xml` 파일에 `<context-param>`으로 전역 파라미터를 설정합니다. 파라미터의 이름은 **contextConfigLocation**이고 **어떤 객체들을 미리 만들어 놓을지가 작성된 설정파일의 경로**를 값으로 할당해 놓습니다.
```xml
<!-- Context parameter -->
<context-param>
        <param-name>contextConfiguration</param-name>
        <param-value>/WEB-INF/applicationContext.xml</param-value>
</context-param>
```
2. 이어서 톰캣이 실행되면 수행할 클래스( 리스너 )의 이름을 같은 파일인 web.xml에 작성합니다.
```xml
<!-- Context Loader Listener -->
<listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```
톰캣을 실행하면 `<listener>`가 등록되어 있는 **ContextLoaderListner** 객체를 호출하는데, 이 객체는 내부적으로 부모 객체를 실행합니다.
부모 객체는 **ContextLoader**이며 이 객체에서 Root Application Context를 생성하는데, 이 컨테이너에는 웹과 관련이 없는 객체들을 저장합니다.
예를들면 DAO 객체들은 웹과 직접적인 관련이 없는 객체입니다.(단지 DB에 접근하기 위한 객체들일 뿐이죠.)

3. Root Application Context 컨테이너에 객체들을 생성하기 위해서, 1단계에서 작성한 전역 파라미터 이름인 contextConfigLocation의 값으로 설정된 `/WEB-INF/applicationContext.xml`을 읽어 들입니다.
![img](./mdimg/03.jpg)

ContextLoader 클래스에는 `CONFIG_LOCATION_PARAM`라는 상수가 정의되어 있는데, 기본 값으로 문자열 contextConfigLocation이 할당되어 있습니다.

그래서 **1단계에서 전역 파라미터 이름을 contextConfigLocation으로 작성한 것**입니다.

그리고 **contextConfigLocation는 사실, 설정 파일 경로( WEB-INF/applicationContext.xml )를 의미**하구요.

`WEB-INF/applicationContext.xml`은 **어떤 객체들을 미리 생성해 놓을지를 정의한 설정 파일**이라고 했었습니다.

즉 `Context Loader Listner`를 통해 `applicationContext.xml`를 읽어들이고, `Root Application Context 컨테이너`에 **웹과 관련이 없는 객체들이 생성**됩니다.

4. applicationContext.xml은 3 - layer 구성된 component를 정의
>3 layer architecture는 Controller - Service - DAO로 정의된 구조

```xml
<context:annotation-config />

<context:component-scan back-package="com.victolee.guestbook.repository">
        <context:include-filter type="annotation"
                expression="org.springframework.stereotype.Repsoitory" />
        <context:include-filter type="annotation"
                expression="org.springframework.stereotype.Service" />
        <context:include-filter type="annotation"
                expression="org.springframework.stereotype.Component" />
</context:component-scan>
```
.xml 파일에 설정을 하는 대신에 어노테이션으로 설정을 하겠다는 의미이며, 어노테이션을 스캔 할 범위인 base-package를 정의
**즉 base-package 범위 내에서 @Repository , @Service , @Component 어노테이션을 스캔하겠다는 의미**

예를들어 GuestbookDAO를 컨테이너에 추가하고 싶다면 base-package안에 GuestbookDAO 클래스를 생성한 후 3 - layer 계층에 맞는 어노테이션을 추가하면 됩니다.
```java
import org.springframework.stereotype.Repository;

@Repository
public class GuestbookDAO {
        // TO-DO
}
```
**이렇게 @Repository를 등록하면 Root Application Context, 즉 bean Factory에 해당 객체가 싱글톤 형태로 저장이 됩니다.**

        정리하면 ApplicationContext.xml 파일에 의존성 주입을 위한 객체들을 미리 정의합니다.
        여기서는 그 방식을 어노테이션으로 해결 하겠다고 했으며,
        스프링이 어노테이션을 스캔할 수 있도록 범위를 지정해줬습니다.
        그러면 톰캣이 실행되었을 때 ContextLoaderListner에 의해 ContextLoader가 실행되어
        컨테이너에 의존성 해결을 위한 객체들을 생성하게 됩니다.


#### 요청이 왔을 때 실행 동작
![img](./mdimg/04.png)

1. `web.xml` 파일에 작성했던 코드
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
" 모든 요청이 왔을 때 DispactherServlet의 init() 메서드가 실행되도록 해라 "는 의미가 담겨있는 코드입니다.
물론 한 번 요청이 왔었다면 init() 메서드는 Servlet의 라이프 사이클에 따라 생략이 되겠죠.

Servlet 매핑을 할 때 작성한 `<servlet-name>`이 `spring`이므로 `DispatcherServlet`의 `init()` 메서드는 `spring-servlet.xml` 파일을 읽어들입니다.

spring-servlet.xml 파일의 이름은 **'servlet-name의 값' + '-servlet.xml'** 이 되어 생성된 이름입니다.

2. spring-servlet.xml 파일의 코드
```xml
<context:annotation-config />
<context:component-scan
        base-package="com.victoleespring.controller" />
```
즉 **어떤 요청이 오면 `DispatcherServlet`은 `spring-servlet.xml` 파일을 읽어서
"`base-package 범위 내`에 있는 어노테이션을 스캐닝 한다"는 의미**가 담겨있는 코드

3.  base-package에 속한 클래스의 선언부에 `@Controller` 어노테이션을 추가하면 스캐닝이 이루어지고, **Web Application Context 컨테이너에 해당 객체를 저장**

```java
import org.springframework.stereotype.Controller

@Controller
public calss GuestbookController {

}
```
4. 톰캣을 실행 했을 때 생성했던 컨테이너(Root Application Content)에 있는 DAO 객체를 불러올 수 있습니다.
**`@Autowired` 어노테이션을 통해 미리 생성된 객체인 DAO를 사용 할 수 있으며, 여기서 의존성 주입( DI )이 일어납니다.**
```java
@Controller
public class GuestbookController {
        @Autowired
        private GuestbookDAO guestbookdao;
}
```

5. 또한 스캐닝 작업을 계속하여 `@RequestMapping` 어노테이션에 대하여 **Handler Mapping을 수행**
즉 **어떤 URL이 올 때 어떤 메서드를 실행하겠다는 매핑 테이블을 만드는 것**입니다.
개발자가 해야 할 것은 `@RequestMapping` 어노테이션으로 URL을 작성하고, 메서드에 수행할 로직을 컨트롤러답게( 데이터를 받아와서 뷰 페이지에 전달 ) 작성하면 되는 것입니다.
```java
@Controller
public class GuestbookController {
        @Autowired
        private GuestbookDAO guestbookdao;

        @RequestMapping("/main")
        public String main(Model model) {
                List<GuestbookVO> list = guestbookdao.getList();
                model.addAttribute("list", list);

                return "/WEB-INF/views/index.jsp";
        }
}
```
URL에 /main 으로 요청이 오면 DAO 객체에서 데이터를 가져오고, 이 데이터를 JSP에 전달하는 컨트롤러 역할을 하는 메서드
DispatcherServlet의 init() 메서드는 이와 같이 어떤 URL이 왔을 때 어떤 메서드를 실행할 것인지에 대한 Mapping을 만들어 놓습니다.

6. 스캐닝 과정을 거쳐 Mapping table이 등록되면 이후의 요청은 `DispatherServlet`의 `init()`메서드가 실행되지 않을 것입니다.
service() 메서드가 실행되어 **DispatcherServlet는 HandlerMapping에게 질의를 하고 어떤 URL 요청에 대하여 어떤 메서드를 수행해야 할 지 분기**를 시킵니다.
**컨트롤러는 return에 작성된 경로명을 보고 응답할 View를 찾기 위해 `ViewResolver`에게 질의해서 View 객체를 반환 받고, 전달할 데이터를 추가하여 클라이언트에게 응답**합니다.

#### Thread safe
Root Application Context , Application Context 안에 들어 있는 객체는 Thread safe입니다.

예를들어 VO가 Root Application Context에 있을 경우, 다른 여러 요청이 같은 VO에 접근하면 이전 요청과 간섭이 발생하여 올바른 데이터를 얻지 못할 것입니다.

즉 각 컨테이너의 객체는 싱글톤이며, Thread safe 해야 합니다.

Thread safe하기 위해서는 객체에 인스턴스 변수가 없으면 됩니다.
나중에 코드를 보시면 아시겠지만 DAO , Controller는 외부에서 접근 가능한 멤버 변수가 없었습니다.
그러나 VO는 getter , setter로 멤버 변수 조작이 가능