# Java泛型

## 概念

泛型的概念是JDK1.5 推出的

> 泛型，即**参数化类型**，类型可以像参数一样传递。

定义的时候定义为形参 类型

使用的时候传入实参 类型

**要求**：

传入的实参类型 必须为**类类型**，

不能为基本类型 ( btye,short,int ,char,float,double,long)

## 泛型作用

- 编译器在编译期 对类型进行检查，提高类型安全
- 避免运行时由于对象类型不一致引发的异常
- 使代码更加通用性

## 泛型特性

**只在编译期有效** ，泛型不会进入运行阶段

编译阶段，对参数化类型进行检查，编译不通过则报错

通过的话，把泛型相关的信息擦除

## 泛型作用域

泛型可以用于

- 类
- 接口
- 方法
- 变量



## 泛型类

通过泛型可以完成对一组类的操作对外开放相同的接口

在实例化类的时候指明泛型的具体类型

**基本语法** 

泛型定义在类名之后，<>

```java 
class 类名<泛型标识>{
	private 泛型标识 成员变量；
	
}
```

## 泛型接口

泛型接口通常用在各种类的生产器中

泛型定义咋接口名之后

```java
public interface Generetor<T>{
	public T next();
}
```

当实现泛型接口的类，未传入泛型实参时 , 需要将泛型的声明也加到类中 （类名后面<>）

```java
class FruitGenerator<T> implements Generetor<T> {
    	public T next(){
            	return null;
        }
}
```

当时先泛型接口的类，传入泛型实参时，则所有使用泛型的地方都被替换成传入的泛型实参

```java
public class FruitGenerator implements Generator<String> {
    private String[] fruits = new String[]{"apple","banana"};
    @Override
    public void next(){
        Random r = new Random();
        return fruits[r.nextInt(2)];
    }
}	
```

## 泛型通配符

同一种泛型可以对应多个版本   		（因参数类型不确定）

不同版本的泛型类实体是不兼容的     （参数类型已确定）

类型通配符一般使用 ? 代替具体的**类型实参**

```java
public void showKey(Generetor<?> obj){
    log.info(obj.getKey());
}
```

当具体类型不确定的时候，可以使用通配符的泛型  "?"

## 泛型方法

在调用方法的时候知名泛型的具体类型

```java
public <T> T genericMethod(class<T> clazz){
	T instance = clazz.newInstance();
    return instance;
}
```

- public 与 返回值 之间的<T> ，起声明作用，表示该方法为泛型方法

- 只有声明了<T> 的方法才是泛型方法，泛型类中使用了泛型的成员方法并不是泛型方法

- 只有在方法上声明 <T> ，才可以在方法中使用泛型类型 T 

- 与泛型类相同，此处的 T 可以定义为任意字符，常见的有 T，E，K，V 等表示泛型

- 泛型方法能使方法独立于类而产生变化

- 静态方法不能使用类上定义的泛型，需要静态方法自己声明泛型

- 任何地方都不能使用未被声明的泛型，(类，接口 -> 名称后面<T> ；方法 -> 修饰符 和 返回值 之间  <T>)

  

## 泛型的上线边界

使用泛型的时候，可以为泛型指定 泛型实参的 上下边界限制。

e.g 只能传入某类型的子类 或者 某类型的父类

上下边界 必须是在 泛型声明处指定，不能在使用的时候指定边界

```java
public <T extends Number> T showData(Generic<T> data){
	
}
```

