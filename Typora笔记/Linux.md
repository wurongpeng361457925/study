# Linux :penguin:

## linux简介：

​	Linux 内核最初只是由芬兰人**林纳斯·托瓦兹（Linus Torvalds）**在赫尔辛基大学上学时出于个人爱好而编写的

Linux 是一套免费使用和自由传播的**类 Unix 操作系统**，是一个基于 POSIX（可移植操作系统接口 Portable Operating System Interface） 和 UNIX 的**多用户、多任务、多线程和多 CPU** 的操作系统。

目前市面上较知名的发行版有：Ubuntu、**RedHat**、**CentOS**、Debian、Fedora、SuSE、OpenSUSE、Arch Linux、SolusOS 等。

## Unix简介：

UNIX 操作系统由 肯·汤普森（Ken Thompson）和丹尼斯·里奇（Dernis Ritchie）发明,它的部分技术来源可追溯到从 **1965 年开始的 Multics 工程计划**，该计划由贝尔实验室、美国麻省理工学院和通用电气公司联合发起，目标是开发一种交互式的、具有多道程序处理能力的**分时操作系统**，以取代当时广泛使用的批处理操作系统。

说明：**分时操作系统**使一台计算机可以同时**为多个用户服务**，连接计算机的终端用户交互式发出命令，操作系统采用**时间片轮转**的方式处理用户的服务请求并在终端上显示结果（操作系统将CPU的时间划分成若干个片段，称为**时间片**）。操作系统以时间片为单位，**轮流为每个终端用户服务，每次服务一个时间片**。

*GNU*'s Not Unix，GNU的创始人，[理查德·马修·斯托曼](https://baike.baidu.com/item/理查德·马修·斯托曼)，是一个自由的[操作系统](https://baike.baidu.com/item/操作系统)，其内容软件完全以[GPL](https://baike.baidu.com/item/GPL)方式发布。

GNU的内核，称为[Hurd](https://baike.baidu.com/item/Hurd)，是[自由软件基金会](https://baike.baidu.com/item/自由软件基金会)发展的重点，但是其发展尚未成熟。在实际使用上，多半使用[Linux内核](https://baike.baidu.com/item/Linux内核)、[FreeBSD](https://baike.baidu.com/item/FreeBSD)等替代方案，作为系统核心，其中主要的操作系统是Linux的发行版。[Linux](https://baike.baidu.com/item/Linux)操作系统包涵了[Linux内核](https://baike.baidu.com/item/Linux内核)与其他自由软件项目中的GNU组件和软件，可以被称为GNU/Linux

![d](C:\Users\alienware\Pictures\Saved Pictures\linux和unix关系.png)

## **应用领域**

今天各种场合都有使用各种 Linux 发行版，从嵌入式设备到超级计算机，并且在服务器领域确定了地位

通常服务器使用 **LAMP**（Linux + Apache + MySQL + PHP）

或 **LNMP**（Linux + Nginx+ MySQL + PHP）组合。

# **Linux 系统启动过程**

## 分为5个阶段：

- 内核的引导。
- 运行 init。
- 系统初始化。
- 建立终端 。
- 用户登录系统。

init程序的类型：

- **SysV:** init, CentOS 5之前, 配置文件： /etc/inittab。
- **Upstart:** init,CentOS 6, 配置文件： /etc/inittab, /etc/init/*.conf。
- **Systemd：** systemd, CentOS 7,配置文件： /usr/lib/systemd/system、 /etc/systemd/system。

# **VMWare虚拟机**

​	**VMWare 网络连接三种模式**：

​		1.桥接模式：虚拟机系统可以和外部通信，但是容易造成ip冲突

​		2.NAT模式：网络地址转换模式，虚拟系统可以和外部通信，不会造成IP冲突。但外部不容易访问虚拟机系统

​		3.主机模式：独立的系统



## 桥接网络模式设置



### 1.虚拟网络设置

![image-20210121163604497](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210121163604497.png)



### 2.**根据物理主机的ip地址设置虚拟机ip**

vim /etc/sysconfig/network-scripts/ifcfg-eth0
DEVICE=eth0        										#虚拟机网卡名称。
TYPE=Ethernet
ONBOOT=yes　　     						 		  #开机启用网络配置。
NM_CONTROLLED=yes
BOOTPROTO=static      								#static，静态ip，而不是dhcp，自动获取ip地址。



IPADDR=192.168.1.101　　							  #设置我想用的静态ip地址，要和物理主机在同一网段，但又不能相同。
NETMASK=255.255.255.0  							#子网掩码，和物理主机一样就可以了。
GATEWAY=192.168.1.1   									#和物理主机一样
DNS1=8.8.8.8　　　　　　							  #DNS，写谷歌的地址就可以了。



### 3.重启网络服务

命令：service network restart



### 4.添加网关地址

在网络配置中 /etc/sysconfig/network 中 添加网关地址

命令：vi /etc/sysconfig/network

添加：GATEWAY=主机网关地址(192.168.1.1)



5.虚拟机和主机互ping

# **安装vmtools**    共享文件夹

![](C:\Users\alienware\Pictures\Saved Pictures\Linux安装VMtools.png)

2.复制到/opt目录中

![](C:\Users\alienware\Pictures\Saved Pictures\linux安装vmtools2.png)

3.解压

tar -zxvf VMwareTools-10.3.32-15902021.tar.gz

4.进入到解压目录 执行 ./vmware-install.pl

5.安装成功，/mnt下会有个/hgfs目录

在主机创建文件夹

![](C:\Users\alienware\Pictures\Saved Pictures\vmware共享文件夹.png)

共享文件夹路径  ：/mnt/hgfs/文件夹名称

![image-20210120014318528](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210120014318528.png)





## llinux安装软件



### 三种安装包

　　　　 tar包，它是使用UNIX系统的打包工具tar打包的。
　　　　 rpm包，它是Redhat Linux提供的一种包封装格式。
　　　　 dpkg包，它是Debain Linux提供的一种包封装格式。



### 包的命名规律

​	名称-版本-修正版-类型

​	e.g.

　　　　software-1.2.3-1.tar.gz 意味着：
　　　　	软件名称：		software
　　　　	版本号：			1.2.3
　　　　	修正版本：		1
　　　　	类型：				tar.gz，说明是一个tar包。



### 包里内容

​	可执行文件

​	源程序



# **Linux的目录结构**

## 基本介绍：

1.Linux的文件系统采用级层式的**树状目录结构**，最上层是根目录 /，在更目录下再创建其他的目录

2.Linux中，**一切皆是文件**

## Linux目录结构树状图：

<img src="C:\Users\alienware\Pictures\Saved Pictures\linux目录结构.jpg" style="zoom:150%;" />

**目录解释：**

**/bin** 			存放Linux最经常使用的命令

/boot			启动Linux时的一些核心文件，包括一些连接文件和镜像文件

/dev			是device（设备）的缩写，存放的是Linux的外部设备，如cpu，disk等

**/etc**			Etcetera(等等) ，存放系统管理所需要的配置文件和子目录，安装一些应用的配置文件也放在该目录下，如mysql

/home		用户主目录，每个用户都有一个对应的目录

/lib			library（库）存放这系统最基本的动态连接共享库，类似Windows中的DLL文件。几乎所有应用程序都需要用到这些共享库

/lost+found     当系统非正常关机时，这里存放了一些文件

/media         linux把识别到的U盘，光驱等设备作为文件放到media目录

/mnt		    临时挂载别的文件系统。可以将光驱挂在到/mnt/上，进入该目录就可以看到光驱中的内容了 

/opt 			optional(可选)  安装额外软件的目录。例如oracle，mysql等一些的安装文件

/usr/local    软件的安装目录。一般是通过编译源码方式安装的程序

/proc          processes(进程)的缩写，  proc目录是一种虚拟文件系统（伪文件系统），存储的是当前内核运行状态的一系列特殊文件，这是个虚拟的目录，是系统内存的映射，可以访问这个目录来获取系统信息。这个目录的内容在内存里

/root			超级用户的目录

**/sbin**			super user缩写，超级用户使用的系统管理程序

/selinux		Redhat/CentOS所特有的目录，selinux是个安全机制，类似Windows的防火墙。存放selinux相关的文件

/usr     Unix Shared Resources(共享资源 )缩写，重要目录，用户的很多应用程序和文件都放在这个usr中，类似Windows下的program files

**/usr/bin**    系统用户使用的应用程序，普通用户使用的指令在这个目录

**/usr/sbin**   超级用户使用的比较高级的管理程序和系统守护程序，超级用户使用的指令在这个目录中

/usr/src    内核源码默认的放置目录

**/var**			variable（变量）的缩写，存放着不断扩充的东西，习惯将经常被修改的目录放到该目录，例如日志文件，mail等

/run         临时文件系统，用来存放系统穷依赖的信息。系统重启时，这个目录下的文件应该被删掉或清除。若系统中有/var/run目录，应该让它指向run

------



在Linux文件系统中有两个特殊的目录，

1.用户所在的工作目录，也叫当前目录              一个点 **.** 来表示；

2.当前目录的上一级目录，也叫父目录，可以使用两个点 **..** 来表示。

-----

## 远程登陆linux

文件上传和下载

xftp,fileZilla等工具

![](C:\Users\alienware\Pictures\Saved Pictures\xftp登陆.png)

![](C:\Users\alienware\Pictures\Saved Pictures\xft登陆密钥.png)

登陆远程Linux需要输入远程Linux的用户名和密码

![](C:\Users\alienware\Pictures\Saved Pictures\xftp登陆远程Linux.png)



-----

## **vi和vim的使用**

linux内置vi文本编辑器

vim:具有程序编辑的能力，vi的增强版。

**vi和vim常用的三种模式：**

**1.正常模式**

​		以vim打开一个文档就直接进入的一般模式

**2.插入模式**

​		按 i,I,o,O,a,A,r,R等按键进入编辑模式，一般按i键

**3.命令行模式**

​		先ESC,再输入冒号":"  ，提供相关指令，完成读取，存盘，替换，离开vim，显示行号等

:wq 保存退出    或者shift + zz

:q    退出 （修改过的话该命令不能用）

:q!  强制退出且不保存

### **vi和vim一些快捷键：**

**正常模式下**

拷贝当前行yy    拷贝当前行下面5行5yy      粘贴:p

删除当前行：dd    删除当前行下面5行：5dd

删除当前光标所在的字符 x

移动到光标行的行首/行尾  home(或者0)/end

向下移动一页/半页  ctrl + f     ctrl + d

向上移动一页/半页 ctrl + b     ctrl + u

光标移动到非空格的下一行 /上一行    +/-

光标移动到当前屏幕最上方一行的第一个字符/屏幕中间行 /最下一行/文档最后一行 /文档第一行 shift + h / shift + m / shift + l  / shift + g  / gg

向下寻找      	 /内容       继续找/反向  n/N

向上寻找           ?内容        继续找/反向  n/N





设置当前的行号/取消行号： :set nu         set  nonu

移动到指定行       n + shift + g  ，n代表行号

<img src="C:\Users\alienware\Pictures\Saved Pictures\vim键盘图.gif" style="zoom: 200%;" />



# Linux 关机命令

正确的关机流程为：sync > shutdown > reboot > halt    h代表halt

- -t seconds : 设定在几秒钟之后进行关机程序。
- -k : 并不会真的关机，只是将警告讯息传送给所有使用者。
- -r : 关机后重新开机。
- -h : 关机后停机。
- -n : 不采用正常程序来关机，用强迫的方式杀掉所有执行中的程序后自行关机。
- -c : 取消目前已经进行中的关机动作。
- -f : 关机时，不做 fcsk 动作(检查 Linux 档系统)。
- -F : 关机时，强迫进行 fsck 动作。
- time : 设定关机的时间。
- message : 传送给所有使用者的警告讯息。

```
sync 将数据由内存同步到硬盘中。
```

```
shutdown 关机指令，你可以man shutdown 来看一下帮助文档。例如你可以运行如下命令关机：
```

```
shutdown –h now 立马关机
```

```
shutdown –h 20:25 系统会在今天20:25关机
```

```
shutdown –h +10 十分钟后关机
```

```
shutdown –r now 系统立马重启
```

```
shutdown –r +10 系统十分钟后重启
```

```
reboot 就是重启，等同于 shutdown –r now
```

```
halt 关闭系统，等同于shutdown –h now 和 poweroff
```

--------

## Linux用户

切换用户                             su 用户名

退出当前用户					 exit/logout

查看当前登陆用户/查看第一个登陆用户  whoami / who am i

查询用户    						id 用户名

增加用户/删除    		useradd 用户名     userdel 用户名

### **用户组**

新增组  		groupadd  组名

删除组			groupdel  组名

增加用户时直接加组     useradd -g 用户组 用户名

修改用户组             usermod -g 用户组 用户名

# Linux文件

Linux是多用户系统,为了安全性,不同的用户拥有不同的文件操作权限

通常使用两个名来修改 文件,目录的所属用户与权限

- chown ( change ownerp ) 修改用户与组
- chmod ( change mode ) 修改用户的权限

可以用 ll 或 ls -l 显示文件的属性及文件所属的用户和组

```bash
-rw-r--r--. 1 root root     131 3月   5 19:16 appendonly.aof  (1)
-rw-r--r--. 1 root root      92 3月   4 15:15 dump.rdb        (2)
drwxr-xr-x. 2 root root      47 3月   5 19:16 myconfig        (3)
-rwxr-xr-x. 1 root root 4833392 3月   1 06:01 redis-benchmark (4)
```

第一个字符表示:

- d 表示为目录
- -表示为文件
- l 表示连接文档 ( link file )
- b 表示可供存储的接口设备
-  c 表示 穿行端口设备,如键盘,鼠标

接下来的字符,三个一组,为rwx三个参数的组合. 没有权限用 "-"号 . rwx表示的位置不变

r : 可读 read

w : 可写 write

x : 可执行 execute

![image-20210305115724164](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210305115724164.png)

对于root用户来说,文件的权限对其不起作用

## 更改文件属性

1. chgrp : 更改文件所属组

   chgrp -R 组名 文件名 -R:递归更改文件属组,文件里的所有文件都改为指定组

2. chown: 更改文件属主 , 也可以同时更改文件属组

   chown -R 属组名 文件名

   chown -R 属主名:属组名 文件名

3.chmod: 更改文件的9个属性

linux文件属性有两种设置方法

- ​	数字方式
- ​    符号方式

linux文件,三种身份

- ower 拥有者
- group 组
- others 其他

每种身份各有三种权限:

- read 读 			 r  数字对应 : 4
- write 写            w 数字对应 : 2
- execute 执行    e  数字对应 : 1



​	说明:  

​			-R 递归变更目录的所有文件权限

​			xyz  数字表示的权限, 为rwx 对应值的和

### chmod数字方式

​															r : 4   w : 2    x : 1 

​															chmod [-R] xyz 文件或目录

```bash
# 将temp目录变更权限  数字方式
drwxr-xr-x.   3 root root    19 3月   5 12:00 temp
# chmod xyz 目录名/文件名
[root@192 usr]# chmod 777 temp

# 变更后的temp权限 ,所属用户/所属用户组/其他用户 都有可读,可写,可执行的权限
drwxrwxrwx.  3 root root  19 3月   5 12:00 .
drwxr-xr-x. 14 root root 167 3月   5 11:59 ..
drwxr-xr-x.  2 root root   6 3月   5 12:00 temp1

```

### chmod符号方式

- user ： 用户 = u
- group ： 组 = g
- others ： 其他 = o
- a 代表所有用户



​												chmod u=rwx,g=rx,o=r 文件名/目录名

```bash
# chmod 符号方式修改temp目录权限 
drwxrwxrwx.   3 root root    19 3月   5 12:00 temp
# chmod 符号方式修改temp目录权限 
[root@192 usr]# chmod u=rwx,g=rx,o=r temp
[root@192 usr]# ls -al temp
总用量 0
# 修改后的temp目录权限
drwxr-xr--.  3 root root  19 3月   5 12:00 .
drwxr-xr-x. 14 root root 167 3月   5 11:59 ..
drwxr-xr-x.  2 root root   6 3月   5 12:00 temp1

# 去掉所有用户的执行权限，不改变所有用户的读写权限
[root@192 usr]# chmod a-x temp
# 修改后
drw-r--r--.  3 root root  19 3月   5 12:00 .
drwxr-xr-x. 14 root root 167 3月   5 11:59 ..
drwxr-xr-x.  2 root root   6 3月   5 12:00 temp1
```

# 防火墙操作

```bash
# 关闭防火墙
systemctl stop firewalld.service

# 查看防火墙状态
firewall-cmd --state

# 机制firewall开机启动
systemctl disable firewalld.service

# 临时关闭防火墙
systemctl stop firewalld

```

## 关闭指定防火墙

```bash
# Rocket MQ，开放name server默认端口
firewall-cmd --remove-port=9876/tcp --permanent

# 开放master默认端口
firewall-cmd --remove-port=10911/tcp --permanent

# 重启防火墙
firewall-cmd --reload
```

# 文件和目录命令

## cd命令

**进入**

```BASH 
cd /home 进入/home目录
cd .. 返回上一级目录
cd ../.. 返回上两级目录
cd 进入个人的主目录
cd ~user1 进入user1的主目录
cd - 返回上次所在的目录
```

## pwd命令

```bash 
pwd 显示工作路径
```

## ls命令

**查看**

```bash
ls 查看目录中的文件
ls -l 显示文件和目录的详细资料
ls -a 列出全部文件，包含隐藏文件
ls -R 连同子目录的内容一起列出（递归列出），该目录下的所有文件都会显示出来
ls [0-9] 显示包含数字的文件名和目录名
```

## cp命令

**复制**

```bash
用户复制文件，copy的意思，还可以把多个文件一次性的复制到一个目录下
-a 将文件的特性一起复制
-p 连同文件的属性一起复制，而非使用默认的方式。与-a相似，常用于备份
-i 若目标文件已存在，覆盖时先询问
-r 递归持续复制，用于多层目录
-u 目标文件与源文件有差异时才会复制
```

## mv命令

**剪切和改名**

```bash
用于移动文件，目录或改名，move之意
-f force,强制的意思。若目标文件已经存在，不会询问，直接覆盖
-i interactive交互的意思，目标文件已经存在，会询问是否覆盖
-u update的意思，目标文件已经存在，且比目标文件新，才会覆盖
```

## rm命令

**删除**

```bash 
用于删除文件和目录，remove的意思
-f force，强制的意思。忽略不存在的文件和参数，永远不要提示
-i interactive，交互的意思。每次移除前提示
-r recursive,递归地删除目录及其内容
```

## cat命令

```bash
cat file1 从第一个字节开始正向查看文件的内容
tac file1 从最后一行开始反向查看一个文件的内容
cat -n file1 标示文件的行数
more file1 查看一个长文件的内容
head -n 2 file1 查看一个文件的前两行
tail -n 2 file1 查看一个文件的最后两行
tail -n +1000 file1  从1000行开始显示，显示1000行以后的
cat filename | head -n 3000 | tail -n +1000  显示1000行到3000行
cat filename | tail -n +3000 | head -n 1000  从第3000行开始，显示1000(即显示3000~3999行)
```

## find命令

**在指定目录下查找文件**

语法：

find   path   -option   [   -print ]   [ -exec   -ok   command ]   {} \;

**参数说明** :

find 根据下列规则判断 path 和 expression，在命令列上第一个 - ( ) , ! 之前的部份为 path，之后的是 expression。如果 path 是空字串则使用目前路径，如果 expression 是空字串则使用 -print 为预设 expression。

expression 中可使用的选项有二三十个之多，在此只介绍最常用的部份。

-mount, -xdev : 只检查和指定目录在同一个文件系统下的文件，避免列出其它文件系统中的文件

-anewer file：atime比mtime更接近现在的文件。也就是说，文件修改过之后被访问过

-cnewer file：ctime比mtime更接近现在的文件

-newer  file：比给定文件的mtime更接近现在的文件。

-newer[acm]t TIME：atime/ctime/mtime比时间戳TIME更新的文件

-amin  n：文件的atime在范围n分钟内被访问过，对这个文件运用 more、cat等命令。ls、stat命令都不会修改文件的访问时间。注意，n可以是(+ -)n，例如-amin +3表示在3分钟以前

-cmin  n：文件的ctime在范围n分钟内被修改过文件的状态，通过chmod、chown命令修改一次文件属性，这个时间就会更新

-mmin  n：文件的mtime在范围n分钟内被修改过内容，vim/vi修改保存

-atime n：文件的atime在范围24*n小时内被访问过*


-ctime n：文件的ctime在范围24*n小时内被修改状态*


-mtime n：文件的mtime在范围24*n小时内被修改内容

-used  n：最近一次ctime改变n天范围内，atime改变过的文件，即atime比ctime晚n天的文件，可以是(+ -)n

-empty : 空的文件-gid n or -group name : gid 是 n 或是 group 名称是 name

-ipath p, -path p : 路径名称符合 p 的文件，ipath 会忽略大小写

-name name, -iname name : 文件名称符合 name 的文件。iname 会忽略大小写

-size n : 文件大小 是 n 单位，b 代表 512 位元组的区块，c 表示字元数，k 表示 kilo bytes，w 是二个位元组。

-type c : 文件类型是 c 的文件。

d: 目录

c: 字型装置文件

b: 区块装置文件

p: 具名贮列

f: 一般文件

l: 符号连结

s: socket

-pid n : process id 是 n 的文件

你可以使用 ( ) 将运算式分隔，并使用下列运算。

exp1 -and exp2

! expr

-not expr

exp1 -or exp2

exp1, exp2

```bash 
find / -name file1 从 '/' 开始进入根文件系统搜索文件和目录
find / -user user1 搜索属于用户 'user1' 的文件和目录
find /usr/bin -type f -atime +100 搜索在过去100天内未被使用过的执行文件
find /usr/bin -type f -mtime -10 搜索在10天内被创建或者修改过的文件
whereis halt 显示一个二进制文件、源码或man的位置
which halt 显示一个二进制文件或可执行文件的完整路径

# 查找当前目录下，24小时内更改过的文件，并进行删除操作（删除没有提示）
 find -type f -mtime -1 -exec rm {} \;
 
# 查找当前目录下文件名以.log结尾且24小时内更改过的文件，并进行安全删除（删除前会询问） 
 find -name "*.log" -type f -mtime -1 -ok rm {} \;
 
 
# 查找当前目录下以“.log”结尾的文件或者目录，并移动到test目录下
find -name ".log" -exec mv {} test \;

# 查找当前目录下size大于30m的文件
find -type f -size 30

#删除大小为0kb 的文件
find . -size 0 -print0|xargs -0 rm 
```

## 文本处理

### grep命令

```bash
# 在文件 '/var/log/messages'中查找关键词"Aug"
grep Aug /var/log/messages  

# 在文件 '/var/log/messages'中查找以"Aug"开始的词汇
grep ^Aug /var/log/messages 

# 选择 '/var/log/messages' 文件中所有包含数字的行
grep [0-9] /var/log/messages 

# 在目录 '/var/log' 及随后的目录中搜索字符串"Aug"
grep Aug -R /var/log/* 

# 将example.txt文件中的 "string1" 替换成 "string2"
sed 's/stringa1/stringa2/g' example.txt 

# 从example.txt文件中删除所有空白行
sed '/^$/d' example.txt 
```

### paste命令

```bash
# 合并两个文件或两栏的内容
paste file1 file2 

# 合并两个文件或两栏的内容，中间用"+"区分
paste -d '+' file1 file2 
```

### sort命令

```bash
# 排序两个文件的内容
sort file1 file2 

# 取出两个文件的并集(重复的行只保留一份)
sort file1 file2 | uniq 

# 删除交集，留下其他的行
sort file1 file2 | uniq -u 

# 取出两个文件的交集(只留下同时存在于两个文件中的文件)
sort file1 file2 | uniq -d 
```

### comm命令

```bash
# 比较两个文件的内容只删除 'file1' 所包含的内容
comm -1 file1 file2 

# 比较两个文件的内容只删除 'file2' 所包含的内容
comm -2 file1 file2 

# 比较两个文件的内容只删除两个文件共有的部分
comm -3 file1 file2 
```

## 打包和压缩文件

### tar命令

英文全拼：tape archive 

tar是用来建立，还原备份文件的工具程序。可以加入，解开备份文件内的文件

tar是linux等下的打包工具，生成的包通常也用tar作为扩展名，其实tar只是负责打包，不一定有压缩，事实上可以压缩，也可以不压缩，通常你看到xxxx.tar.gz，就表示这个tar包是压缩的，并且使用的压缩算法是GNU ZIP，而xxxx.tar.bz2就表示这个包使用了bzip2算法进行压缩，当然这样的命名只是一种惯例，并非强制。简单地说，tar就仅是打包

- -c ：新建打包文件
- -t ：查看打包文件的内容含有哪些文件名
- -x ：解打包或解压缩的功能，可以搭配-C（大写）指定解压的目录，注意-c,-t,-x不能同时出现在同一条命令中
- -j ：通过bzip2的支持进行压缩/解压缩
- -z ：通过gzip的支持进行压缩/解压缩
- -v ：在压缩/解压缩过程中，将正在处理的文件名显示出来
- -f filename ：filename为要处理的文件
- -C dir ：指定压缩/解压缩的目录dir

```bash
# 打包gaga.log ,tomcat.log为test.tar包
# z:通过gzip指令处理备份文件；c:create建立新的备份文件;v:verbose显示指令执行过程；f:指定备份文件
[root@192 hehe]# tar -czvf test.tar.gz gaga.log tomcat.log 
gaga.log
tomcat.log

# 列出压缩文件内容
# t:列出备份未见的内容；z:通过gzip指令处理备份文件；v:显示指令执行过程；f:指定备份文件
[root@192 hehe]# tar -tzvf test.tar.gz 
-rw-r--r-- root/root       130 2021-04-13 21:31 gaga.log
-rw-r--r-- root/root        12 2021-04-13 20:20 tomcat.log

# 解压文件
# 在解压gz压缩方式压缩文件的时候并不需要加上-z，直接用参数-xf即可，另外两种压缩方式在解压的时候一样，因为tar命令会自动选择，解压之后压缩文件还在，如果不指定解压出来的文件保存在哪里，那么会直接解压在当前目录 
# v:显示正在处理的文件名；
# x:解压缩，提取打包的内容；
# f:使用文件名，在f后要接压缩后的文件的名字，tar命令必须使用f参数，且必须在其他参数之后，即最右边 
# -C:指定解压目录
[root@192 hehe]# tar -vxf test.tar.gz -C ../

```

## 进程相关命令

### jps命令

显示当前系统的Java进程情况，及其pid

jps：**J**ava virtual Machine **P**rocess **S**tatus Tool ，**JDK 1.5** 提供的一个显示当前所有Java进程pid的命令。

### ps命令

英文全拼：process status

将某个时间点的进程运行情况选取下来并输出，process的意思

- -A ： 所有的进程都显示出来
- -a ： 不与teminal（终端）有关的所有进程
- -u ： 有效用户的相关进程
- -x ： 列出进程较完整信息。一般与a参数一起使用
- -l ： 详细的将pid信息列出
- -e :   显示环境变量
- f ： 显示程序间的关系
- -H ：显示树状结构
- -r ：显示当前终端的进程
- -T：显示当前终端的所有程序

```bash
[root@192 hehe]# ps aux
USER        PID      %CPU       %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
# USER : 进程属于哪个用户
# PID  : 进程id
# $CPU : 进程cpu资源占用率
# $MEM : 进程物理内存占用率
# VSZ  : 进程虚拟内存量
# RSS  : 进程固定的内存量
# TTY  : 进程在哪个终端机上运作，若与终端机无关显示？，tty1-tty6为本机上面的登陆者程序；pts/0等等的，表示由网络连接进主机的程序
# STAT : 程序目前的状态
	R：run正在运行，或可被运行
	S：sleep睡眠状态，可被默写讯号signal唤醒
	T：正在侦测或者停止
	Z: 静静终止。但其父进程无法正常的终止它，造成僵尸进程zombie
# START: 进程触发启动时间
# TIME : 进程实际使用CPU运行的时间
# COMMAND: 进程的实际指令

# 显示所有命令，连带命令行
ps -ef

# 查找指定进程 ps -ef |grep 进程关键字
[viper@192 ~]$ ps -ef |grep tomcat
viper      8624      1 11 22:39 pts/0    00:00:02 /usr/local/jdk/jdk1.8/jdk1.8.0_281/bin/java -Djava.util.logging.config.file=/opt/apache-tomcat-9.0.44/conf/logging.properties -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager -Djdk.tls.ephemeralDHKeySize=2048 -Djava.protocol.handler.pkgs=org.apache.catalina.webresources -Dorg.apache.catalina.security.SecurityListener.UMASK=0027 -Dignore.endorsed.dirs= -classpath /opt/apache-tomcat-9.0.44/bin/bootstrap.jar:/opt/apache-tomcat-9.0.44/bin/tomcat-juli.jar -Dcatalina.base=/opt/apache-tomcat-9.0.44 -Dcatalina.home=/opt/apache-tomcat-9.0.44 -Djava.io.tmpdir=/opt/apache-tomcat-9.0.44/temp org.apache.catalina.startup.Bootstrap start
viper      8742   8265  0 22:40 pts/1    00:00:00 grep --color=auto tomcat

# ps -Lf 进程id 显示进程下的线程
ps -Lf 8624
UID         PID   PPID    LWP  C NLWP STIME TTY      STAT   TIME CMD

# ps aux --sort=-pcpu |head -10 按CPU使用率降序取前十
[root@192 hehe]# ps aux --sort=-pcpu |head -10
USER        PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
viper      1993  1.0 10.3 3523436 193220 ?      Rl   20:57   1:57 /usr/bin/gnome-shell
root       1337  0.5  4.7 382116 87896 tty1     Ssl+ 20:57   1:03 /usr/bin/X :0 -background none -noreset -audit 4 -verbose -auth /run/gdm/auth-for-gdm-lphAsS/database -seat seat0 -nolisten tcp vt1
viper      3248  0.2  2.2 719020 41744 ?        Rl   20:58   0:24 /usr/libexec/gnome-terminal-server
viper      8624  0.1  6.4 2986812 120612 pts/0  Sl   22:39   0:08 /usr/local/jdk/jdk1.8/jdk1.8.0_281/bin/java -Djava.util.logging.config.file=/opt/apache-tomcat-9.0.44/conf/logging.properties -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager -Djdk.tls.ephemeralDHKeySize=2048 -Djava.protocol.handler.pkgs=org.apache.catalina.webresources -Dorg.apache.catalina.security.SecurityListener.UMASK=0027 -Dignore.endorsed.dirs= -classpath /opt/apache-tomcat-9.0.44/bin/bootstrap.jar:/opt/apache-tomcat-9.0.44/bin/tomcat-juli.jar -Dcatalina.base=/opt/apache-tomcat-9.0.44 -Dcatalina.home=/opt/apache-tomcat-9.0.44 -Djava.io.tmpdir=/opt/apache-tomcat-9.0.44/temp org.apache.catalina.startup.Bootstrap start
root          1  0.0  0.3 128556  7176 ?        Ss   20:56   0:02 /usr/lib/systemd/systemd --switched-root --system --deserialize 22
root          2  0.0  0.0      0     0 ?        S    20:56   0:00 [kthreadd]
root          4  0.0  0.0      0     0 ?        S<   20:56   0:00 [kworker/0:0H]
root          6  0.0  0.0      0     0 ?        S    20:56   0:00 [ksoftirqd/0]
root          7  0.0  0.0      0     0 ?        S    20:56   0:00 [migration/0]


# ps aux --sort -rss|head -3 按内存使用率排序
[root@192 hehe]# ps aux --sort -rss|head -3
USER        PID %CPU %MEM    VSZ   RSS TTY      STAT START   TIME COMMAND
viper      1993  1.0 10.3 3523436 193228 ?      Sl   4月13   1:58 /usr/bin/gnome-shell
viper      8624  0.1  6.4 2986812 120612 pts/0  Sl   4月13   0:08 /usr/local/jdk/jdk1.8/jdk1.8.0_281/bin/java -Djava.util.logging.config.file=/opt/apache-tomcat-9.0.44/conf/logging.properties -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager -Djdk.tls.ephemeralDHKeySize=2048 -Djava.protocol.handler.pkgs=org.apache.catalina.webresources -Dorg.apache.catalina.security.SecurityListener.UMASK=0027 -Dignore.endorsed.dirs= -classpath /opt/apache-tomcat-9.0.44/bin/bootstrap.jar:/opt/apache-tomcat-9.0.44/bin/tomcat-juli.jar -Dcatalina.base=/opt/apache-tomcat-9.0.44 -Dcatalina.home=/opt/apache-tomcat-9.0.44 -Djava.io.tmpdir=/opt/apache-tomcat-9.0.44/temp org.apache.catalina.startup.Bootstrap start



```

### pstree命令

以树状结构显示进程

```bash
# -p:显示进程（线程）id，viper：用户
[viper@192 haha]$ pstree -p viper |grep java
java(8624)-+-{java}(8628)
           |-{java}(8632)
           |-{java}(8633)
           |-{java}(8634)
           |-{java}(8635)
           |-{java}(8636)
           |-{java}(8637)
           |-{java}(8638)
           |-{java}(8639)
           |-{java}(8640)
           |-{java}(8641)
           |-{java}(8642)
           |-{java}(8643)
           |-{java}(8644)
           |-{java}(8645)
           |-{java}(8646)
           |-{java}(8647)
           |-{java}(8648)
           |-{java}(8649)
           |-{java}(8650)
           |-{java}(8651)
           |-{java}(8652)
           |-{java}(8653)
           |-{java}(8654)
           |-{java}(8655)
           |-{java}(8656)
           |-{java}(8657)
           |-{java}(8658)
           `-{java}(8659)

```



### netstat ,ss命令

netstat：查看进程占用的端口号，显示网络连接、路由表、接口统计信息、伪装连接以及多播成员

ss: 一般用于**转储套接字统计信息**。它能够输出类似于 `netstat` 输出的信息，但它可以比其它工具显示更多的 TCP 信息和状态信息。

```bash

# ss
[root@192 hehe]# ss -tnlp |grep java
LISTEN     0      1       [::ffff:127.0.0.1]:8005                  [::]:*                   users:(("java",pid=8624,fd=69))
LISTEN     0      100       [::]:8080                  [::]:*                   users:(("java",pid=8624,fd=57))

# 使用端口号来检查
[root@192 hehe]# ss -tnlp |grep "8624"
LISTEN     0      1       [::ffff:127.0.0.1]:8005                  [::]:*                   users:(("java",pid=8624,fd=69))
LISTEN     0      100       [::]:8080                  [::]:*                   users:(("java",pid=8624,fd=57))

```

```bash
# netstat
[root@192 hehe]# netstat -tnlp |grep java
tcp6       0      0 127.0.0.1:8005          :::*                    LISTEN      8624/java           
tcp6       0      0 :::8080                 :::*                    LISTEN      8624/java  

# netstat 使用端口号来检查
[root@192 hehe]# netstat -tnlp |grep "8624"
tcp6       0      0 127.0.0.1:8005          :::*                    LISTEN      8624/java           
tcp6       0      0 :::8080                 :::*                    LISTEN      8624/java  
```

### 通过进程号查看端口号

```bash
netstat -nap |grep java

ss -nap |grep java

```

### 通过端口查看进程

```bash
# 通过8080端口查看相应的进程
[root@192 hehe]# netstat -nap |grep 8080
tcp6       0      0 :::8080                 :::*                    LISTEN      8624/java   
```

# date命令

```bash
# date 显示时间
root@DESKTOP-A7IOA9J:/# date                                                                                            Sun Apr 18 08:18:13 CST 2021   

# 指定时间格式 date +"%Y-%m-%d"
root@DESKTOP-A7IOA9J:/# date +"%Y-%m-%d"                                                                                2021-04-18  

# 指定时间格式，时区
root@DESKTOP-A7IOA9J:/# date +"%Y-%m-%d %k-%M-%S %Z"                                                                    2021-04-18  8-21-50 CST 

# date -s "2021/4/18 8:25"  设置系统时间
```

%n : 下一行
%t : 跳格
%H : 小时(00-23)
%I : 小时(01-12)
%k : 小时(0-23)
%l : 小时(1-12)
%M : 分钟(00-59)
%p : 显示本地 AM 或 PM
%r : 直接显示时间 (12 小时制，格式为 hh:mm:ss [AP]M)
%s : 从 1970 年 1 月 1 日 00:00:00 UTC 到目前为止的秒数
%S : 秒(00-60)
%T : 直接显示时间 (24 小时制)
%X : 相当于 %H:%M:%S
%Z : 显示时区
%a : 星期几 (Sun-Sat)
%A : 星期几 (Sunday-Saturday)
%b : 月份 (Jan-Dec)
%B : 月份 (January-December)
%c : 直接显示日期与时间
%d : 日 (01-31)
%D : 直接显示日期 (mm/dd/yy)
%h : 同 %b
%j : 一年中的第几天 (001-366)
%m : 月份 (01-12)
%U : 一年中的第几周 (00-53) (以 Sunday 为一周的第一天的情形)
%w : 一周中的第几天 (0-6)
%W : 一年中的第几周 (00-53) (以 Monday 为一周的第一天的情形)
%x : 直接显示日期 (mm/dd/yy)
%y : 年份的最后两位数字 (00.99)
%Y : 完整年份 (0000-9999)