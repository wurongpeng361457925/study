# HTML

## 概念

**H**yper **T**ext **M**arkup **L**anguage 超文本标记语言

超文本:文字,图片,音频,视频,动画等等

HTML5，提供了一些新的元素和一些有趣的新特性。同时也建立了一些新的规则。这些新的元素，特性和新规则，提供了许多新的网页功能，如使用网页实现动态渲染图形，图标，图像，动画，以及不需要安装任何插件直接使用网页播放视频等（之前需要安装flash插件）

## HTML5优势

- 知名浏览器厂商对HTML5的支持
  - 微软
  - Google
  - 苹果
  - opera
  - Mozilla
- 跨平台
- 市场的需求 

## W3C标准

### W3C

- World wid Web Consortium 万维网联盟
- 成立于1994年。web技术领域最权威和最具影响力的国际中立性技术标准机构
- 官网 http://www.w3.org     org:非营利性组织
- 中国官网 http://www.chinaw3c.org  

![image-20210324100654951](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210324100654951.png)

### W3C标准

- 结构化标准语言 HTML ，XML
- 表现标准语言 CSS
- 行为标准  DOM，ECMAScript



## HTML网页

```html
<!-- DOCTYPE 声明规范 -->
<!DOCTYPE html>
<html lang="en">
<!-- head 标识网页头部 -->
	<head>
	<!-- 描述标签  描述网页的信息 ,一般用来做SEO -->
		<meta charset="utf-8">
		<meta name="keywords" content="hello,html">
		<meta name="description" content="学习HTML"
		<!-- title 网页标题 -->
		<title>hello HTML</title>
	</head>
	<!-- body 标识网页主体 -->
	<body>
		
	</body>
</html>
```

### HTML标签

```html
<h1></h1>标题标签 1~6
<p></p> 段落标签
<br/>换行标签
<hr/>水平线标签
<strong></strong> 粗体
<em></em>斜体
&nbsp; 空格
&gt;   大于号>
&lt;   小于号<
&copy; 版权符号©
<img src="" alt="" title="" width="" height="">图片标签，alt：图片不显示时展示的字符，title：鼠标悬停展示内容
<a href="" name="top"></a> 连接标签
<a href="#top">回到顶部</a> 网页内跳转
<a href="mailto:123.@163.com">发送邮件</a>  链接发送邮件给123.@163.com
<video src="" controls autoplay></video> 视频  controls：播放控制，autoplay：打开网页自动播放
<audio src="" controls autoplay></audio> 音频 controls：播放控制，autoplay：打开网页自动播放
<iframe src="" name="" ></iframe> 创建包含另外一个文档的内联框架（即行内框架）

<label for="usr" >  label 标签为 input 元素定义标注（标记）。选择该标签时，浏览器就会自动将焦点转到和标签相关的表单控件上
        <p>用户名：<input type="text" name="username" id="usr"></p>
</label>
```

```html
列表
有序列表
<ol>
        <li>灰熊</li>
        <li>太阳</li>
        <li>马刺</li>
        <li>湖人</li>
</ol>
无序列表
<ul>
        <li>西甲</li>
        <li>意甲</li>
        <li>英超</li>
        <li>中超</li>
    </ul>
自定义列表
<dl>
        <dt>LOL</dt>
        <dd>安妮</dd>
        <dd>亚索</dd>
        <dd>杰斯</dd>
        <dd>李青</dd>
</dl>
```

## 页面结构

```html
<header></header>标题头部区域内容，用于页面或页面中的一块区域

<footer></footer>标记脚部区域内容，用于页面或页面中的一块区域

<selection></selection> Web页面中一块独立区域

<article></article>独立的文章内容

<aside></aside>相关内容或应用，常用于侧边栏

<nav></nav>导航类辅助内容
```

## 表单

```html	
表单初级验证 input框中
placeholder 提示信息
required  非空判断
pattern  正则表达式
```



### 块元素

无论内容多少，该元素独占一行

p标签，h1~h6标签

### 行内元素

内容成开宽度，左右都是行内元素的可以排在一行

a标签，strong加粗标签，em斜体标签

