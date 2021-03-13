# Linux :penguin:

## linux简介：

​	Linux 内核最初只是由芬兰人**林纳斯·托瓦兹（Linus Torvalds）**在赫尔辛基大学上学时出于个人爱好而编写的

Linux 是一套免费使用和自由传播的**类 Unix 操作系统**，是一个基于 POSIX 和 UNIX 的**多用户、多任务、支持多线程和多 CPU** 的操作系统。

目前市面上较知名的发行版有：Ubuntu、**RedHat**、**CentOS**、Debian、Fedora、SuSE、OpenSUSE、Arch Linux、SolusOS 等。

## Unix简介：

UNIX 操作系统由 肯·汤普森（Ken Thompson）和丹尼斯·里奇（Dernis Ritchie）发明,它的部分技术来源可追溯到从 **1965 年开始的 Multics 工程计划**，该计划由贝尔实验室、美国麻省理工学院和通用电气公司联合发起，目标是开发一种交互式的、具有多道程序处理能力的**分时操作系统**，以取代当时广泛使用的批处理操作系统。

说明：**分时操作系统**使一台计算机可以同时**为多个用户服务**，连接计算机的终端用户交互式发出命令，操作系统采用**时间片轮转**的方式处理用户的服务请求并在终端上显示结果（操作系统将CPU的时间划分成若干个片段，称为**时间片**）。操作系统以时间片为单位，**轮流为每个终端用户服务，每次服务一个时间片**。

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

1.Linux的文件系统采用级层式的树状目录结构，最上层是根目录 /，在更目录下再创建其他的目录

2.Linux中，一切皆是文件

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



## Linux 关机

正确的关机流程为：sync > shutdown > reboot > halt    h代表halt

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



## 关闭防火墙

systemctl stop firewalld		 # 临时关闭防火墙
systemctl disable firewalld	 # 禁止开机启动



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

