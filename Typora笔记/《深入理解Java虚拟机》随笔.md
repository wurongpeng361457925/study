# 《深入理解Java虚拟机》随笔

# Java语言优点

- 一次编译,到处运行
- 相对安全的内存管理和访问机制,避免了大部分内存泄漏和指针越界
- 热点代码探测和运行时编译及优化
- 完善的应用程序接口,开源社区的第三方类库
- 等等...

Sun官方定义的Java技术体系:

- Java程序设计语言
- 各种硬件平台上的Java虚拟机
- 
- class文件格式
- Java api类库
- 商业机构,开源社区的第三方Java类库

JDK包括:

- java程序设计语言
- JVM
- Java api
  - Java SE api JVM 统称为JRE,  JRE是支持Java运行的标准环境



ERP :Enterprise Resource Planning,企业资源计划

​	建立在信息技术基础上以系统化的管理思想企业决策及员工提供决策运行手段的**管理平台**

CRM :Customer RelationShip Management, 客户关系管理

# 	Java语言发展史

- 91年4月, James Gosling领导的Green Project计划, 开发一种能在各种电子产品上运行的程序架构,Oak语言

- 95年5月 Oak改名为Java,在Sunworld大会上发布Java1.0版本,提出"一次编译,到处运行"口号

- 96年1月 发布 Sun Classic VM, Java1.0的代表技术:JVM,Applet,AWT

- 96年5月,第一届JavaOne大会,旧金山

- 97年2月,JDK1.1 ,Jar文件格式,JDBC, JavaBeans, RMI, Inner Class, 反射 Reflection

- 99年4月,HotSpot VM发布. LongView Technolgies公司开发,97年被Sun公司收购.JDK1.3之后HotSpot为默认虚拟机

- 2000年5月  JDK1.3发布,JNDI服务作为一项平台级服务提供。HotSpot作为Java默认虚拟机。JDK1.3开始，Sun大约两年发布一个JDK主版本，以动物命名主版本。用昆虫命名修正版本

  - JNDI：Java Naming and Directory Interface    Java命名与目录接口 （现在很少用了 ）

    类似于在一个中心注册一个东西，以后要用的时候，只需要根据名字去注册中心查找，注册中心返回你要的东西，如DNS、文件服务、数据库等

- 2002年2月，JDK1.4发布。Java真正走向成熟的版本。很多公司都有参与。 同年，Windows的 .net发布。.net类似 Java

- 2004年9月，JDK1.5发布，工程代号tiger。在Java语法易用性上做出了很多改进。如：自动拆装箱，泛型，注解，枚举，可变长参数，foreach循环等。改进了Java内存模型（JMM Java Memory Model），提供了Java.util.concurrent包

  - JDK1.5开始，官方已经不再使用类似 JDK1.5的命名，而采用 JDK 5, JDK 6, JDK 7 的命名方式

- 2006年11月，Java One大会，Sun公司宣布将Java开源。

- 2007年，建立OpenJDK组织

- 2009年4月，Oracle 74亿美元收购Sun公司，Java商标归Oracle所有

- 2011年7月, 发布 JDK 7 

- 2014年3月 , 发布 JDK 8 

- 2018年 , Java One被Oracle重命名未Code One

- Oracle管理Java版本生命周期采用LTS长期支持的概念(Long Term Support),每三年指定一个LTS版本.JDK8 , JDK11都是LTS版本 .还有像Java 12, Java 13 这样的FR版本 (Feature Release 版本) 

- 2019年3月,  Alibaba Dragon well 8.0.0开源 阿里巴巴的 JDK版本  Alibaba Dragon well -AJDK

# JVM发展史

- Sun Classic VM ：Sun公司推出的世界上第一款商用Java虚拟机。只能使用纯解释器来执行Java代码。使用外挂的方式使用 JIT编译器
- Exact VM ：两级及时编译，编译器与解释器混合工作。 使用准确式内存管理而得名 exact-准确
- HotSpot VM ： 热点代码探测技术。通过执行计数器找出最具编译价值的代码，通过JIT编译器以方法为单位进行编译
- JRockit VM ： BEA公司，号称“世界最快的Java虚拟机” 。专注于为服务器提供JVM。不注重程序启动速度，全部代码都靠及时编译器编译后执行
- J9 VM ： IBM公司。J9于HotSpot定位类似，为多用途VM（服务器，桌面应用，嵌入式等）
- Azul VM ： Azul Systems公司，在HotSpot基础上改进。运行在Vega系统的Java虚拟机
- Liquid VM ： BEA公司。就是JRockit VE，运行在Hypervisor系统
- Apache Harmory ：Apache软件基金会的VM。未通过TCK认证 （Technology Compatibility Kit）。Java开源后该VM被极大的削弱
- Dalvik VM ： Android平台的核心组成之一。没有遵守Java虚拟机规范。使用的寄存器架构。执行.dex文件，可通过.class文件转换
- Microsoft JVM　：　微软公司，（微软目的：Java从跨平台技术变成绑定在Windows上的技术）

