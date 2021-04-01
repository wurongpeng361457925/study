# CSS

## 概念

层叠样式表 Cascading Style Sheets

- 样式定义**如何显示** HTML 元素
- 样式通常存储在**样式表**中
- 把样式添加到 HTML 4.0 中，是为了**解决内容与表现分离的问题**
- **外部样式表**可以极大提高工作效率
- 外部样式表通常存储在 **CSS 文件**中
- 多个样式定义可**层叠**为一个

## CSS优势

1. 内容和表现分离
2. 网页结构表现统一，可以实现复用
3. 样式丰富
4. 建议使用独立于HTML的CSS文件
5. 利用SEO，容易被搜索引擎收录　　（VUE前端框架不易于被SEO）

## CSS文件的导入方式

- 内部样式表
  - css样式代码写在HTML页面里的
- 外部样式表
  - css样式独立存在于.css文件中,HTML需要通过<link rel="stylesheet" href="xxx">引入
- 行内样式
  - 在HTML的标签中,通过标签的style属性编写样式
  - 外部样式的两种写法
    - 链接式<link rel="stylesheet" href="css/style.css">
    - 导入式 <style>@import url("css/style.css")</style> 
      - @import是css2.1版本的功能

优先级: 就近原则. 离当前标签近的生效

​	

## CSS三种选择器

- 标签选择器
  - 选择的是同类的标签  格式: 标签名{}
- class选择器
  - 给标签添加class属性  格式: .class名{}
- id选择器
  - 给标签添加id属性 格式: #id名{}

优先性: 

id选择器 > class选择器 > 标签选择器

## CSS高级选择器

### 层次选择器

p是标签

- 后代选择器
  - 格式 body p{}
- 子选择器
  - 格式 body>p{}
- 相邻兄弟选择器  只选择当前标签同级的下一个
  - 格式 .class名/#id名 +{}
- 通用选择器  当前标签的向下的所有兄弟元素
  - 格式 .class名 ~ p{}

### 结构伪类选择器

## 属性选择器

```css
格式
标签[标签中的属性]{}
a[href]{} //选择href属性的a标签
a[href][title] {color:red;} //选择href,title属性的a标签
```







```css
span,img{
    vertical-align: sub;// 垂直对齐方式 top,middle,sub
    horiz-align: left;  // 水平对齐方式
}

a[href] { // a 标签去掉下划线
            text-decoration-line: none;
        }
```

SASS :基于Ruby,通过服务器端处理,功能强大.需要熟悉Ruby语言

LESS:基于Node JS,通过客户端处理.使用简单.功能比SASS简单.解析效率低于SASS.平时开发的话LESS够用了