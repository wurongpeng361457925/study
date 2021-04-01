# JavaScript

## 概念

- （简称“JS”） 是一种具有函数优先的轻量级，解释型或即时编译型的编程语言
- JavaScript 是 Web 的编程语言。
- 所有现代的 HTML 页面都使用 JavaScript。
- JavaScript 在 1995 年由Netscape公司的 Brendan Eich 在网景导航者浏览器上首次设计实现而成，并于 1997 年成为一部 ECMA 标准。
- 因为Netscape与Sun合作，Netscape管理层希望它外观看起来像Java，因此取名为JavaScript
- ECMA-262 是其官方名称。ECMAScript 6 （发布于 2015 年）是最新的 JavaScript 版本

## JavaScript运行模式

1. 是一种解释性脚本语言（代码不进行[预编译](https://baike.baidu.com/item/预编译)）。 
2. 主要用来向[HTML](https://baike.baidu.com/item/HTML)（[标准通用标记语言](https://baike.baidu.com/item/标准通用标记语言)下的一个应用）页面添加交互行为。 
3. 可以直接嵌入HTML页面，但写成单独的[js](https://baike.baidu.com/item/js/10687961)文件有利于结构和行为的[分离](https://baike.baidu.com/item/分离)。
4. 跨平台特性，在绝大多数浏览器的支持下，可以在多种平台下运行（如[Windows](https://baike.baidu.com/item/Windows)、[Linux](https://baike.baidu.com/item/Linux)、[Mac](https://baike.baidu.com/item/Mac/173)、[Android](https://baike.baidu.com/item/Android/60243)、[iOS](https://baike.baidu.com/item/iOS/45705)等）。
5. JavaScript脚本语言同其他语言一样，有它自身的基本数据类型，表达式和[算术运算符](https://baike.baidu.com/item/算术运算符/9324947)及程序的基本程序框架。JavaScript提供了四种基本的数据类型和两种特殊数据类型用来处理数据和文字。而变量提供存放信息的地方，表达式则可以完成较复杂的信息处理。

## JavaScript语言特点

1. 脚本语言
2. 基于对象
3. 简单
4. 动态性
5. 跨平台性

## JavaScript加载方式

- 在HTML文件中使用<script type="text/javascript"></script>标签,type属性可以省略,默认text/javascript
  - 把脚本置于 **<body>** 元素的底部，可改善显示速度，因为脚本编译会拖慢显示。
- 引入外部js文件 .js
- 可通过完整的 URL 或相对于当前网页的路径引用外部脚本



## JavaScript输出

- 使用 window.alert() 写入警告框
- 使用 document.write() 写入 HTML 输出  (仅用于测试)
- 使用 innerHTML 写入 HTML 元素
- 使用 console.log() 写入浏览器控制台

## ==和===

== 在比较之前进行类型转换（以匹配类型）。

=== 强制对值和类型进行比较

## eval()

eval() 函数用于将文本作为代码来允许

允许任意代码运行，它同时也意味着**安全问题**

## JavaScript数据类型

JavaScript 属于松散类型。变量可包含不同的数据类型，并且变量能够改变其数据类型

```JavaScript
var x = "Hello";     // typeof x 为字符串
x = 5;               // 把 typeof x 更改为数值
```

typeof 运算符可返回以下原始类型之一：

- string
- number
- boolean
- undefined

### String

JavaScript中,String类型使用 *\ 转义字符*。

反斜杠转义字符把特殊字符转换为字符串字符：

| 代码 | 结果 | 描述   |
| :--- | :--- | :----- |
| \'   | '    | 单引号 |
| \"   | "    | 双引号 |
| \\\  | \    | 反斜杠 |

```JavaScript
// (x == y) 为 false，因为 x 和 y 是不同的对象
var x = new String("Bill");             
var y = new String("Bill");

// (x === y) 为 false，因为 x 和 y 的类型不同（字符串与对象）
var x = "Bill";             
var y = new String("Bill");

三种提取部分字符串的方法：
slice(start, end)  start end某个参数为负，则从字符串的结尾开始计数
substring(start, end)  无法接受负的索引。
substr(start, length) 第二个参数规定被提取部分的长度
```

### Number

JavaScript 数值始终是 64 位的浮点数

JavaScript 数值始终以双精度浮点数来存储，根据国际 IEEE 754 标准。

此格式用 64 位存储数值，其中 0 到 51 存储数字（片段），52 到 62 存储指数，63 位存储符号：

| 值(aka Fraction/Mantissa) | 指数              | 符号       |
| :------------------------ | :---------------- | :--------- |
| 52 bits(0 - 51)           | 11 bits (52 - 62) | 1 bit (63) |

#### 加法和级联（concatenation）

JavaScript 的加法和级联（concatenation）都使用 + 运算符

- 两个数相加，结果将是一个数
- 两个字符串相加，结果将是一个字符串的级联
- 对一个数和一个字符串相加，结果也是字符串级联
- 对一个字符串和一个数字相加，结果也是字符串级联



#### NaN

NaN = Not a Number, 

JavaScript的保留字,表示某个数不是合法数

NaN是数, typeof NaN返回number

#### Infinity

Infinity或-Infinity是JavaScript在计算数时超出最大可能数范围时返回的值



```JavaScript
Number(new Date("2021-03-24"))
1616544000000
```



Undefined 与 Null 的区别

Undefined 与 null 的值相等，但类型不相等

```js
typeof undefined              // undefined
typeof null                   // object
null === undefined            // false
null == undefined             // true
```



## JavaScript函数

**JavaScript 函数是被设计为执行特定任务的代码块。**

**JavaScript 函数会在某代码调用它时被执行。**

```javascript
JavaScript函数
格式
function 函数名(参数 1, 参数 2, 参数 3) {
    要执行的代码
}
函数返回 return
function myFunction(a, b) {
    return a * b;                // 函数返回 a 和 b 的乘积
}
```

## JavaScript对象

```JavaScript
语法
var 名称 = {属性1:"value1",属性2:"value2",属性3:"value3"...}
```

### 对象方法

对象也可以有*方法*。

方法是在对象上执行的*动作*。

方法以*函数定义*被存储在属性中。

注意:

​	请避免字符串、数值或逻辑对象。他们会增加代码的复杂性并降低执行速度

```javascript
function stu() {
    var student = {
        name: "spy",
        age: 21,
        address: "Shanghai",
        email: "123@123.com",
        course: "体育",
        study: function () {  // 对象中定义的函数
            return this.name + "喜欢" + this.course + "课";
        }

    };
    var hobby = student.study();
    document.getElementById("a").value = hobby;
}

访问对象方法
var hobby = student.study();   //访问方法
var fun = student.study;       //返回方法定义
```

### 访问对象属性

```JavaScript
两种方式
1. 对象名.属性名;      person.lastName;
2. 对象名["属性名"];   person["lastName"];
```

## JavaScript事件

```JavaScript
使用 this.innerHTML改变了其自身元素的内容 
<button onclick="this.innerHTML=new Date()">时间</button>
```

下面是一些常见的 HTML 事件：

| 事件        | 描述                         |
| :---------- | :--------------------------- |
| onchange    | HTML 元素已被改变            |
| onclick     | 用户点击了 HTML 元素         |
| onmouseover | 用户把鼠标移动到 HTML 元素上 |
| onmouseout  | 用户把鼠标移开 HTML 元素     |
| onkeydown   | 用户按下键盘按键             |
| onload      | 浏览器已经完成页面加载       |

```javascript
window.onload = function () {
            document.getElementById("c").onclick=fn1;
            function fn1() {
                this.innerHTML="huhu";
            }
        }
```

