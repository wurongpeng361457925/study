# Java注解 

Annotation ，JDK１.５　引入

Java注解可以通过反射获取注解内容 . 在编译器生成类文件时,注解可以被嵌入到字节码中.

JVM可以保留注解内容,在运行时获取到注解内容



Java元注解

| 注解        | 作用域         | 属性                               | 属性说明                       |
| ----------- | -------------- | ---------------------------------- | ------------------------------ |
| @Target     | Annotation类型 | ElementType                        | 枚举,8个值; 标注该注解的作用域 |
| @Retention  | Annotation类型 | RetentionPolicy                    | 该注解的保留策略,  默认.CLASS  |
| @Inherited  | Annotation类型 |                                    | 可否被继承                     |
| @Documented | 所有           |                                    |                                |
| @Repeatable |                | value  Class<? extends Annotation> |                                |



Annotation是一个接口

每个Annotation接口都有唯一的一个RetentionPolicy (保留策略) 属性 ,可以有多个ElementType (作用域)属性



ElementType是enum类型    有八种

```java
public enum ElementType {
    TYPE,               /* 1.类、接口（包括注释类型）或枚举声明  */

    FIELD,              /* 2.字段声明（包括枚举常量）  */

    METHOD,             /* 3.方法声明  */

    PARAMETER,          /* 4.参数声明  */

    CONSTRUCTOR,        /* 5.构造方法声明  */

    LOCAL_VARIABLE,     /* 6.局部变量声明  */

    ANNOTATION_TYPE,    /* 7.注释类型声明  */

    PACKAGE             /* 8.包声明  */
}
```

