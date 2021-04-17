# Spring Security

## 概念

Spring Security是一个基于Spring的安全框架。提供全面的安全解决方案，在web请求级别和调用级别确认和授权。在Spring Framework基础上，Spring Security充分利用了依赖注入（DI） 和面向切面编程（AOP），为应用系统提供声明式的安全访问控制功能。是一个轻量级的安全框架，很好的集成了SpringMVC。

## 核心功能

1. 认证　Authentication　：用户是否为合法用户。
2. 授权　Authorization　：　用户是否有权执行某个操作

## 实现技术

- Filter
- Servlet
- AOP

web资源保护 -> Filter

方法调用保护 -> AOP

Spring Security进行用户的认证和授权时,通过各种拦截器来控制权限的访问,从而实现安全的

![image-20210417132134075](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210417132134075.png)

## 单点登陆

### 概念

在一个多应用系统中，只要在其中一个系统上登录之后，不需要在其它系统上登录也可以访问其内容

![image-20210417132820140](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210417132820140.png)

## JWT

上面提到的Token就是JWT

JWT : Json Web Token 

是一种通信双方之间传递安全信息的简洁的,URL安全的表述性声明规范.

一个JWT就是一个字符串.



由三部分组成,之间用"."连接

- 头部 (header),用base64加密 (对称加密)

  - typ,类型,这里是JWT类型
  - alg,加密算法 (HMAC,SHA256,RSA)

- 载荷 (payload) ,存放有效信息的地方,用base64加密 (对称加密)

  - 标准中注册的声明	(建议但不强制使用)
    - iss(issuer):JWT签发者
    - sub(subject):JWT所面向的用户
    - aud(audience):接收JWT的一方
    - exp(expiration time):JWT的过期时间,过期时间必须大于签发时间
    - nbf:在指定时间之前,JWT不可用
    - iat:JWT的签发时间
    - jti:JWT的唯一身份标识.主要用来作为一次性token,从而回避重放攻击
  - 公共的声明
    - 可以添加任何信息.一般添加用户相关的信息或业务需要的信息.不建议添加敏感信息,该部分在客户端可解密
  - 私有的声明
    - 提供者和消费者 共享的信息.不建议存放敏感信息.

- 签名 (signature)

  - base64加密后的header

  - base64加密后的payload

  - 盐 secret (在header中声明的加密方式进行加盐)

    - 注意：secret是保存在服务器端的，jwt的签发生成也是在服务器端的，secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥，在任何场景都不应该流露出去。一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。

      



## JWT流程

一般是在请求头离加入Authorization,并加上Bearer标注

```http
fetch('api/user/1',{
	headers:{
		'Authorization':'Bearer' +token
	}
})
```

服务端验证token,验证通过就返回相应的资源

![image-20210417130627076](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210417130627076.png)

如果token是在授权头（Authorization header）中发送的，那么跨源资源共享(CORS)将不会成为问题，因为它不使用cookie。

每一次请求都把token放在请求header中 -还需要将服务器设置为接受来自所有域的请求，用Access-Control-Allow-Origin: *

## JWT与Session的差异

相同点:都是存储的用户信息

不同点:Session保存在服务器端,JWT保存在客户端

​			Session存储会占用服务器端资源

​			JWT可以减轻服务器端资源开销的压力

## JWT优点		

- 因为json的通用性，所以JWT是可以进行跨语言支持的，像JAVA,JavaScript,NodeJS,PHP等很多语言都可以使用。
- 因为有了payload部分，所以JWT可以在自身存储一些其他业务逻辑所必要的非敏感信息。
- 便于传输，jwt的构成非常简单，字节占用很小，所以它是非常便于传输的。
- 它不需要在服务端保存会话信息, 所以它易于应用的扩展

### 安全相关

- 不应该在jwt的payload部分存放敏感信息，因为该部分是客户端可解密的部分。
- 保护好secret私钥，该私钥非常重要。
- 如果可以，请使用https协议

## Spring Security的工作流程

![image-20210417135322924](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210417135322924.png)



认证流程

![image-20210417140643530](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210417140643530.png)



授权流程

![image-20210417141628558](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210417141628558.png)

