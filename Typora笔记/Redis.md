[TOC]



# Redis

NoSQL : Not ONLY SQL



## NoSQ存储的四大分类

- 键值对
  - redis
  - memecache
- 列存储
  - HBase
  - 分布式文件系统
- 文档存储
  - MongoDB : 是介于关系型数据库和菲关系型数据库之间的, 是菲关系型数据库中功能最丰富
- 图形数据库 : 放的是关系,比如朋友圈社交网络,广告推荐等等...
  - Neo4j
  - InfoGrid

![image-20210228161138970](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210228161138970.png)



大数据量高性能 redis 每秒写8w次, 读 11万次



大数据时代的3V 3高

​	

3V:主要是描述问题的

- 海量 volume
- 多样 variety
- 实时 velocity

3高:主要是对程序的要求

- ​	高并发
- ​    高可扩
- ​    高性能

去IOE :

​	I : IBM 服务器提供商 ,俗称 小型机   几十万到百万

​    O : Oracle 数据库提供商    几千万

​	E : EMC 存储设备提供商, 集中式存储

文档型数据库  MongoDB



图片

​		FastDFS  分布式文件系统

​		TFS		淘宝

​		GFS		Google

​		HDFS	 Hadoop

​		OSS		阿里云



> ## Redis是什么

Redis（[Re]()mote [Di]()ctionary [S]()erver )，即远程字典服务 ，C语言编写，kv数据库，提供多种语言的api

免费，开源



## Redis能干嘛

redis 可以作为数据库 ,缓存,消息中间件MQ

- 内存存储，持久化（RDB，AOF）
- 效率高，可用于高速缓存
- 发布订阅系统
- 地图信息分析
- 计时器，计数器 （浏览量）

## Redis特性

- 数据类型多样
- 持久化
- 集群
- 事务
- ........

> Redis是单线程的

Redis基于内存操作,CPU不是redis的性能瓶颈, redis的瓶颈是根据机器的内存和带宽

cpu线程切换需要消耗时间,对于内存系统来说没有上下文切换效率就是最高的



## 五种基本数据类型

- String                   字符串

- List						列表

- Set						集合

- ZSet (sort set)		有序集合

- Hash					 散列


## 特殊数据类型

- geospastial		地理空间

- hyperloglog		范围查询

- bitmaps				范围查询


## 查看Redis版本

在redis的安装目录bin

```bash
[viper@192 bin]$ pwd
/usr/local/bin
# redis-server -v 查看redis版本
[viper@192 bin]$ redis-server -v
Redis server v=6.2.0 sha=00000000:0 malloc=jemalloc-5.1.0 bits=64 build=24d770f805233f28
# redis-server --version 查看redis版本
[viper@192 bin]$ redis-server --version
Redis server v=6.2.0 sha=00000000:0 malloc=jemalloc-5.1.0 bits=64 build=24d770f805233f28
```



# Redis数据类型

## **String类型**

```bash
127.0.0.1:6379[1]> select 0  #选择数据库,redis默认有16个库
OK
127.0.0.1:6379> FLUSHALL  #清空当前库中的所有数据
OK
127.0.0.1:6379> set name Viper #设置一个key为name,值为Viper
OK
127.0.0.1:6379> get name   #获取key
"Viper"
127.0.0.1:6379> APPEND name ,hello  #在name的key后面追加 内容
(integer) 11
127.0.0.1:6379> STRLEN name  #获取key为name的value长度
(integer) 11
127.0.0.1:6379> get name
"Viper,hello"
```

```bash
127.0.0.1:6379> incr see    #自增1
(integer) 3
127.0.0.1:6379> decr see    #自减1
(integer) 2
127.0.0.1:6379> incrby see 10   #设置自增步数 10
(integer) 12
127.0.0.1:6379> DECRBY see 5    #设置自减步数 5

########################################################
127.0.0.1:6379> GETRANGE key1 0 4   #获取字符串指定长度内容 range : 范围
"hello"
127.0.0.1:6379> STRLEN key1
(integer) 11
127.0.0.1:6379> GETRANGE key1 6 11
"viper"
127.0.0.1:6379> getrange key1 0 -1  #查看全部字符串内容  0 到 -1,同get key
"hello,viper"
########################################################
127.0.0.1:6379> set key2 aaaaa
OK
127.0.0.1:6379> setrange key2 1 bb  #范围替换指定位置的字符串
(integer) 5
127.0.0.1:6379> get key2
"abbaa"
########################################################

# setnx 不存在则新建 (在分布式锁中常用)
# setex
127.0.0.1:6379> setex key 120 "hello" #设置计时key120秒
OK
127.0.0.1:6379> setex key 150 "viper" #重设key时间和值
OK
127.0.0.1:6379> get key
"viper"
127.0.0.1:6379> setnx key "haha"  #如果key不存在则新建key,这里存在新建key失败
(integer) 0
127.0.0.1:6379> setnx key1 "haha" #setnx key if not exists
(integer) 1		# 不存在key1,成功新建key1
127.0.0.1:6379> setnx key1 "hehe"
(integer) 0
########################################################
# mset   批量设置
# mget   批量获取
# msetnx 批量设置多个值,若存在则全部创建失败,原子操作
127.0.0.1:6379> mset k1 v1 k2 v2 k3 v3
OK
127.0.0.1:6379> mget k1 k2 k3
1) "v1"
2) "v2"
3) "v3"
127.0.0.1:6379> msetnx k1 v1 k4 v4
(integer) 0
#########################################################
#getset 先get 再set
127.0.0.1:6379> getset db redis #如果不存在值,返回null
(nil)
127.0.0.1:6379> get db
"redis"
127.0.0.1:6379> getset db mongodb #如果存在值,获取原来的值,并设置新的值 CAS
"redis"
127.0.0.1:6379> get db
"mongodb"
##########################################################
#设置对象
#设置的key为 user:1:name, user:1:age 
127.0.0.1:6379> mset user:1:name zhangsan user:1:age 20
OK
127.0.0.1:6379> mget user1
1) (nil)
127.0.0.1:6379> mget user:1:name user:1:age
1) "zhangsan"
2) "20"

```

## List**类型**

​	redis中的List可以当做 栈, 队列, 阻塞队列 来使用

所有的List 相关命令都是以 L 开头

```bash
127.0.0.1:6379> lpush list 1  #将一个或多个值放到列表的头部(左)
(integer) 1
127.0.0.1:6379> lpush list 2
(integer) 2
127.0.0.1:6379> lpush list 3
(integer) 3
127.0.0.1:6379> lrange list 0 -1
1) "3"
2) "2"
3) "1"
127.0.0.1:6379> RPUSH list right  # Rpush 将value从右边放入列表尾部(右)
(integer) 4
127.0.0.1:6379> LRANGE list 0 -1
1) "3"
2) "2"
3) "1"
4) "right"
127.0.0.1:6379> lpop list  # 从左弹出列表的第一个值,并从列表中删除
"3"
127.0.0.1:6379> rpop list	# 从右弹出列表的第一个值,并从列表中删除
"right"
127.0.0.1:6379> lrange list 0 -1
1) "2"
2) "1"
127.0.0.1:6379> lindex list 0 #通过下标获取值
"2"
127.0.0.1:6379> lindex list 1
"1"
127.0.0.1:6379> llen list  # 返回列表的长度
(integer) 2
127.0.0.1:6379> lrem list 2 3 #移除值,list中可有重复的值 2: 列表中值的个数,3:具体值
(integer) 2
####################################################
127.0.0.1:6379> lpush list hello
(integer) 1
127.0.0.1:6379> lpush list hello1
(integer) 2
127.0.0.1:6379> lpush list hello2
(integer) 3
127.0.0.1:6379> lpush list hello3
(integer) 4
127.0.0.1:6379> lrange list 0 -1
1) "hello3"
2) "hello2"
3) "hello1"
4) "hello"
127.0.0.1:6379> LTRIM list 1 2 # 通过下标截取指定的长度,list已经改变,list中只剩下截取的元素
OK
127.0.0.1:6379> lrange list 0 -1
1) "hello2"
2) "hello1"
#############################################
#rpoplpush 移除列表中最后一个元素并添加到其他列表中
127.0.0.1:6379> rpush list 1
(integer) 1
127.0.0.1:6379> rpush list 2
(integer) 2
127.0.0.1:6379>  rpush list 3
(integer) 3
127.0.0.1:6379> rpoplpush list mylist #移除列表中最后一个元素并添加到其他列表中
"3"
127.0.0.1:6379> lrange list 0 -1
1) "1"
2) "2"
127.0.0.1:6379> lrange mylist 0 -1
1) "3"
#################################################
#lset
127.0.0.1:6379> exists list #判断 list是否存在
(integer) 0
127.0.0.1:6379> lset list 0 hello #若list不存在.lset报错
(error) ERR no such key
127.0.0.1:6379> lpush list tempValue
(integer) 1
127.0.0.1:6379> lrange list 0 0
1) "tempValue"
127.0.0.1:6379> lset list 0 viper #若存在list,更新对应下标的值
OK
127.0.0.1:6379> lrange list 0 0 
1) "viper"
127.0.0.1:6379> lset list 1 tom #list中没有下标1,更新元素失败
(error) ERR index out of range
#################################################
#linsert
127.0.0.1:6379> rpush list "hello"
(integer) 1
127.0.0.1:6379> rpush list "world"
(integer) 2
127.0.0.1:6379> linsert list before world , #在list指定值world之前插入','
(integer) 3
127.0.0.1:6379> lrange list 0 -1
1) "hello"
2) ","
3) "world"
127.0.0.1:6379> linsert list after world xixi #在list指定值world之后插入'xixi'
(integer) 4
127.0.0.1:6379> lrange list  0 -1
1) "hello"
2) ","
3) "world"
4) "xixi"

127.0.0.1:6379> type list #显示数据类型
list

```

- list实际是一个双向链表 before node , after node 都可以插入值

list 可用作 消息队列 ,  (lpush rpop 队列) ( lpush lpop 栈[只有一个出口])



## set**类型**

 set的命令都是以 s 开头

````bash
###############################################
#sadd set中添加值
27.0.0.1:6379> sadd set hello
(integer) 1
127.0.0.1:6379> sadd set world
(integer) 1
#sismember
127.0.0.1:6379> sismember set hello # set中是否存在某元素
(integer) 1
#smembers 查看set中的所有元素
127.0.0.1:6379> smembers set
1) "world"
2) "hello"
#scard 获取set中元素个数
127.0.0.1:6379> scard set
(integer) 2
#srem 移除set中的指定元素
127.0.0.1:6379> SREM set ,
(integer) 1
# srandmember 随机抽出set 中的元素 
127.0.0.1:6379> srandmember set
"3"
# 随机抽出set 中的元素,指定个数
127.0.0.1:6379> srandmember set 2
1) "3"
2) "world"
# spop 随机从set中弹出一个元素
127.0.0.1:6379> spop set
"hello"
# smove 从set中移动指定元素到另一个set中
127.0.0.1:6379> smove set set1 tom
(integer) 1
####################################################
# 差集 sdiff
# 交集 sinter
# 并集 sunion
127.0.0.1:6379> sdiff set1 set2 #差集,set1 
1) "a"
2) "b"
127.0.0.1:6379> sdiff set2 set1
1) "e"
2) "d"
127.0.0.1:6379> sinter set1 set2 # 交集
1) "c"
127.0.0.1:6379> sinter set2 set1
1) "c"
127.0.0.1:6379> sunion set1 set2 # 并集
1) "a"
2) "b"
3) "c"
4) "e"
5) "d"
127.0.0.1:6379> sunion set2 set1
1) "b"
2) "d"
3) "c"
4) "e"
5) "a"
127.0.0.1:6379> sunion set1 set2
1) "a"
2) "b"
3) "c"
4) "e"
5) "d"

````

六度分割理论



## hash**类型**

​	map集合

​	key-map

````bash
# hset 设置hash,key为hash 值为name:viper,age:12,address:beijing
127.0.0.1:6379> hset hash name viper age 12 address beijing
(integer) 3
#  hget获取指定key的值
127.0.0.1:6379> hget hash name
"viper"
# hmget 获取所有key的值
127.0.0.1:6379> hmget hash name age address
1) "viper"
2) "12"
3) "beijing"
# hmgetall 获取所有键值
127.0.0.1:6379> hgetall hash
1) "name"
2) "viper"
3) "age"
4) "12"
5) "address"
6) "beijing"
# hlen 获取hash中kv对个数
127.0.0.1:6379> hlen hash
(integer) 3
# hexists 判断指定key在hash中是否存在
127.0.0.1:6379> hexists hash name
(integer) 1
# hdel 删除指定key,value也会被删除
127.0.0.1:6379> HDEL hash age
(integer) 1
# hkeys 获取hash中全部的key
127.0.0.1:6379> hkeys hash
1) "name"
2) "address"
# hvals 获取hash中全部的value
127.0.0.1:6379> hvals hash
1) "viper"
2) "beijing"
##################################################
# hincrby 自增
127.0.0.1:6379> HINCRBY hash num 1
(integer) 2

# hsetnx 若存在则设置key失败,反之成功
127.0.0.1:6379> hsetnx hash email xxx@qq.com
(integer) 1


````

hash类型的应用:

​		存变更的数据, 适合存储对象,String适合存储字符串

​		



## ZSet 类型

​	有序集合 sorted set

```bash
# zadd 添加元素到 zset中 100 为score数
127.0.0.1:6379> zadd salary 100 wu
(integer) 1
127.0.0.1:6379> zadd salary 10 vi
(integer) 1
127.0.0.1:6379> zadd salary 88 tom
(integer) 1
127.0.0.1:6379> zadd salary 33 tu
# zrangebyscore 排序 从最小到88
127.0.0.1:6379> zrangebyscore salary -inf 88
1) "vi"
2) "tu"
3) "tom"
# 排序 并显示score
127.0.0.1:6379> ZRANGEBYSCORE salary -inf +inf withscores
1) "vi"
2) "10"
3) "tu"
4) "33"
5) "tom"
6) "88"
7) "wu"
8) "100"

```

## **geospatial类型**

地理位置,朋友的定位,附近的人,

```bash
#Geoadd 添加地理位置
127.0.0.1:6379> geoadd china:city 116.40 39.90 beijing
(integer) 1


#getdist 查看两地距离
127.0.0.1:6379> geodist china:city beijing shanghai km 
"1067.3788"

#zrange 查看geo中所有元素
127.0.0.1:6379> zrange china:city 0 -1
1) "chognqing"
2) "xian"
3) "shengzhen"
4) "hangzhou"
5) "shanghai"
6) "beijing"
#georadius  显示到中间距离的位置
127.0.0.1:6379> GEORADIUS china:city 110 30 1000 km withdist

```

geospatial 底层是基于zset实现的 



## **Hyperloglog类型**

基数( 不重复的元素 ) , 可以接受误差   0.81%的错误率

Redis 2.8.9 版本更新了 Hyperloglog数据结构

占用的内存是固定  , 2 ^64不同元素的基数 ,只需要 12 KB 内存

Hyperloglog采用的就是基数统计的算法，基本思想采用 伯努利过程（抛硬币，第一次出现正面时抛的次数）

![image-20210306200636309](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210306200636309.png)





场景 :  统计网站uv (  访问量 )





````bash
# pfadd 在Hyperloglog中添加元素
127.0.0.1:6379> PFADD mykey a b c d e f g h i j
(integer) 1
# 显示Hyperloglog中元素的数量(重复的算做一个)
127.0.0.1:6379> pfcount mykey
(integer) 10
127.0.0.1:6379> pfadd mykey2 i j z x c v b n m
(integer) 1
127.0.0.1:6379> pfcount mykey2
(integer) 9
# pfmerge 把mykey mykey2 合并到mykey3中 ( 并集 )
127.0.0.1:6379> pfmerge mykey3 mykey mykey2
OK
127.0.0.1:6379> pfcount mykey3
(integer) 15

````

```bash
MISCONF Redis is configured to save RDB snapshots, but it is currently not able to persist on disk. Commands that may modify the data set are disabled, because this instance is configured to report errors during writes if RDB snapshotting fails (stop-writes-on-bgsave-error option). Please check the Redis logs for details about the RDB error.
# 原因是因为强制把redis快照关闭了导致不能持久化的问题
127.0.0.1:6379> config set stop-writes-on-bgsave-error no
```



## **bitmap类型**    

## 位图

位存储 ,操作二进制位来进行记录,只有  0 和 1 两个状态   bypte数组

BitMap是String类型，最大为512MB

e.g 

设置 key 为 big，将big对应的ASCII表的数存储到 对应的位图 b=98  i=105  g=103

​			offset 0 ----> bitArray.length

![image-20210308172643997](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210308172643997.png)

````bash
# setbit 对key所存储的字符串值，设置或清除指定偏移量上的位（bit）
# 1. 返回值为该位在setbit之前的值
# 2. value只能取0或1
# 3. offset从0开始，即使原位图只能10位，offset可以取1000
127.0.0.1:6379> setbit sign 0 1
(integer) 0
127.0.0.1:6379> setbit sign 1 1
(integer) 0
127.0.0.1:6379> setbit sign 2 1
(integer) 0
127.0.0.1:6379> setbit sign 3 1
(integer) 0
127.0.0.1:6379> setbit sign 4 1
(integer) 0
127.0.0.1:6379> setbit sign 5 1
(integer) 0
127.0.0.1:6379> setbit sign 6 0
(integer) 0
# getbit 获取key指定位置的值 （0 或 1）
127.0.0.1:6379> getbit sign 5
(integer) 1
# 0 表示无状态
127.0.0.1:6379&gt; getbit sign 6
(integer) 0
# bitcount 统计 位为1的个数
127.0.0.1:6379> bitcount sign
(integer) 5

````

## 布隆过滤器

#### 概念

布隆过滤器是一种 **空间高效** **概率性**的数据结构

1970年由 Burton Howard Bloom提出

#### **数据结构**

- ​	bit数组 : 存储bit位  0 和 1
- ​	k个hash函数: 用来给数据做hash运算来映射到bit数组中

#### 作用

判断一个元素是否存在于某个集合中

处理的结果有两种：

- 可能存在
- 一定不存在

#### **优点**	

- 二进制数组组成的数据结构,占用空间小
- 查询插入效率快, 时间复杂度为O(K)  k为hash函数的个数
- 不存储原始数据,安全性

#### **缺点**

- 很难进行修改删除操作
- 结果存在误判 (专业术语称为假阳性 false positive)

#### 布隆过滤器误判率

(假阳性) 

影响误判率的因素:

- bit数组大小
- hash函数个数

bit数组越大,hash函数越多,误判率越小。但是占用的空间和时间也就越多

#### 布隆过滤器解决 

缓存穿透

![image-20210308161927751](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210308161927751.png)

#### 使用场景

##### 推送内容

![image-20210308162049107](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210308162049107.png)



##### 内容转载限制

![image-20210308162134931](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210308162134931.png)

Redis的单条命令可以保证原子性的

但是Redis事务不保证原子性

Redis事务的本质 :  一组命令的集合

Redis事务中的所有命令都会被序列化 , 在事务执行过程中,会按照顺序执行

特点 : 

-    一次性
-    顺序性
-    排他性

Redis事务 没有隔离级别的概念

## Redis事务的阶段 

- ​	开启事务 (  multi )
- ​    命令入队
  - 放弃事务 （discard）
- ​    执行事务 ( exec )

```bash
# multi 开始事务
127.0.0.1:6379> multi
OK
# 命令入队
127.0.0.1:6379(TX)> set k1 v1
QUEUED
# 命令入队
127.0.0.1:6379(TX)> set k2 v2
QUEUED
# 命令入队
127.0.0.1:6379(TX)> get k2
QUEUED
# 命令入队
127.0.0.1:6379(TX)> set k3 v3
QUEUED
# exec 执行 队列中的命令
127.0.0.1:6379(TX)> exec
1) OK
2) OK
3) "v2"
4) OK
#  discard 放弃事务
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> set k4 v4
QUEUED
#  discard 放弃事务
127.0.0.1:6379(TX)> discard
OK

```

事务中命令错误

> 编译型异常 ( 命令写错了  ) ,事务中的所有命令都不会被执行 !

```bash
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379(TX)> set k1 v1
QUEUED
127.0.0.1:6379(TX)> set k2 v2
QUEUED
127.0.0.1:6379(TX)> set k3 v3 
QUEUED
# 错误的命令
127.0.0.1:6379(TX)> getset k3
(error) ERR wrong number of arguments for 'getset' command
127.0.0.1:6379(TX)> set k4 v4
QUEUED

# 命令队列中 命令写错了,所有命令都没有执行
127.0.0.1:6379(TX)> exec
(error) EXECABORT Transaction discarded because of previous errors.

```





> 运行时异常 ( 语法性错误 )  ,执行命令的时候 , 其他命令可以正常执行 , 错误命令抛出异常

````bash
127.0.0.1:6379> set k1 v1 
OK
127.0.0.1:6379> MULTI
OK
# 执行失败
127.0.0.1:6379(TX)> INCR k1  
QUEUED
127.0.0.1:6379(TX)> set k2 v2 
QUEUED
127.0.0.1:6379(TX)> set k3 v3 
QUEUED
127.0.0.1:6379(TX)> get k3
QUEUED
127.0.0.1:6379(TX)> EXEC
# 第一条命令执行失败
1) (error) ERR value is not an integer or out of range
# 其他命令执行成功
2) OK
3) OK
4) "v3"
127.0.0.1:6379> get k2
"v2"

````

# Redis实现乐观锁

> 监控 Watch

### 		

( 数据库 使用version 字段 , redis使用 watch 监视 值 )

### 乐观锁

​				正常情况

```bash
127.0.0.1:6379> set money 100 
OK
127.0.0.1:6379> set out 0
OK
# watch 监视 ,此时 money 值为 100
127.0.0.1:6379> watch money 
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> decrby money 20
QUEUED
127.0.0.1:6379(TX)> incrby out 20 
QUEUED
127.0.0.1:6379(TX)> exec
1) (integer) 80
2) (integer) 20

```

其他线程修改了当前watch 的key

```bash
# watch 当前key  此时money 值为100
127.0.0.1:6379> watch money
OK
127.0.0.1:6379> multi
OK
127.0.0.1:6379(TX)> DECRBY money 10
QUEUED
127.0.0.1:6379(TX)> INCRBY out 10
QUEUED
# 其他线程在执行上面命令之前修改了 money的值,这里就执行时检查到和监视的时候值不同,执行失败 
127.0.0.1:6379(TX)> exec
(nil)  # 执行失败

```

若修改失败,获取最新值

```bash
# unwatch 放弃监视
127.0.0.1:6379> unwatch
OK
# 再次监视,获取最新值
127.0.0.1:6379> watch money 
OK
127.0.0.1:6379> MULTI
OK
127.0.0.1:6379(TX)> DECRBY money 10
QUEUED
127.0.0.1:6379(TX)> INCRBY out 10
QUEUED
127.0.0.1:6379(TX)> EXEC
1) (integer) 1990
2) (integer) 40

```



# Jedis

**Jedis**是 Redis 官方推荐的 java 连接 redis开发工具

#  Springboot整合

Springboot2.x之后,原来使用的jedis 替换成了 lettuce

**jedis** :

​	采用的是直连,多个线程操作的话不安全

​	避免不安全,可以采用jedis pool 连接池

​	更像BIO 模式

​	Block IO

**lettuce** : 

​	采用netty , 实例可以在多个线程中共享,不存在线程不安全的情况

​	更像NIO模式 : 将IO模式设为 Non-Blocking模式



```java
@ConditionalOnMissingBean(name = "redisTemplate")
spring注解,当该类不存在的时候,使用默认类.这里可以自己实现一个redisTemplate类
```



springboot使用redis

1.导入依赖

```xml
<!--    操作redis spirng-boot-starter-data-redis    -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

2.增加配置文件

application.properties

```properties
# 配置redis
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.database=0
```

redis放入的对象需要序列化 implement Serializable接口

JDK序列化 查看 key 会发现有转义字符

这里使用自定义的RedisTemplate:

```java
@Configuration
public class RedisConfig {

    //编写自己的RedisTemplate , 固定模板,可直接在项目中使用
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        // 对象类型转义
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
        objectMapper.enableDefaultTyping(DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //String序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //key使用string序列化方式
        template.setKeySerializer(stringRedisSerializer);
        //hash key采用string序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value采用jackjson方式
        template.setValueSerializer(jackson2JsonRedisSerializer);
        //hash value采用Jackson方式
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
        //配置具体的序列化方式
        template.afterPropertiesSet();
        return template;
    }
}
```

在实际开发中,一般使用自己封装的RedisUtils ( 封装的对opsFor..的操作)

# Redis配置文件详解

Redis.conf 

​		配置内存大小,redis大小写不敏感,就是大小写都可以

```bash
# 1k => 1000 bytes
# 1kb => 1024 bytes
# 1m => 1000000 bytes
# 1mb => 1024*1024 bytes
# 1g => 1000000000 bytes
# 1gb => 1024*1024*1024 bytes
#
# units are case insensitive so 1GB 1Gb 1gB are all the same.
```

可以包含多个配置文件

```bash
# include /path/to/local.conf
# include /path/to/other.conf
```

网络

```bash
# bind 192.168.1.100 10.0.0.1     # listens on two specific IPv4 addresses
# bind 127.0.0.1 ::1              # listens on loopback IPv4 and IPv6
# bind * -::*                     # like the default, all 
bind 127.0.0.1 -::1  # 只能本地访问,无法通过远程连接,其他机器访问可以改为 * -::* ;如果bind选项为空的话，那会接受所有来自于可用网络接口的连接。
```

端口:

```bash
port 6379 # 默认是6379。由于Redis是单线程模型，因此单机开多个Redis进程的时候会修改端口。
```

是否保护模式

```bash
protected-mode yes # 默认开启状态,值允许本地客户端连接,拒绝外部访问.若设置了bind和密码,可以开启
```

客户端连接超时时间

```bash
timeout 0 # 单位秒,当客户端在指定时间没有发出任何指令,name关闭该连接.0表示不关闭
```

keepalive 

```bash
tcp-keepalive 300 # 单位秒,周期性的使用SO_KEEPALIVE检测客户端是否还处于健康状态,避免服务器一直阻塞.官方建议300s (5分钟),0为不会周期性的检测
```

general配置

以后台方式运行redis-server (守护进程方式运行)

```bash
daemonize yes # 默认为no
```

如果以后台方式运行redis-server,需要指定一个pid进程文件

```bash
pidfile /var/run/redis_6379.pid
```

日志

```bash
# Specify the server verbosity level.
# This can be one of:
# debug (a lot of information, useful for development/testing)
# verbose (many rarely useful info, but not a mess like the debug level)
# notice (moderately verbose, what you want in production probably) 生产环境使用这个级别日志记录
# warning (only very important / critical messages are logged)

loglevel notice # 生产环境使用这个级别日志记录
logfile ""  # 日志生成的文件,空串表示是一个标准的输出

```

redis 数据库数量

```bash
databases 16  # 默认16个库
```

是否显示logo

```bash
always-show-logo no
```

**SNAPSHOTTING (持久化用到该配置)**

RDB持久化方式

在规定的时间内,执行了多少操作,则会执行持久化

redis是内存数据库,不做持久化的话断电就没有了数据

```bash
save 3600 1   #若900秒内,至少有一个key进行了修改,则进行持久化操作
save 300 100  #若300秒内,至少有100个key进行了修改,则进行持久化操作
save 60 10000 #若60秒内,至少有1万个key进行了修改,则进行持久化操作
```

持久化出错是否继续工作

```bash
stop-writes-on-bgsave-error yes  # 默认开启
```

是否压缩rdb文件

```bash
rdbcompression yes  # 默认开启,会消耗CPU资源
```



保存rdb文件的时候,进行错误的检查校验

```bash
rdbchecksum yes
```

RBD文件名称

```bash
dbfilename dump.rdb  
```



rdb文件保存的目录

```bash
dir ./  # 默认当前目录
```

**主从复制配置**

​							REPLICATION



**安全**

​							SECURITY

设置密码

```bash
# requirepass foobared # 默认没有密码

# 通过命令行设置密码
127.0.0.1:6379> config set requirepass 123
OK
# 获取密码
127.0.0.1:6379> config get requirepass
1) "requirepass"
2) "123"
#客户端连接需要登录
127.0.0.1:6379> auth 123
OK

```

CLIENTS

​	客户端连接设置

```bash
# maxclients 10000   最大的连接客户端数量 ,默认1万
```

设置redis最大内存大小

```bash
# maxmemory <bytes>
```

内存达到上限的处理策略

```bash
# maxmemory-policy noeviction  # 默认noeviction,报错方式

# maxmemory-policy 六种方式
- valatile-lru : 只对设置了过期时间的key进行LRU (  默认此策略)
- allkeys-lru : 删除lru算法的key
- volatile-random : 随机删除即将过期的key
- allkeys-random : 随机删除
- volatile-ttl : 删除即将过期的
- noeviction : 永不过期,返回错误
```

- 

**APPEND ONLY MODE** 持久化模式

aof 配置

```bash
appendonly no  # 默认不开启aof,rdb完全够用
appendfilename "appendonly.aof"  # aof文件名称
# appendfsync always # 每次修改都会同步,消耗新能
appendfsync everysec # 每秒执行一次,可能会丢失当前这一秒的数据
# appendfsync no  # 不执行数据同步,系统自己通过数据,速度最快!

```

# Redis持久化

## RDB

RBD ,即 Redis DataBase  ,一次全量备份，记录的是数据的二进制序列化形式。在指定的时间间隔内 [ 如配置文件中配置了 sava 60 1  ]将内存中数据集体写入磁盘,就是快照 SNAPSHOT, 恢复数据时将快照文件直接读取到内存里

redis使用操作系统的多进程COW（Copy On Write）机制实现快照持久化，redis单独创建一个fork子进程进行持久化,现将数据写入一个临时文件,等持久化过程结束后,再用这个临时文件替换上次持久化好的文件. 整个过程主进程不进行任何IO操作,确保了性能.

RDB优点： 如果需要大规模数据的恢复,且对数据完整性不是非常敏感,RBD比AOF方式更高效.

RDB缺点: 最后一次持久化的数据可能丢失

​					fork子进程的时候，占用一定的内存空间

**redis默认持久化方式就是RDB**  默认文件: dump.rdb

在配置文件 SNAPSHOTTING 中进行配置

### RDB触发规则:

​	生成RDB文件

- flushall 命令,会产生一个默认的RDB文件
- save机制满足 产生一个RDB文件
- 退出redis 产生RDB文件

恢复RDB文件：

- ​	将RDB文件放到redis的启动目录即可。redis启动时自动检查dump.rdb 恢复其中的数据

有时候在生产环境会将dump.rdb文件进行备份

## AOF

AOF,即Append Only model ，连续的增量备份，记录的是内存数据修改的指令

保存的文件名为 appendonly.aof

redis默认不开启AOF

```bash
# 配置文件 aof默认关闭
appendonly no
```

若appendonly.aof文件损坏,可执行下面命令,修复appendonly.aof文件

```bash
# redis-check-aof --fix aof文件名
[root@192 bin]# redis-check-aof --fix appendonly.aof 
0x              83: Expected prefix '*', got: 'a'
AOF analyzed: size=139, ok_up_to=131, diff=8
This will shrink the AOF from 139 bytes, with 8 bytes, to 131 bytes

Continue? [y/N]: y # 是否确认修复
Successfully truncated AOF  # 修复aof文件成功

```



aof的写入规则:

- appendfsync always      有修改就写入aof文件
  appendfsync everysec   每秒写入一次
  appendfsync no              从不写入

AOF重写规则:

```bash
no-appendfsync-on-rewrite no

auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
```



# Redis发布/订阅

发布  publish    pub

订阅  subscribe   sub

pubsub模块 支持多播机制

使用场景: 网络聊天室,实时广播,实时提醒等

```bash
# 发布消息,将消息message发布到指定的频道
PUBLISH channel message

# 订阅频道,客户端订阅一个或多个频道
SUBSCRIBE channel [channel...]

# 按模式订阅，客户端按照channel得模式进行订阅，类似MQ的queue模式
PSUBSCRIBE pattern [pattern...]

# 退订一个或多个频道，与SUBSCRIBE指令配合使用
UNSUBSCRIBE channel [channel...]

# 按给定的模式退订一个或多个频道，与PSUBSCRIBE指令配合使用
PUNSUBSCRIBE pattern [pattern...]
```

```bash
# 订阅者订阅ufo频道 ,监听状态
127.0.0.1:6379> SUBSCRIBE ufo 
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "ufo"
3) (integer) 1
1) "message"

#发布者在UFO频道发布消息
127.0.0.1:6379> PUBLISH ufo see,ufo
(integer) 1

127.0.0.1:6379> SUBSCRIBE ufo 
# 订阅者 接收到消息
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "ufo"
3) (integer) 1
1) "message"  # 有消息
2) "ufo"      # 消息频道
3) "see,ufo"  # 接收到消息

# pattern subsribe 一次订阅多个频道
127.0.0.1:6379> SUBSCRIBE history art sport
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "history"
3) (integer) 1
1) "subscribe"
2) "art"
3) (integer) 2
1) "subscribe"
2) "sport"
3) (integer) 3

```



Redis的list类型也可以作为一个简单的消息队列模型  ( 不支持多播机制 )

LPush  client-1 把数据放入队列

RPop   client-2 从队列中取出数据



# Redis主从复制

## 概念

> 将一台redis服务器的数据,复制到其他redis服务器  一般配置一主多从
>
> 主节点 master / leader      写为主
>
> 从节点  slave / follower   读为主

​	数据的复制是单向的,只能 从主到从

​	默认情况下,每台Redis服务器都是主节点

## 主从复制作用

- 数据冗余 : 实现了数据的热备份 ,持久化之外的一种数据冗余方式
- 故障恢复 :  主节点出现问题,从节点提供服务. 服务冗余
- 负载均衡 : 在主从的基础上,配合读写分离,实现主节点写,从节点读, 从而分担服务器负载
- 高可用的基石 : 哨兵( Sentinel )和集群能够实施的基础

## 主从环境配置

### 	一主多从

查看当前Redis服务的信息

```bash
# info replication 查看当前Redis服务的信息
127.0.0.1:6379> info replication
# Replication
role:master  # 当前redis服务的角色
connected_slaves:0  # 当前redis服务有几个从机
master_failover_state:no-failover
master_replid:670a40250d572f33b4f76766bf614ea48a08804b
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:0
repl_backlog_size:1048576
repl_backlog_first_byte_offset:0
repl_backlog_histlen:0
```

复制若干配置文件,修改响应的配置信息

```bash
# 修改端口
port 6380

# 修改pidfile
le /var/run/redis_6380.pid

# 修改 logfile
logfile "6380.log"

# 修改dbfilename
dbfilename dump6380.rdb

```

启动redis-server,指定配置文件

```bash
[root@192 bin]# redis-server myconfig/redis79.conf  # 1
[root@192 bin]# redis-server myconfig/redis80.conf  # 2 
[root@192 bin]# redis-server myconfig/redis81.conf  # 3
[root@192 bin]# ps -ef|grep redis 				   # 查看redis进程
root       7320   2698  0 14:45 pts/0    00:00:00 redis-cli -p 6379
root       8167      1  0 15:33 ?        00:00:00 redis-server 0.0.0.0:6379
root       8180      1  0 15:33 ?        00:00:00 redis-server 0.0.0.0:6380
root       8200      1  0 15:33 ?        00:00:00 redis-server 0.0.0.0:6381
root       8212   4148  0 15:33 pts/2    00:00:00 grep --color=auto redis
```

#### 命令行方式

暂时有效

```bash
# slaveof 配置从机  后面为主机的ip + port
127.0.0.1:6381> SLAVEOF 127.0.0.1 6379
OK
127.0.0.1:6381> info replication
# Replication
role:slave                 # 当前redis角色,slave
master_host:127.0.0.1      # 主机地址
master_port:6379           # 主机端口
master_link_status:up
master_last_io_seconds_ago:6
master_sync_in_progress:0
slave_repl_offset:0
slave_priority:100
slave_read_only:1
connected_slaves:0
master_failover_state:no-failover
master_replid:95ea48ae559bc8f129295d1ba5f3d2d91100a408
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:0
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:0
```

主机信息

```bash
127.0.0.1:6379> info replication
# Replication
role:master                # 当前redis服务角色
connected_slaves:2         # 从机数量
slave0:ip=127.0.0.1,port=6381,state=online,offset=252,lag=0   #从机 ip,port,状态,偏移量
slave1:ip=127.0.0.1,port=6380,state=online,offset=252,lag=0   #从机 ip,port,状态,偏移量
master_failover_state:no-failover
master_replid:95ea48ae559bc8f129295d1ba5f3d2d91100a408
master_replid2:0000000000000000000000000000000000000000
master_repl_offset:252
second_repl_offset:-1
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:252

```

命令行选自身为master

```bash
# slaveof no one 选自身为master
127.0.0.1:6380> slaveof no one
OK
127.0.0.1:6380> info replication
# Replication
role:master				# 当前redis server角色
connected_slaves:0
master_failover_state:no-failover
master_replid:ac3956c31d5c373c5a640844e20c339cf4d7f4fa
master_replid2:95ea48ae559bc8f129295d1ba5f3d2d91100a408
master_repl_offset:3363
second_repl_offset:3364
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:197
repl_backlog_histlen:3167
```



#### 配置文件方式

```bash
# 配置主机ip 和 端口
replicaof <masterip> <masterport>
# 配置主机连接密码,如果有密码的话
masterauth <master-password>

```

#### 测试

##### 从机不可写入

```bash
127.0.0.1:6381> set address beijing  # 从机只能read,不能write
(error) READONLY You can't write against a read only replica.  # 不能对只读副本进行写入
```

##### 主机断线重连

主机断线,从机依旧保持对主机的连接状态, 当前没有写操作.主机重连后,从机依旧可以从主机获取写的数据

### 链路主从

![image-20210305161948907](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210305161948907.png)

注: redis server-2名义上是master和slave,实际还是slave ,不能写操作



### 哨兵模式

![image-20210305163613780](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210305163613780.png)

多哨兵模式原理:

​	若master服务器宕机 , 哨兵1线检测到 , 并不会马上进行failover过程.只是哨兵1认为master不可用.

​	这个称为 **主观下线**

​	当其他哨兵也检测到master不可用,且数量达到一定值时,则哨兵之间进行投票 , 进行failover故障转移 工作.

​	切换成功后,通过发布订阅模式, 让个哨兵监控的slave服务器 切换新的master

​	这个称为 **客观下线**

## 主从复制原理

> slave连接到master之后,发送一个sync同步命令
>
> master到命令,启动后台存盘进程,收集所有的修改命令
>
> master将收集的修改命令文件传送给 发送 sync同步命令的slave
>
> 完成 全量同步

全量复制 :  slave接收到master所有的修改命令文件,将其存盘并加载到slave内存

增量复制 :  master 将 新的修改命令一次传给slave

slave第一次连接master时,做全量复制





# Redis集群搭建

# 缓存穿透和缓存雪崩

## 缓存穿透

### 概念

访问不存在的key

### 解决方案

- 不存在的key从数据库中查询到的结果保存到缓存中  **Set (key,null)**
- 对参数的合法性校验
- 拉黑恶意用户IP
- 使用布隆过滤器 （可以使用Redis bitmap数据结构 ，Google的 Guava, Java 操作Redis工具 Redisson）

## 缓存击穿

#### 概念

访问某个**热点key**，热点key时间失效，造成缓存击穿

#### 解决方案

- 热点key的时间设置不过期（暴力方法）
- 分布式锁 ，在从数据库取数据这一步加入分布式锁。 从数据库查询到的数据缓存到Redis中
  - 分布式锁的实现
    - Zookeeper
    - Redis
- 



## 缓存雪崩

### 概念

​	Redis缓存的key同一时间大面积失效

### 解决方案

- 设置缓存的失效时间不要在同一时间失效
- 热点key平均分布在Redis集群的节点
- 不设置失效时间 （暴力方法）
- 定时任务不断刷新缓存的失效时间









缓存穿透

​	访问不存在的key

缓存击穿

​	热点key突然失效

缓存雪崩

​	key同一时间大面积失效

