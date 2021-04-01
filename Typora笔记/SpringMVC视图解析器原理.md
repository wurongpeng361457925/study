# SpringMVC视图解析器原理

![image-20210327132749093](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210327132749093.png)







拦截器实现：

1. 实现接口HandlerInterceptor，重写需要的handler方法
2. 把自定义的拦截器注册到容器中。
   1. 实现WebMvcConfigurer接口，重写addInterceptors方法