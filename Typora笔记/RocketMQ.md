# RocketMQ

## MQ

Message Queue，消息队列。队列，是一种FIFO（first in first out）先进先出的数据结构

## 作用

1. 异步

   能提高系统的响应速度，吞吐量

2. 解耦

   1. 减少服务之间的影响。提高系统整体的稳定性和可扩展性
   2. 解耦之后可以实现数据分发。

3. 削峰

   应对突发的流量冲击

   

   

## 系统引入MQ的缺点

1. 系统复杂度提高

   如：数据链路变得复杂，消息丢失，重复调用，消息的顺序性等问题

2. 消息一致性问题

   A系统处理完业务，通过MQ发送消息给B，C系统进行后续的业务处理。若B处理成功，C处理失败，该怎么办。如何保证消息数据处理的一致性

## MQ产品对比

常用的MQ

Kafka，RabbitMQ，RocketMQ

|           | 优点                                                         | 缺点                                                         | 使用场景                 |
| --------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------ |
| Kafka     | 吞吐量非常大<br />性能非常好<br />集群高可用                 | 会丢失数据<br />功能比较单一                                 | 日志分析<br />大数据采集 |
| Rabbit MQ | 消息可靠性高<br />功能全面                                   | 吞吐量比较低<br />消息积累会响应性能<br />erlang语言不好定制 | 小规模场景               |
| Rocket MQ | 高吞吐，高性能，高可用，功能全面。开源版本是阿里捐献给Apache的开源版本，与阿里云上的商业版本有些差距 | 开源版本不如云上版<br />官方文档比较简单<br />客户端只支持Java<br />大数据生态不如Kafka | 几乎全场景               |

## 单机测试运行Rocket MQ

下载地址：https://www.apache.org/dyn/closer.cgi?path=rocketmq/4.8.0/rocketmq-all-4.8.0-bin-release.zip

```bash
查看磁盘空间
[root@192 bin]# df -hl
文件系统                 容量  已用  可用 已用% 挂载点
devtmpfs                 894M     0  894M    0% /dev
tmpfs                    910M   52K  910M    1% /dev/shm
tmpfs                    910M   11M  900M    2% /run
tmpfs                    910M     0  910M    0% /sys/fs/cgroup
/dev/mapper/centos-root   10G  9.7G  371M   97% /
/dev/sda1                197M  163M   34M   83% /boot
/dev/mapper/centos-home  497M  316M  182M   64% /home
tmpfs                    182M   32K  182M    1% /run/user/1000
/dev/sr0                 4.4G  4.4G     0  100% /run/media/viper/CentOS 7 x86_64
tmpfs                    182M     0  182M    0% /run/user/0

# 1. 解压RocketMQ压缩包
unzip rocketmq-all-4.2.0-bin-release.zip -d /usr/local/rocketmq

# 2. 修改配置文件，RocketMQ的namesrv 和 broker 的配置文件配置的空间太大，可稍微做些调整
vim runbroker.sh
vim runserver.sh 
# 修改这里
JAVA_OPT="${JAVA_OPT} -server -Xms256m -Xmx256m -Xmn128m"

# 3. 启动RockerMQ,使用后台启动方式 nohup
# 启动namesrv
nohup sh bin/mqnamesrv &
# 启动broker -c：指定启动的配置文件   -n:连接namesrv的地址
nohup sh bin/broker -c conf/broker.conf -n localhost:9876

# 4. 查看日志
tail -f ~/logs/rocketmqlogs/namesrv.log
tail -f ~/logs/rocketmqlogs/broker.log
# 查看日志还可以通过notepad++来查看，插件 -> NppFTP ,使用SFTP连接

# 5.关闭RocketMQ
sh bin/mqshutdown namesrv
sh bin/mqshutdown broker
```

![image-20210408233808689](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210408233808689.png)

## RocketMQ角色

producer：消息的发送者

consumer：消息的接收者

broker：暂存和传输消息；

Name Server：管理broker，无状态的

topic：区分消息的种类；

message queue：相当于是topic的分区；用于并行发送和接收消息



## 集群各个节点特点

- Name server：几乎是无状态节点。可集群部署，节点之间无任何信息同步
- Broker部署相对复杂。分主从节点。一个Master对应多个Slave，一个Slave对应一个Master。Master与Slave的对应关系通过指定相同的Broker Name，不同的BrokerId来定义。id为0表示Master，非0表示Slave。Master也可以部署多个，每个Broker与Name server集群中的所有节点建立长连接，定时注册Topic信息到所有NameServer 
- Producer与Name Server集群中的一个节点（随机选择）建立长连接，定期从Name Server取Topic路由信息，并向提供Topic服务的Master建立长连接，且定时向Master发送心跳。Producer完全无状态，可集群部署
- Consumer与Name Server集群中的其中一个节点（随机选择）建立长连接，定期从Name Server取Topic路由信息，并向提供Topic服务的Master，Slave建立长连接，且定时向Master，Slave发送心跳。Consumer既可以从Master订阅消息，也可以从Slave订阅，订阅规则由Broker配置决定

## Rocket MQ集群模式

### 单Master模式

这个方式风险较大，一旦broker重启或者宕机，会导致整个服务不可用。不建议线上环境使用这个模式

### 多Master模式

一个集群没有Slave，全部是Master

优点：配置简单。单个Master宕机或者重启对应用没有影响。消息丢失几率小

缺点：单台机器宕机期间，这台机器上未被消费的消息在机器恢复之前不可订阅，消息实时性收到影响

### 多Master多Slave模式（异步）

每个Master配置一个Slave，有多对Master，Slave，HA采用异步复制方式，主备有短暂消息延迟（毫秒级）

优点：即使磁盘损坏，消息丢失的非常少，且消息的实时性不会受影响。同时Master宕机后，消费者仍然可以从Slave消费。

缺点：Master宕机，磁盘损坏情况下会丢失少量消息

### 多Master多Slave模式（同步）

每个Master配置一个Slave，有多对Master，Slave，HA采用同步双写方式，即只有主备都写入成功，才向应用返回成功

优点：数据与服务都没有单点故障，Master宕机情况下，消息无延迟，服务和数据的可用性都非常高

缺点：性能比异步复制模式略低（打约10%左右），发送单个消息的RT会略高，且目前版本在主节点宕机后，备机不能自动切换为主机

**同步一致性高，异步响应快**



## 双主双从集群搭建

消息高可用采用2m-2s（同步双写）方式

![](C:\Users\alienware\Pictures\微信截图_20210409211927.png)

### 集群工作流程

1. 启动Name Server，启动后监听端口，等待Broker，Producer，Consumer练上来，相当于一个路由中心。
2. Broker启动，跟所有的Name Server保持长连接，定时发送心跳包。心跳包中包含当前Broker信息（IP+端口）以及当前Broker中所有Topic信息。注册成功后，Name Server中就有Topic跟Broker的映射关系
3. 收发消息前，需要先创建Topic，指定该Topic要存储在哪些Broker上，也可以在发送消息时自动创建Topic
4. Producer发送消息，启动时先跟Name Server集群中的其中一台建立长连接，并从Name Server中获取当前发送的Topic存在哪些Broker上，轮询从队列列表中选择一个队列，然后于队列所在的Broker建立长连接从而向Broker发送消息
5. Consumer跟Producer类似，跟其中一台Name Server建立长连接，获取当前订阅的Topic存在于哪些Broker上，然后直接跟Broker建立连接通道，开始消费消息





## 消息发送示例

创建Maven项目，导入rocketmq-client依赖

```pom
<!--  rocketmq依赖包  -->
        <dependency>
            <groupId>org.apache.rocketmq</groupId>
            <artifactId>rocketmq-client</artifactId>
            <version>4.4.0</version>
        </dependency>
```

消息发送者步骤分析：

1. 创建消息生产者producer，并指定生产者组名
2. 指定Name Server地址
3. 启动producer
4. 创建消息对象，指定Topic，Tag，和消息体
5. 发送消息
6. 关闭Producer



消息消费者步骤分析：

1. 创建Consumer对象，指定消费者组名
2. 指定Name server地址
3. 订阅Topic和Tag
4. 设置回调函数，处理消息
5. 启动Consumer

### 发送同步消息

这种可靠性同步的发送方式使用的比较广泛，比如：重要的消息通知，短信通知



```java
public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        // 1. 创建producer，指定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");

        // 2. 指定Name Server地址
        producer.setNamesrvAddr("localhost:9876");

        // 3. 启动Producer
        producer.start();
        // 4. 创建消息对象，指定Topic，Tag，消息体
        for (int i = 0; i < 10; i++) {
            // topic,tag,内容
            Message message = new Message("base","Tag1",("hello world"+i).getBytes());
            // 5. 发送消息
            SendResult result = producer.send(message);
            //发送结果
            SendStatus status = result.getSendStatus();//消息发送状态
            String msgId = result.getMsgId();//消息id
            int queueId = result.getMessageQueue().getQueueId();//队列id
            System.out.println("发送状态：" + result+",消息id:"+msgId+",队列："+queueId);
            TimeUnit.SECONDS.sleep(1);//发送完消息线程sleep1秒再发送下一条消息
        }
        //6. 关闭Producer
        producer.shutdown();
    }
```



### 发送异步消息

```java 
public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("group2");
        producer.setNamesrvAddr("localhost:9876");
        producer.start();

        for (int i = 0; i < 10; i++) {
            Message msg = new Message("async_mq", "Tag2", ("async" + i).getBytes());
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("消息发送成功" + sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("消息发送失败" + e);
                }
            });
            TimeUnit.SECONDS.sleep(2);
        }
        Thread.sleep(600000);
        producer.shutdown();
    }
```

### 发送单向消息

```java 
public static void main(String[] args) throws RemotingException, MQClientException, InterruptedException {
        DefaultMQProducer producer = new DefaultMQProducer("group3");
        producer.setNamesrvAddr("localhost:9876");
        for (int i = 0; i < 10; i++) {
            Message msg = new Message("one way topic", "Tag2", ("oneway" + i).getBytes());
            producer.sendOneway(msg); //发送单向消息
            TimeUnit.SECONDS.sleep(3);
        }
        producer.shutdown();
    }
```

