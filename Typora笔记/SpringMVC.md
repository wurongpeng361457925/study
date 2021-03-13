# **SpringMVC**

SpringMVC 是一种基于 Java 的实现 MVC 设计模型的请求驱动类型的轻量级 Web 框架，它通过一套**注解**，让一个简单的 Java 类成为处理请求的控制器，而无须实现任何接口。同时它还支持RESTful 编程风格的请求。

-----

## **MVC架构[也是一种设计模式]：**

M: 模型(dao,service)

V：视图（jsp,html..）

C:控制器(servlet)

#### mvc架构好处：可以将 业务逻辑，数据，显示 来组织代码



MVVM：

M:模型层

V:view

VM:viewModel 双向绑定

## **SpringMVC原理图**

<img src="C:\Users\alienware\Pictures\Saved Pictures\SpringMVC流程图.png" style="zoom:150%;" />





---

## **第一个MVC项目**

### 1.导入依赖包

```xml
<!--        springmvc依赖-->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.1.9.RELEASE</version>
</dependency>

<!--        servlet依赖-->
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>servlet-api</artifactId>
    <version>2.5</version>
</dependency>

<!--        jsp页面依赖-->
<dependency>
    <groupId>javax.servlet.jsp</groupId>
    <artifactId>jsp-api</artifactId>
    <version>2.2</version>
</dependency>

<!--        jstl表达式依赖-->
<dependency>
    <groupId>javax.servlet.jsp.jstl</groupId>
    <artifactId>jstl</artifactId>
    <version>1.2</version>
</dependency>

<!--        lombok依赖-->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.8</version>
</dependency>
```

### 2.配置web.xml，注册DispatcherServlet

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <!--1.注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet
        </servlet-class>       <!--关联一个springmvc的配置文件:【servlet-name】-servlet.xml-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>       <!--启动级别-1-->
        <load-on-startup>1</load-on-startup>
    </servlet>
    <!--/ 匹配所有的请求；（不包括.jsp）-->   <!--/* 匹配所有的请求；（包括.jsp）-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

### **3.配置springMVC的配置文件，名称springmvc-servlet.xml(官方要求的名称)**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>
```

### 4.**添加处理器映射器**

```xml
<bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
```

### 5.**添加适配器**

```xml
<bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>
```

### **6.添加视图解析器**

```xml
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="InternalResourceViewResolver">
    <!--前缀-->
    <property name="prefix" value="/WEB-INF/jsp/"/>
    <!--后缀-->
    <property name="suffix" value=".jsp"/>
</bean>
```

### **7.添加业务层Controller，两种方式,一种实现controller接口【org.springframework.web.servlet.mvc.Controller】，一种使用@Controller注解，需要返回一个ModelAndView对象，用来封装数据和视图**

```java
package com.mxxb;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.addObject("msg","Hello ,SpringMVC");
        mv.setViewName("hello");

        return mv;
    }
}
```

### **8.把编写Controller类交给Spring管理**

```
<bean class="com.mxxb.MyController" id="/hello"/>
```

### **9.添加JSP页面，把ModelAndView中的数据放到页面中显示**

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MVC</title>
</head>
<body>
${msg}
</body>
</html>
```

### **10.配置tomcat，启动测试**

`可能遇到的问题：访问出现404，排查步骤：`

1. 查看控制台输出，看一下是不是缺少了什么jar包。

2. 如果jar包存在，显示无法输出，就在IDEA的项目发布中，添加lib依赖！

   <img src="C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210114231352137.png" alt="image-20210114231352137" style="zoom:67%;" /> 

![image-20210114231605469](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210114231605469.png)