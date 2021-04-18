# Zookeeper

## 概念

ZooKeeper 是 Apache 软件基金会的一个软件项目，Zookeeper 最早起源于雅虎研究院的一个研究小组。

是一个**分布式应用协调服务**,服务治理的作用

## 作用

可以作为

- 注册中心  （常用的场景）
- 配置中心
- 数据的发布/订阅
- 负载均衡
- 命名服务
- 集群管理
- Master选取
- 分布式锁
- 分布式队列

在Dubbo框架中，Zookeeper就是担任了注册中心的角色

![image-20210330200037888](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210330200037888.png)

## Zookeeper中的角色

- leader           主节点        由有所节点投票产生  无主 --> 有主
- follower       跟随节点        有选举权和投票权
- observer       观察节点    只对client提供查询和访问
- client             dubbo，java代码...   发送查询，修改请求
- Proposal      提议
  - 每个提议会被封装成一个对象Znode Change
  - 客户端的提议被封装成一个节点挂载到Zookeeper维护的目录树上面
  - 可以对数据进行访问，数据量不饿能超过1M
  - 提议编号PID  按照数字序列递增，不会减少，不会重复

## Zab协议

Zookeeper Atomic Broadcast 原子广播协议

Leader选举算法采用的是Zab协议

Zab的核心思想是：当多数Server写入成功，则任务数据写入成功

ZK能保证数据一致性主要依赖于Zab协议的**消息广播，崩溃恢复，数据同步**三个过程。

### 消息广播

#### 2PC协议

2PC就是两阶段提交

1. 一个事务请求进来之后，leader节点会将请求包装成提议Proposal事务，并添加一个全局唯一的64位递增事务id--zxid
2. leader节点向集群其他节点广播proposal事务，节点之间的通信通过FIFO的消息队列（history queue）。leader为每个follower节点分配一个单独的FIFO队列
3. follower会把收到的proposal事务持久化磁盘。完成之后发送一个ACK给leader
4. leader接收到集群超过半数的ACK后，会提交leader本机上的事务
5. leader广播commit，follower节点收到commit后，完成各自的事务提交

消息广播模式，无法处理因leader在发起事务请求后节点宕机带来的数据不一致问题。因此ZAB协议引入了**奔溃恢复**机制

### 崩溃恢复

当整个集群在启动时，或者leader失联后，ZAB协议就会进入恢复模式

1. 集群通过**选举机制**产生新的leader，纪元号 +1
2. 其他节点从新leader同步状态
3. 过半的节点完成同步，退出恢复模式。进入消息广播模式

#### 选举机制

##### 节点的状态

| 状态      | 说明                                                         |
| --------- | ------------------------------------------------------------ |
| LOOKING   | 竞选状态。节点处于该状态时，该节点任务当前集群没有leader     |
| FOLLOWING | 跟随着状态。表示当前节点角色是follower。职责：从leader同步数据，参与选举 |
| LEADING   | 领导者状态。表示当前节点是leader                             |
| OBSERVING | 观察者状态。职责：提供查询和访问，不参与选举和投票           |

##### 选举过程

Leader选举是集群正常运行的前提，当集群启动或Leader失联后，就会进入Leader选举流程。

1. 所有节点进入LOOKING状态
2. 每个节点广播携带自身ID和ZXID的选票，投票推举自己为Leader
3. 节点接收其他节点发送的选票，把选票信息和自己推举的选票进行PK（选票中ZXID大者胜出，ZXID相同，则ID大者胜出）
4. 如果外部选票获胜，则保存此选票信息，并把选票广播出去（赞成该选票）
5. 循环上述3-4步骤
6. 当有选票得到超过半数节点赞成，且该选票的所有者也赞成该选票，则选举成功，该选票所有者成为Leader
7. Leader切换为LEADING，Follower切换为FOLLOWING，Observer切换为OBSERVING状态，选举结束，进入数据同步流程。

### 数据同步

以leader数据为基础，让集群数据达到一致的状态

1. 新Leader把本地快照加载到内存，并通过日志应用快照之后的所有事务，确保Leader数据库是最新的。
2. Follower和Observer把自身的ZXID和Leader的ZXID进行比较，确定每个节点的同步策略
3. 根据同步策略，Leader把数据同步到各节点
4. 每个节点同步结束后，Leader向节点发送NEWLEADER指令
5. 同步完成的Follower节点返回ACK
6. 当Leader收到过半节点反馈的ACK时，认为同步完成

Leader向Follower节点发送UPTODATE指令，通知集群同步完成，开始对外服务。

## 重要概念

- ZK本身就是一个分布式程序（半数以上节点存活，ZK就能正常服务）
- 集群方式部署ZK来保证其高可用
- 数据保存在内存。保证了高吞吐，低延迟。
  - 内存限制了最大的存储量，也是Znode中存储的数据量较小的进一步原因
- ZK是高性能的。"写操作"会使节点处于同步状态。所以ZK更适用于"读多写少"的场景
- ZK有临时，持久节点的概念。当创建临时节点的client会话一直保持活动，则临时节点一直存在；反之则被删除。持久节点：一旦Znode被创建，就一直存在与ZK，除非主动进行删除操作。
- ZK底层其实就两个功能：
  - 管理数据
  - 监听节点 Watcher

## 一些ZK术语

### Session

ZK server与client的会话。client与server之间的一个TCP长连接。通过这个长连接，client可以：

- 通过心跳检测，与server保持有效的会话。
- 向ZK server发送请求，接收响应
- 接收ZK server的Watcher事件通知

### sessionTimeout

若client 与server端断开连接（包括主动，故障等因素），在sessionTimeout时间内，能重新连接到集群中的任意一台server，那么之前的session依然有效

### sessionID

- server在创建session之前，会先给client分配sessionID
- sessionID是session的重要标志。与会话相关的运行机制都与sessionID相关
- sessionID全局唯一

### Znode

（节点）ZK中的Znode分为两类

- 构成ZK集群的机器---> 机器节点

- 数据模型中的数据单元 ---> 数据节点,Znode，K/V形式存储，每个Znode默认存储大小1MB（兆）

- Znode中由数据内容 和 节点属性信息

- ZK中的数据模型是Znode Tree，由"/"进行分割的路径（类似于Unix的文件系统）

- 节点序列化：可以给节点添加一个特殊属性：sequential（顺序）

  - 节点被创建时，在节点名后追加一个由父节点维护的自增整数。记录每个子节点的先后顺序

  

### 脑裂

ZK集群因网络震荡导致的多主多从问题。ZK集群多机房部署也可能会产生"脑裂"

#### 防止脑裂方式

1. 法定人数方式

   集群节点数量为奇数台

2. 冗余通信方式

   - 集群中采用多种通信方式，防止一种通信方式失效导致集群中的节点无法通信。

3. 共享资源方式

   看到共享资源就表示在集群中，能够获得共享资源的锁的就是Leader，看不到共享资源的，就不在集群中。

4. 仲裁机制方式

5. 启动磁盘锁定方式

奇数台node  + leader选举机制（过半机制） + 心跳检测 

脑裂 带来的 数据冲突问题

### 假死

### stat

版本

ZK为每个Znode维护一个stat信息

stat信息中包含

- version     当前Znode版本
- cversion    当前Znode子节点版本
- aversion   当前Znode的ACL版本（Access Control List 访问控制表）

### ACL权限控制

ZK的数据模型类似于Unix的文件系统

ZK的ACL （Access Control List）可以对Znode（相当于文件）做权限控制

ACL控制的范围：

1. 权限模式   scheme         ： 授权的策略

   ![image-20210330231512804](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210330231512804.png)

2. 授权的对象   id                  ： 授权的对象

   给谁授予权限

   授权对象id：权限赋予的实体，如：IP地址 或者 用户

3. 授予的权限            permission   ： 授予的权限

   简写：cdrwa

   delete是对子节点删除的权限。其他四种权限是指针对当前节点的操作权限

![image-20210330232229085](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210330232229085.png)

```bash
# setAcl：授权指令， /test：授权对象， ip:授权模式-IP模式，cdrwa：授予的权限
setAcl /test ip:192.168.1.101:cdrwa
```

![image-20210330232901798](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210330232901798.png)



### Watcher

事件监听器，ZK中实现**分布式协调服务**的重要特征

使用Watcher机制来实现发布/订阅功能，监听节点的变化（节点的删除，内容的变化，子节点状态的变化）

也可以看作分布式下的观察者模式

状态的监听

- KeeperState 通知状态  6种通知状态  server与client之间的连接状态的通知
  - ![image-20210330220608760](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210330220608760.png)
- EventType事件类型  4种事件类型  Znode节点状态
  - ![image-20210330220626046](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210330220626046.png)

#### Watcher特性

- 一次性
  - 无论是客户端还是服务端，watcher一旦被触发，那么它就会被zk删除，再次使用需要重新注册
- 顺序性
  - watcher会带哦是顺序穿行化执行的。只有回调后客户端才能看到最新的数据状态。一个watcher的回调逻辑不应耗时太长，以免影响其他watcher执行
- 轻量
  - WatcherEvent是最小的通信单元，只包含通知状态，事件类型，节点路径这些信息。并不包括节点变化的内容
- 时效性
  - watcher只在当前session彻底失效时才会无效。反之，则watcher一直存在，仍可以接收server端通知

#### 建立Watcher监听

三种方式建立某个Znode节点的监听

1. zk.getChildren(path,watch);
2. zk.exists(path,watch);
3. zk.getData(path,watch,stat);

![image-20210330230616588](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210330230616588.png)



#### 全量备份 

又称Raid1

- 优点：有效的缓存了IO问题
- 缺点：不利于数据的扩充

#### 对数据切片shard    

又称Raid0

- 优点
  - 有效的解决了IO的问题
  - 可以更多的区存放数据，容量可以扩充
- 缺点
  - 当一个节点异常关闭相应的数据会失效

#### Raid5

是raid0 和raid1的这种方案

将多个容量较小,廉价的磁盘进行组合,从而以较低的成本获得与昂贵大容量磁盘相当的容量,性能,可靠性

数据会进行奇偶校验,若损坏一块磁盘,当磁盘重新挂载后,会自动恢复丢失的数据

可用磁盘空间 = 磁盘数 - 1

冗余:只允许一块磁盘损坏

![image-20210329165115434](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210329165115434.png)

### 数据一致性

- 强一致性

  - 所有的读写操作都按照全局时钟下的顺序执行.且任何时刻线程读取到的缓存数据都是一样的

  - 若更新一次数据,所有的存储节点都要更新数据

  - 且需要所有节点都完成更新后才能进行写操作

  - 写入时当前节点不能被读取

    

- 弱一致性

  - 不能保证读写的一致性.但是能保证最终可以读到写入的数据

- 顺序一致性

  - 多个线程的整体执行可以是无序的.但对于单个线程来说执行是有序的.保证任何一次读操作的数据都是最后一次的写入数据

- 最终一致性 [Zookeeper就是最终一致性]

  - 从客户端看，多并发访问时获取最新数据
    - 有可能暂时获取的不是最新数据，但最终还是能访问到最新的
  - 冲服务端看，更新如何复制分布到整个系统，以确保最终一致
  - 超过半数的节点数据更新完成   
    - 抽屉理论
      - 集群认为半数以上的机器存储成功就算成功
      - 所以，集群的节点数量尽量位奇数

## ZK缺点

1. 非高可用

   极端情况下ZK会丢弃一些请求。如机房之间通信故障，对网络抖动非常敏感

2. 选举过程很慢，且期间无法对外提供服务

3. 性能有限。TPS大概是1W多。无法覆盖系统内部每天高频的调用。因此每次请求都去zookeeper获取业务系统master信息是不可能的。因此zookeeper的client必须自己缓存业务系统的master地址。

4. ZK本身的权限控制非常薄弱

5. **羊群效应**

   所有的客户端都尝试对一个临时节点去加锁，当一个锁被占有的时候，其他的客户端都会监听这个临时节点。一旦锁被释放，Zookeeper反向通知添加监听的客户端，然后大量的客户端都尝试去对同一个临时节点创建锁，最后也只有一个客户端能获得锁，但是大量的请求造成了很大的网络开销，加重了网络的负载，影响Zookeeper的性能.

   **解决方法**：

   ​	获取锁时创建一个临时顺序节点，顺序最小的那个才能获取到锁，之后尝试加锁的客户端就监听自己的上一个顺序节点，当上一个顺序节点释放锁之后，自己尝试加锁，其余的客户端都对上一个临时顺序节点监听，不会一窝蜂的去尝试给同一个节点加锁导致羊群效应。

6. ZK读取的数据可能不是最新的数据。

   ZK集群在写入数据的时候采用的是（抽屉理论）超过半数节点更新成功就算成功。若client访问的是其他没有更新数据节点，就是读到了旧数据。

## CAP原则

**C**onsistency       一致性:每次读取都会得到最新数据

**A**vailability       可用性：每个请求都会收到不是错误的响应

Partition			分区容错性: 节点之间通信故障的情况下，整个系统仍是可用的

三者不能同时满足，只能同时满足两个

多节点下  CA  CP 两种选择

CA的话，没有分区 =  单机应用







## Zookeeper一致性算法

### Paxos

Paxos是业界公认能解决**分布式系统一致性问题**算法之一.

Paxos于1990年由Lamport提出，但直到1998才正式被计算机科学界接受

多个节点会存在节点间通信的问题

通信模型

1. 共享内存 Shared memory
2. 消息传递 Messages passing

paxos 使用的是 消息传递 通信模型

#### 有主无主模型

##### 有主

只能有一个发送指令的节点

优点

​	

缺点

​	可能会出现单点故障

​	解决：重新选举   或者 切换到备用节点

存在多个主节点就会出现**脑裂**

##### 无主

每个节点都能发送指令

- 投票结果可能会导致分区
- 事务编号可能会混乱，每个节点都可以有自己的提议
- 集群可用性: 可用节点 > 总结点数一半  1/2 +1



写数据过程

1. 先将数据写入当前server
2. 发送消息给leader，leader将命令发送给其他follower
3. 其他follower收到命令修改数据，返回leader修改完成的消息
4. leader统计超过半数的follower修改成功，就认为修改成功
5. leader将信息发送给zkServer，zkServer将信息返回给client             

。。