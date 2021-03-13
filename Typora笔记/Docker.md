



# Docker

Docker通过容器技术，独立运行一个或一组应用。通过镜像来创建一个或多个容器

## Docker架构图

![image-20210309162236742](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210309162236742.png)



### ![image-20210313165130798](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313165130798.png)



### ![image-20210313170305804](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313170305804.png)Docker组成

#### Client

​		类似Redis-cli

#### Docker_Host

- Docker daemon 				   Docker的守护进程
- Images                                  镜像
- Containers                             容器，运行镜像Images长成的多个容器。容器互相隔离。容器可以启停，删除等

#### Registry

镜像的来源和保存位置

分为

- 公有Registry
  - Docker hub （国外的，速度慢，需配置镜像加速）
  - 阿里云
  - 等等
- 私有Registry



## 安装Docker

```bash
# 1.查看系统内核， 需要3.10以上
[viper@192 ~]$ uname -r
3.10.0-1160.el7.x86_64

# 查看系统版本
[viper@192 ~]$ cat /etc/os-release
NAME="CentOS Linux"
VERSION="7 (Core)"
ID="centos"
ID_LIKE="rhel fedora"
VERSION_ID="7"
PRETTY_NAME="CentOS Linux 7 (Core)"
ANSI_COLOR="0;31"
CPE_NAME="cpe:/o:centos:centos:7"
HOME_URL="https://www.centos.org/"
BUG_REPORT_URL="https://bugs.centos.org/"

CENTOS_MANTISBT_PROJECT="CentOS-7"
CENTOS_MANTISBT_PROJECT_VERSION="7"
REDHAT_SUPPORT_PRODUCT="centos"
REDHAT_SUPPORT_PRODUCT_VERSION="7"

# 2.卸载Docker旧版本
[root@192 lib]# yum remove docker \
>                   docker-client \
>                   docker-client-latest \
>                   docker-common \
>                   docker-latest \
>                   docker-latest-logrotate \
>                   docker-logrotate \
>                   docker-engine
# 3. 安装yum的工具包
[root@192 lib]# yum intall -y yum-utils

# 4. 配置Docker的镜像下载地址
[root@192 lib]# yum-config-manager \
>     --add-repo \
>     http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

# 5. 安装Docker 相关的引擎
[root@192 lib]# yum install docker-ce docker-ce-cli containerd.io

# 6. 启动docker服务 daemon 进程
[root@localhost local]# systemctl enable docker.service

# 设置开机启动docker服务
[root@localhost local]# systemctl start docker.service

# 重启docker服务
[root@localhost local]# systemctl daemon-reload

# 6. 查看Docker本地镜像库
[root@192 lib]# docker images

# 7.查看Docker 版本
[root@192 lib]# docker version
Client: Docker Engine - Community
 Version:           20.10.5
 API version:       1.41
 Go version:        go1.13.15
 Git commit:        55c4c88
 Built:             Tue Mar  2 20:33:55 2021
 OS/Arch:           linux/amd64
 Context:           default
 Experimental:      true

Server: Docker Engine - Community
 Engine:
  Version:          20.10.5
  API version:      1.41 (minimum version 1.12)
  Go version:       go1.13.15
  Git commit:       363e9a8
  Built:            Tue Mar  2 20:32:17 2021
  OS/Arch:          linux/amd64
  Experimental:     false
 containerd:
  Version:          1.4.4
  GitCommit:        05f951a3781f4f2c1911b05e61c160e9c30eaa8e
 runc:
  Version:          1.0.0-rc93
  GitCommit:        12644e614e25b05da6fd08a38ffa0cfe1903fdec
 docker-init:
  Version:          0.19.0
  GitCommit:        de40ad0
  
  # docker run 镜像名   运行hello-world测试
  [root@192 lib]# docker run hello-world
Unable to find image 'hello-world:latest' locally  #本地镜像仓库没找到hello-world镜像
latest: Pulling from library/hello-world  # 从远程镜像库拉取hello-world镜像
b8dfde127a29: Pull complete 
Digest: sha256:89b647c604b2a436fc3aa56ab1ec515c26b085ac0c15b0d105bc475be15738fb
Status: Downloaded newer image for hello-world:latest

Hello from Docker!  # hello-world镜像运行结果
This message shows that your installation appears to be working correctly.

To generate this message, Docker took the following steps: # Docker 执行了哪些操作
 1. The Docker client contacted the Docker daemon. # Docker客户端连接Docker守护进程
 2. The Docker daemon pulled the "hello-world" image from the Docker Hub.
    (amd64) # Docker守护进程从Docker Hub拉取 hello-world镜像
 3. The Docker daemon created a new container from that image which runs the
    executable that produces the output you are currently reading.# 通过镜像Docker 守护进程创建一个容器，该容器运行可执行文件，生成最终运行结果
 4. The Docker daemon streamed that output to the Docker client, which sent it
    to your terminal.  # Docker守护进程将输出流到Docker客户端，由客户端发送你的终端

To try something more ambitious, you can run an Ubuntu container with:
 $ docker run -it ubuntu bash

Share images, automate workflows, and more with a free Docker ID:
 https://hub.docker.com/

For more examples and ideas, visit:
 https://docs.docker.com/get-started/
 
 
 # 测试运行一个本地和远程镜像库都不存在的镜像，会有有什么结果
 [root@192 lib]# docker run viper3614
 # 请求访问的资源被拒绝
Unable to find image 'viper3614:latest' locally
docker: Error response from daemon: pull access denied for viper3614, repository does not exist or may require 'docker login': denied: requested access to the resource is denied.
See 'docker run --help'.



```

## Docker 常用命令

### Docker命令官网地址 

 https://docs.docker.com/reference/

```bash



# Docker 常用命令
# docker info 查看docker系统信息，包括镜像和容器
[root@192 lib]# docker info
Client:
 Context:    default
 Debug Mode: false
 Plugins:
  app: Docker App (Docker Inc., v0.9.1-beta3)
  buildx: Build with BuildKit (Docker Inc., v0.5.1-docker)

Server:
 Containers: 3
  Running: 0
  Paused: 0
  Stopped: 3
 Images: 2
 Server Version: 20.10.5
 Storage Driver: overlay2
  Backing Filesystem: xfs
  Supports d_type: true
  Native Overlay Diff: true
 Logging Driver: json-file
 Cgroup Driver: cgroupfs
 Cgroup Version: 1
 Plugins:
  Volume: local
  Network: bridge host ipvlan macvlan null overlay
  Log: awslogs fluentd gcplogs gelf journald json-file local logentries splunk syslog
 Swarm: inactive
 Runtimes: io.containerd.runc.v2 io.containerd.runtime.v1.linux runc
 Default Runtime: runc
 Init Binary: docker-init
 containerd version: 05f951a3781f4f2c1911b05e61c160e9c30eaa8e
 runc version: 12644e614e25b05da6fd08a38ffa0cfe1903fdec
 init version: de40ad0
 Security Options:
  seccomp
   Profile: default
 Kernel Version: 3.10.0-1160.el7.x86_64
 Operating System: CentOS Linux 7 (Core)
 OSType: linux
 Architecture: x86_64
 CPUs: 1
 Total Memory: 972.3MiB
 Name: 192.168.1.102
 ID: VPO3:ZV2H:FR3X:TCLJ:5YO3:RUPI:2CAB:QALF:GZKS:ERRO:TYQ2:OQNE
 Docker Root Dir: /var/lib/docker
 Debug Mode: false
 Registry: https://index.docker.io/v1/
 Labels:
 Experimental: false
 Insecure Registries:
  127.0.0.0/8
 Live Restore Enabled: false


```



### 镜像命令

```bash
# docker pull 镜像名  下载镜像，默认下载最新的版本 【tag --> lastest】
[root@192 lib]# docker pull mysql
Using default tag: latest  # 不指定版本 ，默认下载tag为最新的版本
latest: Pulling from library/mysql
45b42c59be33: Pull complete  # 分层下载。docker images的核心 联合文件系统
b4f790bd91da: Pull complete 
325ae51788e9: Pull complete 
adcb9439d751: Pull complete 
174c7fe16c78: Pull complete 
698058ef136c: Pull complete 
4690143a669e: Pull complete 
f7599a246fd6: Pull complete 
35a55bf0c196: Pull complete 
790ac54f4c47: Pull complete 
b0ddd5d1b543: Pull complete 
1aefd67cb33d: Pull complete 
Digest: sha256:7706e4c382be813b58ef514f2bdac747cd463a6866c6c81165d42a1d0e4fe947 # 签名
Status: Downloaded newer image for mysql:latest
docker.io/library/mysql:latest  # 镜像真实地址

# docker pull mysql == docker pull docker.io/library/mysql:latest  两个命令效果一样

# docker pull 镜像名:版本  下载指定版本的镜像（指定的版本在docker hub中必须存在）
[root@192 lib]# docker pull mysql:5.7
5.7: Pulling from library/mysql
45b42c59be33: Already exists  # 联合文件系统，已经存在的不需要再次下载了
b4f790bd91da: Already exists 
325ae51788e9: Already exists 
adcb9439d751: Already exists 
174c7fe16c78: Already exists 
698058ef136c: Already exists 
4690143a669e: Already exists 
66676c1ab9b3: Pull complete 
25ebf78a38b6: Pull complete 
a6510e5d6228: Pull complete 
90ca045d52c5: Pull complete 
Digest: sha256:9fc60b229633ce1d1f2ee306705152d4b001056fb27c1b5debe23a732df72b70
Status: Downloaded newer image for mysql:5.7
docker.io/library/mysql:5.7


# docker rmi 镜像id/镜像名称 删除指定id或名称的镜像
[root@192 lib]# docker rmi -f d1165f221234
Untagged: hello-world:latest
Untagged: hello-world@sha256:89b647c604b2a436fc3aa56ab1ec515c26b085ac0c15b0d105bc475be15738fb
Deleted: sha256:d1165f2212346b2bab48cb01c1e39ee8ad1be46b87873d9ca7a4e434980a7726

# docker rmi -f $(需要删除的参数) 删除所有镜像
[root@192 lib]# docker rmi -f $(docker images -aq 查询docker的所有镜像)
Untagged: mysql:5.7
Untagged: mysql@sha256:9fc60b229633ce1d1f2ee306705152d4b001056fb27c1b5debe23a732df72b70
Deleted: sha256:d54bd1054823adac521a08c227f8f7106e93158184f0978eda0eb6ab7b4a5b38
Deleted: sha256:81df52d6b6986ce243ecad13c5a33d6685cca38a7f246e275c20b459068ac8bc
Deleted: sha256:76c8845eec0bc7703e321f1f5a312033cb73d4bbea7212517cdfd84ef52f532d
Deleted: sha256:b851a484b18c5d3d25497260c111631ae3adf924eb10baa533b2a5b03b339d1a
Deleted: sha256:b5133b076285236e7fd98c42c1f18f57e2b4ed98daaed7b0afb3b98b804d6f25
Untagged: mysql:latest
Untagged: mysql@sha256:7706e4c382be813b58ef514f2bdac747cd463a6866c6c81165d42a1d0e4fe947
Deleted: sha256:8457e9155715d4e1c80c9e048d94c9b47b5b733fa927756280382dd326403644
Deleted: sha256:f0d02d3f5fc5a0f745bf3a97ec0b26c6b2d8b05288d98d954eeb87c4a6d47146
Deleted: sha256:bf1129a8799d8beaafa396d6333a3ba6eac9d0d7f606491f9794c470fb2dd311
Deleted: sha256:4386f82820992c927b924177ed3e4c2ffd477d4db7a63539ac76fd09ee36cd89
Deleted: sha256:d7494c9168a11444d8b13558068409ace7393452f08f878686eec45122ee56c1
Deleted: sha256:08dbcab3fe630e39bbabaa9f0ae72ec6d100bf1e400ebb4b7f04151b18bca89c
Deleted: sha256:c3f78dcd6bcc4c156554296323e0eed74a4d2d93b304be15f55c1ef62dd06e0a
Deleted: sha256:f89b66495a65489290c8edb71e0dbf9e3d0d6213b82cebc2554b271599f2f99d
Deleted: sha256:1918839317d9988ff5e0168e336717e32820af1e77c3121297efc73a387ecdc5
Deleted: sha256:1d2bcd52664a92805e5f49d94d3649323dd0f5682ae3e1380fa07b7a54d6ceb0
Deleted: sha256:787de05fee96c7ba99e49f17d72aec68769a7373a8881a27917bdbf83dca58e8
Deleted: sha256:eb82f9a2fbd7a4a0fdfbe40b5e77a995ccf73ab91364d90f4db820fd59dbf63b
Deleted: sha256:9eb82f04c782ef3f5ca25911e60d75e441ce0fe82e49f0dbf02c81a3161d1300
Error: No such image: 300e315adb2f

# docker rmi 镜像id/镜像名  镜像id/镜像名  镜像id/镜像名    删除多个镜像
```



### 容器命令

```bash
# 启动容器
# 参数说明
# docker run --name="取名" 启动容器并命名，如tomcat1，tomcat2...
# -d 后台方式运行容器
# -it 使用交互方式运行，进入容器查看内容
# -p 指定容器的端口 -p 8080:8080
		-p ip:主机端口：容器端口
		-p 主机端口：容器端口(常用)
		-p 容器端口
# -P 随机指定端口  （大写P）
# docker run -it centos /bin/bash 进入centos容器 
[root@localhost local]# docker run -it centos /bin/bash

# exit 停止并退出容器
[root@20f2e76007da /]# exit
exit

# CTRL + p + q 容器不停止退出
[root@ce7503d568ef /]# [root@localhost local]# 

# docker pause 暂停容器
[root@localhost home]# docker pause 80cd48dd4691
80cd48dd4691

# docker unpause 停止暂停容器
[root@localhost home]# docker unpause 80cd48dd4691
80cd48dd4691


# docker ps 查看所有运行的容器
[root@localhost local]# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES

# docker ps -a 当前运行和曾经运行过的容器
[root@localhost local]# docker ps -a
CONTAINER ID   IMAGE          COMMAND       CREATED         STATUS                          PORTS     NAMES
20f2e76007da   centos         "/bin/bash"   8 minutes ago   Exited (0) About a minute ago             ecstatic_rubin
553acbbeb120   d1165f221234   "/hello"      30 hours ago    Exited (0) 30 hours ago                   fervent_golick
811c50f94ed6   d1165f221234   "/hello"      30 hours ago    Exited (0) 30 hours ago                   elegant_tesla
67c0e75b5de
# docker ps -a -n=数量  显示最近创建的容器
[root@localhost local]# docker ps -a -n=1
CONTAINER ID   IMAGE     COMMAND       CREATED          STATUS                     PORTS     NAMES
20f2e76007da   centos    "/bin/bash"   11 minutes ago   Exited (0) 3 minutes ago             ecstatic_rubin

# 显示所有容器的编号
[root@localhost local]# docker ps -aq
20f2e76007da
553acbbeb120
811c50f94ed6
67c0e75b5dee

# docker rm  删除容器
[root@localhost local]# docker rm c8e4d202fd71
c8e4d202fd71

# docker rm -f $(docker ps -aq)  删除所有容器
# docker ps -a -q|xargs docker rm  删除所有容器

# docker start 容器id 启动容器

# docker restart 容器id 重启容器

# docker kill 容器id  强制结束容器进程

# docker run -d 镜像名 后台启动
[root@localhost local]# docker run -d centos
e5aa88d29303b599525c8d4e47e503097a369dec3df86771c2c01d785f85c5e4

# 查看启动的docker容器,发现没有后台启动的centos
# 常见的坑: docker容器使用后台运行,需要一个前台进程.docker发现没有应用,就会自动停止
[root@localhost local]# docker ps
CONTAINER ID   IMAGE     COMMAND   CREATED   STATUS    PORTS     NAMES

# 查看指定行数日志
[root@localhost local]# docker logs -tf --tail 10 c5f5af830d4e
2021-03-10T15:37:17.822037518Z [root@c5f5af830d4e /]# 
2021-03-10T15:37:18.012156406Z [root@c5f5af830d4e /]# 
2021-03-10T15:37:18.132128565Z [root@c5f5af830d4e /]# 
2021-03-10T15:37:18.268287893Z [root@c5f5af830d4e /]# 
2021-03-10T15:37:35.981566096Z [root@c5f5af830d4e /]# docker ps            
2021-03-10T15:37:35.981592158Z bash: docker: command not found


# docker top 容器运行id 查看容器中进程信息
[root@localhost local]# docker ps
CONTAINER ID   IMAGE     COMMAND                  CREATED         STATUS         PORTS     NAMES
bb7bef32238d   centos    "/bin/sh -c 'while t…"   4 minutes ago   Up 4 minutes             angry_easley

# docker top 容器运行id 查看容器中进程信息
[root@localhost local]# docker top bb7bef32238d
UID                 PID                 PPID                C                   STIME               TTY                 TIME                CMD
root                7118                7099                0                   23:50               ?                   00:00:00            /bin/sh -c while true;do echo hello world;sleep 5;done
root                7593                7118                0                   23:55               ?                   00:00:00            /usr/bin/coreutils --coreutils-prog-shebang=sleep /usr/bin/sleep 5


#  docker inpsect 容器id 查看镜像的元数据
[root@localhost local]# docker inspect bb7bef32238d
[
    {
        "Id": "bb7bef32238df9190bde1d105c74f0087ecd65946882eab25d859ea572ed805a",
        "Created": "2021-03-10T15:50:51.549100508Z",
        "Path": "/bin/sh",
        "Args": [
            "-c",
            "while true;do echo hello world;sleep 5;done"
        ],
        "State": {
            "Status": "running",
            "Running": true,
            "Paused": false,
            "Restarting": false,
            "OOMKilled": false,
            "Dead": false,
            "Pid": 7118,
            "ExitCode": 0,
            "Error": "",
            "StartedAt": "2021-03-10T15:50:52.364533023Z",
            "FinishedAt": "0001-01-01T00:00:00Z"
        },
        "Image": "sha256:300e315adb2f96afe5f0b2780b87f28ae95231fe3bdd1e16b9ba606307728f55",
        "ResolvConfPath": "/var/lib/docker/containers/bb7bef32238df9190bde1d105c74f0087ecd65946882eab25d859ea572ed805a/resolv.conf",
        "HostnamePath": "/var/lib/docker/containers/bb7bef32238df9190bde1d105c74f0087ecd65946882eab25d859ea572ed805a/hostname",
        "HostsPath": "/var/lib/docker/containers/bb7bef32238df9190bde1d105c74f0087ecd65946882eab25d859ea572ed805a/hosts",
        "LogPath": "/var/lib/docker/containers/bb7bef32238df9190bde1d105c74f0087ecd65946882eab25d859ea572ed805a/bb7bef32238df9190bde1d105c74f0087ecd65946882eab25d859ea572ed805a-json.log",
        "Name": "/angry_easley",
        "RestartCount": 0,
        "Driver": "overlay2",
        "Platform": "linux",
        "MountLabel": "",
        "ProcessLabel": "",
        "AppArmorProfile": "",
        "ExecIDs": null,
        "HostConfig": {
            "Binds": null,
            "ContainerIDFile": "",
            "LogConfig": {
                "Type": "json-file",
                "Config": {}
            },
            "NetworkMode": "default",
            "PortBindings": {},
            "RestartPolicy": {
                "Name": "no",
                "MaximumRetryCount": 0
            },
            "AutoRemove": false,
            "VolumeDriver": "",
            "VolumesFrom": null,
            "CapAdd": null,
            "CapDrop": null,
            "CgroupnsMode": "host",
            "Dns": [],
            "DnsOptions": [],
            "DnsSearch": [],
            "ExtraHosts": null,
            "GroupAdd": null,
            "IpcMode": "private",
            "Cgroup": "",
            "Links": null,
            "OomScoreAdj": 0,
            "PidMode": "",
            "Privileged": false,
            "PublishAllPorts": false,
            "ReadonlyRootfs": false,
            "SecurityOpt": null,
            "UTSMode": "",
            "UsernsMode": "",
            "ShmSize": 67108864,
            "Runtime": "runc",
            "ConsoleSize": [
                0,
                0
            ],
            "Isolation": "",
            "CpuShares": 0,
            "Memory": 0,
            "NanoCpus": 0,
            "CgroupParent": "",
            "BlkioWeight": 0,
            "BlkioWeightDevice": [],
            "BlkioDeviceReadBps": null,
            "BlkioDeviceWriteBps": null,
            "BlkioDeviceReadIOps": null,
            "BlkioDeviceWriteIOps": null,
            "CpuPeriod": 0,
            "CpuQuota": 0,
            "CpuRealtimePeriod": 0,
            "CpuRealtimeRuntime": 0,
            "CpusetCpus": "",
            "CpusetMems": "",
            "Devices": [],
            "DeviceCgroupRules": null,
            "DeviceRequests": null,
            "KernelMemory": 0,
            "KernelMemoryTCP": 0,
            "MemoryReservation": 0,
            "MemorySwap": 0,
            "MemorySwappiness": null,
            "OomKillDisable": false,
            "PidsLimit": null,
            "Ulimits": null,
            "CpuCount": 0,
            "CpuPercent": 0,
            "IOMaximumIOps": 0,
            "IOMaximumBandwidth": 0,
            "MaskedPaths": [
                "/proc/asound",
                "/proc/acpi",
                "/proc/kcore",
                "/proc/keys",
                "/proc/latency_stats",
                "/proc/timer_list",
                "/proc/timer_stats",
                "/proc/sched_debug",
                "/proc/scsi",
                "/sys/firmware"
            ],
            "ReadonlyPaths": [
                "/proc/bus",
                "/proc/fs",
                "/proc/irq",
                "/proc/sys",
                "/proc/sysrq-trigger"
            ]
        },
        "GraphDriver": {
            "Data": {
                "LowerDir": "/var/lib/docker/overlay2/c4886b5e6a78c7d7fe72f4fa1f9e0605296d2f37cf58d1c60ad09accc5f2876f-init/diff:/var/lib/docker/overlay2/5a3d6c9a78a86396c439ac0725e9fcf69354e4e2ccfc5c6bd6b94332d0e052b5/diff",
                "MergedDir": "/var/lib/docker/overlay2/c4886b5e6a78c7d7fe72f4fa1f9e0605296d2f37cf58d1c60ad09accc5f2876f/merged",
                "UpperDir": "/var/lib/docker/overlay2/c4886b5e6a78c7d7fe72f4fa1f9e0605296d2f37cf58d1c60ad09accc5f2876f/diff",
                "WorkDir": "/var/lib/docker/overlay2/c4886b5e6a78c7d7fe72f4fa1f9e0605296d2f37cf58d1c60ad09accc5f2876f/work"
            },
            "Name": "overlay2"
        },
        "Mounts": [],
        "Config": {
            "Hostname": "bb7bef32238d",
            "Domainname": "",
            "User": "",
            "AttachStdin": false,
            "AttachStdout": false,
            "AttachStderr": false,
            "Tty": false,
            "OpenStdin": false,
            "StdinOnce": false,
            "Env": [
                "PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin"
            ],
            "Cmd": [
                "/bin/sh",
                "-c",
                "while true;do echo hello world;sleep 5;done"
            ],
            "Image": "centos",
            "Volumes": null,
            "WorkingDir": "",
            "Entrypoint": null,
            "OnBuild": null,
            "Labels": {
                "org.label-schema.build-date": "20201204",
                "org.label-schema.license": "GPLv2",
                "org.label-schema.name": "CentOS Base Image",
                "org.label-schema.schema-version": "1.0",
                "org.label-schema.vendor": "CentOS"
            }
        },
        "NetworkSettings": {
            "Bridge": "",
            "SandboxID": "1a54658263a886868f095e431553bcf8242851c766f64c90abc03e4aeeccaa46",
            "HairpinMode": false,
            "LinkLocalIPv6Address": "",
            "LinkLocalIPv6PrefixLen": 0,
            "Ports": {},
            "SandboxKey": "/var/run/docker/netns/1a54658263a8",
            "SecondaryIPAddresses": null,
            "SecondaryIPv6Addresses": null,
            "EndpointID": "d4bb42054c99f8856048abadb6d1f8d11f8761139c33c2c153240e34da9c43c8",
            "Gateway": "172.17.0.1",
            "GlobalIPv6Address": "",
            "GlobalIPv6PrefixLen": 0,
            "IPAddress": "172.17.0.2",
            "IPPrefixLen": 16,
            "IPv6Gateway": "",
            "MacAddress": "02:42:ac:11:00:02",
            "Networks": {
                "bridge": {
                    "IPAMConfig": null,
                    "Links": null,
                    "Aliases": null,
                    "NetworkID": "506ed021dc4c8c104095f58b6d046b7bf37f3e372eb71c2690878e0fffb27c9a",
                    "EndpointID": "d4bb42054c99f8856048abadb6d1f8d11f8761139c33c2c153240e34da9c43c8",
                    "Gateway": "172.17.0.1",
                    "IPAddress": "172.17.0.2",
                    "IPPrefixLen": 16,
                    "IPv6Gateway": "",
                    "GlobalIPv6Address": "",
                    "GlobalIPv6PrefixLen": 0,
                    "MacAddress": "02:42:ac:11:00:02",
                    "DriverOpts": null
                }
            }
        }
    }
]



# 进入运行中的容器 两种方式
# 1. docker exec -it 容器id bashShell 会连接到容器，可以像SSH一样进入容器内部，进行操作，可以通过exit退出容器，不影响容器运行。(常用)
[root@localhost /]# docker exec -it bb7bef32238d /bin/bash

# 2. docker attach 容器id   会通过连接stdin，连接到容器内输入输出流，会在输入exit后终止进程.


# docker cp 容器id：/目录/...  /目的地  将容器内的文件拷贝到主机
[root@localhost home]# docker cp 34354cee526d:/home/test.java /home
[root@localhost home]# ls
test.java  viper  viper.java

```

# 实例

下载ngix镜像并运行

````bash
# docker search 镜像名：版本
[root@localhost home]# docker search nginx


# docker pull 镜像名称:版本  从docker hub拉取镜像

# docker run -d --name 自定义名称 -p 主机端口：容器端口 镜像名  运行nginx
[root@localhost home]# docker run -d --name nginx-01 -p 5555:80 nginx
145500fb59e63aaa4a91e629b88b67c1e89e8167e19b50ee07ba6d677ff1c354

````

# Docker可视化工具portainer

```bash
# 安装portainer-ce版
docker run -d -p 8000:8000 -p 9000:9000 --name=portainer --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v portainer_data:/data portainer/portainer-ce

# 安装portainer client
docker run -d -p 9001:9001 --name portainer_agent --restart=always -v /var/run/docker.sock:/var/run/docker.sock -v /var/lib/docker/volumes:/var/lib/docker/volumes portainer/agent

```

![image-20210311172948842](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210311172948842.png)

# Docker镜像理解

## 概念

Docker镜像是一种轻量级，可执行的独立软件包。用来打包软件运行环境和基于运行环境开发的软件。包含运行软件需要的所有内容，包括代码，运行时，库，环境变量，配置文件等等

所有应用，直接打包Docker镜像

## 获取镜像

- 远程仓库下载
- 拉取别人镜像
- 自己制作镜像 DockerFIle

## Docker镜像加载原理

### UnionFS 联合文件系统

UnionFS是一种分层，轻量级，高性能的文件系统。支持对文件系统的修改作为一次提交来层层的叠加。同时可以将不同目录挂载到同一个虚拟文件系统下。

UnionFS是Docker镜像的基础。

镜像可以通过分层来进行继承，基于基础镜像，可以制作各种具体的应用镜像。

![image-20210311174656937](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210311174656937.png)

#### bootFS

( boot file system ) 主要包括bootloader和kernel，

bootloader主要是引导加载kernel，Linux刚启动的时候会加载bootFS文件系统。

当boot加载完毕，整个内核就在内存中了。此时内存的使用权限由bootFS转交给内核kernel，系统也会卸载bootFS

#### rootfS 

(root file system) ，在boot FS之上。包含Linux/Unix系统中的 /dev /proc /bin /etc 等标准目录和文件

root FS就是不同操作系统的发行版，如Ubuntu，CentOS等

### 为什么Docker的系统很小

对于一个精简的OS，rootFS可以很小，只需要包含最基本的命令，工具和程序库就可以了。

因为底层直接使用Host的Kernel，自己只需要提供root FS就可以了 。

不同的Linux发行版，bootFS基本一致。rootFS会由差别。

因此，不同的发行版可以使用共用的bootFS

### Docker 镜像特点

Docker镜像默认为**只读**，当容器启动的时候，一个新的可写层加入到镜像的顶部

新加的这一层，就是通常说的容器层，容器之下的都称为镜像层。

### 提交自己的镜像

commit镜像

```bash
# docker commit 提交镜像 
# -a="作者"
# -m="镜像描述"
# d04f2c2dcc9d 运行的容器id
# mytomcat:1.0 命名新的镜像和版本
[root@192 viper]# docker commit -a="Viper" -m="add webapps" d04f2c2dcc9d mytomcat:1.0
sha256:18efcc44e96f6cb050383c522bca824eb53f273fcdc22b680c2f100709fb44b6

```



# 容器数据卷

## 概念

Docker容器中产生的数据，同步到本地

就是目录的挂载。--- 将容器内的目录，挂载到宿主机上。

## 作用

- 容器中数据的持久化和同步操作
- 容器之间的数据可以共享

## 使用数据卷

### 方式一命令方式

#### 

```bash
# docker run -it  -v 主机目录：容器目录   v=volume【体积，容量】
[root@192 viper]# docker run -it -v /home/ceshi:/home centos /bin/bash

# docker inspect 容器id 查看容器信息
[root@192 home]# docker inspect 5db2113f2b3b
 "Mounts": [   #挂载的信息
            {
                "Type": "bind",   
                "Source": "/home/ceshi",   # 主机目录
                "Destination": "/home",    # docker容器的目录
                "Mode": "",
                "RW": true,    # 容器的读写权限 RW:可读写  RO：只读
                "Propagation": "rprivate"  #传播方式
            }
容器内部的文件会同步到主机。即使容器被删除，数据也会同步到主机中
主机修改文件内容也会同步到容器内。即使容器已经停止

# docker volume ls 查看所有的volume信息
[root@192 _data]# docker volume ls
DRIVER    VOLUME NAME
local     2c1871da6dbe331ebb6fa7c463f2a5bc4bb331facafcc40795b5fbce7e5f0c63
local     juming-nginx
local     juming-nginx03
local     portainer_data


# docker volume inspect juming-nginx03 查看指定卷信息
[root@192 _data]# docker volume inspect juming-nginx03
[
    {
        "CreatedAt": "2021-03-12T03:13:52+08:00",
        "Driver": "local",
        "Labels": null,
        "Mountpoint": "/var/lib/docker/volumes/juming-nginx03/_data",  # 数据卷在主机的位置
        "Name": "juming-nginx03",
        "Options": null,
        "Scope": "local"
    }
]
```

#### 测试

安装启动mysql

```bash
# 参数说明  -d 后台运行  -p 主机端口：容器端口 映射  -v 主机目录：容器目录 数据卷挂载 -e 配置环境 MYSQL_ROOT_PASSWORD 设置密码  --name 容器自定义名
[root@192 viper]# docker run -d -p 3310:3306 -v /home/mysql/conf:/ect/mysql/conf.d -v /home/mysql/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root --name mysql01 mysql:5.7
28461cef20bcd3953483e8112f664c4e9f4fa47f694c8ee74fc60f8cd150470e

```

### 具名挂载

```bash
# 起了名字的挂载数据卷
[root@192 viper]# docker run -d -P --name nginx01 -v juming-nginx:/etc/nginx nginx
497fed6b9efac4132c641af1cb7626a01c50e246606ad28c7d779f1003b912f1
```



### 匿名挂载

所有的docker容器内的卷，没有指定目录的情况下，默认路径为：/var/lib/docker/volumes/卷名/_data  路径下

```bash
# 只指定了容器的路径，没有指定主句的路径
[root@192 viper]# docker run -d -P --name nginx02 -v /etc/nginx nginx
afdceabe9338053b36d06b2046948c88c770a9b59fdee436a324972e01508fcd


# docker inspect  查看挂载目录
"Mounts": [
            {
                "Type": "volume",
                "Name": "2c1871da6dbe331ebb6fa7c463f2a5bc4bb331facafcc40795b5fbce7e5f0c63",
                "Source":   "/var/lib/docker/volumes/2c1871da6dbe331ebb6fa7c463f2a5bc4bb331facafcc40795b5fbce7e5f0c63/_data", 
                # 挂载到主机这个目录下了 
                "Destination": "/etc/nginx",
                "Driver": "local",
                "Mode": "",
                "RW": true,
                "Propagation": ""
            }


```



## 区分具名，匿名，路径挂载

```bash
-v 容器路径             # 匿名挂载
-v 卷名：容器路径       # 具名挂载
-v /主机路径：容器路径  # 指定路径挂载
```

## ro 和 rw

```bash
# ro 就是read only，只读文件。文件的修改只能在主机修改，容器不能修改
docker run -d -P --name nginx03 -v juming-nginx:/ect/nginx:ro nginx 

# rw 容器对挂载的文件由读写的权限
docker run -d -P --name nginx03 -v juming-nginx:/ect/nginx:rw nginx 
```

## 多个容器同步数据

数据卷容器  ，（使用的是备份拷贝）

```bash
# 通过 --volumes-from 容器名/容器id  就可以在启动时与其他容器实现数据共享
[root@192 docker-test-volume]# docker run -it --name docker03 --volumes-from docker01 cd8b5a665b46

```



# DockerFile

## 概念

DockerFile就是用来构建docker镜像的构建文件，就是一些命令脚本

## DockerFile基础

- 每个关键字都必须为大写
- 从上到下依次执行
- “#” 表示注释
- 每个指令创建一个新的镜像层

![image-20210312174502139](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210312174502139.png)

![image-20210312210401261](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210312210401261.png)



```bash
# 步骤
# 1. 编写一个dockerfile文件
# 2. docker build 构建称为一个镜像
# 3. docker run 运行镜像
# 4. docker push 发布镜像 （可以发布到Docker Hub,阿里云镜像仓库）[开源or闭源]
```



```bash

# 创建一个命令文件 dockerfile01  这里的每个命令都是镜像中的一层
FROM centos   # 指令都为大写
VOLUME ["volume01","volume02"]  # 指定挂载卷
CMD echo "---end---"            # 结束输出一句话
CMD /bin/bash				  #  进入bash控制台

# docker build 创建镜像  -f dockerfile文件的目录地址 -t 名称，也可以选择' Name:tag'格式的标签
[root@192 docker-test-volume]# docker build -f dockerfile1  -t viper/centos .
Sending build context to Docker daemon  2.048kB
Step 1/4 : FROM centos
 ---> 300e315adb2f
Step 2/4 : VOLUME ["volume01","volume02"]
 ---> Running in 69a1bb68cf51
Removing intermediate container 69a1bb68cf51
 ---> 9023498c7857
Step 3/4 : CMD echo "---end---"
 ---> Running in 94d785d2feb6
Removing intermediate container 94d785d2feb6
 ---> ec25bda4f97f
Step 4/4 : CMD /bin/bash
 ---> Running in 588b3a8a5a0f
Removing intermediate container 588b3a8a5a0f
 ---> cd8b5a665b46
Successfully built cd8b5a665b46
Successfully tagged viper/centos:latest
# docker history 镜像id  查看镜像构建过程
[root@192 docker-test-volume]# docker history cd8b5a665b46
IMAGE          CREATED        CREATED BY                                      SIZE      COMMENT
cd8b5a665b46   4 hours ago    /bin/sh -c #(nop)  CMD ["/bin/sh" "-c" "/bin…   0B        
ec25bda4f97f   4 hours ago    /bin/sh -c #(nop)  CMD ["/bin/sh" "-c" "echo…   0B        
9023498c7857   4 hours ago    /bin/sh -c #(nop)  VOLUME [volume01 volume02]   0B        
300e315adb2f   3 months ago   /bin/sh -c #(nop)  CMD ["/bin/bash"]            0B        
<missing>      3 months ago   /bin/sh -c #(nop)  LABEL org.label-schema.sc…   0B        
<missing>      3 months ago   /bin/sh -c #(nop) ADD file:bd7a2aed6ede423b7…   209MB  
```

CMD命令 vs ENTRYPOINT命令 区别

```bash
CMD : 指定这个镜像启动的时候要运行的命令。只有最后一个会生效。可被替代
ENTRYPOINT：指定这个镜像启动的时候需要运行的命令。可以追加命令
```

测试CMD命令

```bash
# 创建dockerfile文件
[root@192 docker-test-volume]# vim dockerfile-cmd-test
# dockerfile文件命令详情 ，镜像启动时通过CMD 执行 ls -a 命令
[root@192 docker-test-volume]# cat dockerfile-cmd-test 
FROM centos
CMD ["ls","-a"]
# 执行镜像效果
[root@192 docker-test-volume]# docker run 484980a80371
.
..
.dockerenv
bin
dev
etc
home
lib
lib64
lost+found
media
mnt
opt
proc
root
run
sbin
srv
sys
tmp
usr
var

```

测试ENTRYPOINT命令

```bash
# 创建dockerfile文件
[root@192 docker-test-volume]# cat dockerfile-entrypoint-test 
FROM centos
ENTRYPOINT ["ls","-a"]   # entrypoint命令，运行镜像时可以追加命令

# docker build 通过dockerfile创建镜像
[root@192 docker-test-volume]# docker build -f dockerfile-entrypoint-test  -t entrypointtest .
Sending build context to Docker daemon  4.096kB
Step 1/2 : FROM centos  					# 构建镜像步骤
 ---> 300e315adb2f
Step 2/2 : ENTRYPOINT ["ls","-a"]			 # 构建镜像步骤
 ---> Running in 7c03f586faef
Removing intermediate container 7c03f586faef
 ---> 331ca837cb8a
Successfully built 331ca837cb8a
Successfully tagged entrypointtest:latest

# 运行镜像 ，通过entrypoint命令，运行时可以追加命令，这里追加了 -l, 即运行镜像时执行了 ls -al 命令
[root@192 docker-test-volume]# docker run 331ca837cb8a -l
total 0
drwxr-xr-x.   1 root root   6 Mar 12 13:23 .
drwxr-xr-x.   1 root root   6 Mar 12 13:23 ..
-rwxr-xr-x.   1 root root   0 Mar 12 13:23 .dockerenv
lrwxrwxrwx.   1 root root   7 Nov  3 15:22 bin -> usr/bin
drwxr-xr-x.   5 root root 340 Mar 12 13:23 dev
drwxr-xr-x.   1 root root  66 Mar 12 13:23 etc
drwxr-xr-x.   2 root root   6 Nov  3 15:22 home
lrwxrwxrwx.   1 root root   7 Nov  3 15:22 lib -> usr/lib
lrwxrwxrwx.   1 root root   9 Nov  3 15:22 lib64 -> usr/lib64
drwx------.   2 root root   6 Dec  4 17:37 lost+found
drwxr-xr-x.   2 root root   6 Nov  3 15:22 media
drwxr-xr-x.   2 root root   6 Nov  3 15:22 mnt
drwxr-xr-x.   2 root root   6 Nov  3 15:22 opt
dr-xr-xr-x. 239 root root   0 Mar 12 13:23 proc
dr-xr-x---.   2 root root 162 Dec  4 17:37 root
drwxr-xr-x.  11 root root 163 Dec  4 17:37 run
lrwxrwxrwx.   1 root root   8 Nov  3 15:22 sbin -> usr/sbin
drwxr-xr-x.   2 root root   6 Nov  3 15:22 srv
dr-xr-xr-x.  13 root root   0 Mar 12 08:46 sys
drwxrwxrwt.   7 root root 145 Dec  4 17:37 tmp
drwxr-xr-x.  12 root root 144 Dec  4 17:37 usr
drwxr-xr-x.  20 root root 262 Dec  4 17:37 var
[root@192 docker-test-volume]# cat dockerfile-entrypoint-test 
FROM centos
ENTRYPOINT ["ls","-a"]

```



## 镜像推送到Docker Hub上

```bash
# 1. 先通过docker tag 将镜像打标签
# 2. 通过 docker login 登陆 Docker Hub账号
# 3. 通过 docker push 推送到 Docker Hub上

# 1. docker tag 打标签 vipertomcat是本地镜像名称  wurongpeng是Docker Hub 账号，mytomcat是推送到Dock Hub后的镜像名称，  ：1.0是镜像版本号
[root@192 opt]# docker tag vipertomcat wurongpeng/mytomcat:1.0

# 查看 打过标签的镜像
[root@192 opt]# docker images 
REPOSITORY            TAG       IMAGE ID       CREATED         SIZE
vipertomcat           latest    b2c8792953af   7 minutes ago   640MB
wurongpeng/mytomcat   1.0       b2c8792953af   7 minutes ago   640MB
tomcat                latest    5fcb14d62101   41 hours ago    672MB
centos                latest    300e315adb2f   3 months ago    209MB

# docker push 推送镜像到 Docker Hub上
[root@192 opt]# docker push wurongpeng/mytomcat:1.0
The push refers to repository [docker.io/wurongpeng/mytomcat]
086e5e564345: Pushed 
7cd96e532f21: Pushed 
8269d9e7ea65: Pushed 
1b46cf5e8aac: Pushed 
2653d992f4ef: Pushed 
1.0: digest: sha256:355731555917966722ee7b397e1b428040e03135c55f06d6c0e1e41d10e52212 size: 1373

```

可以在 Docker Hub上看到推送的镜像

![image-20210313013543933](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313013543933.png)

# Docker网络

## Docker的网络模式分类

- Bridge : 桥接模式, Docker默认
- none : 不配置网络
- host : 与宿主机共享网络
- container : 容器网络连通 (用的少,局限性大)



Linux查看ip地址方式

```bash
# 1. ifconfig
[root@192 sha256]# ifconfig
docker0: flags=4099<UP,BROADCAST,MULTICAST>  mtu 1500
        inet 172.17.0.1  netmask 255.255.0.0  broadcast 172.17.255.255
        ether 02:42:50:e5:cd:ca  txqueuelen 0  (Ethernet)
        RX packets 0  bytes 0 (0.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 0  bytes 0 (0.0 B)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

ens33: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 192.168.1.108  netmask 255.255.255.255  broadcast 192.168.1.108
        inet6 fe80::a55:7663:4e13:56c4  prefixlen 64  scopeid 0x20<link>
        ether 00:0c:29:bc:92:a5  txqueuelen 1000  (Ethernet)
        RX packets 118722  bytes 174516467 (166.4 MiB)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 114965  bytes 9161453 (8.7 MiB)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
        inet 127.0.0.1  netmask 255.0.0.0
        inet6 ::1  prefixlen 128  scopeid 0x10<host>
        loop  txqueuelen 1000  (Local Loopback)
        RX packets 0  bytes 0 (0.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 0  bytes 0 (0.0 B)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

virbr0: flags=4099<UP,BROADCAST,MULTICAST>  mtu 1500
        inet 192.168.122.1  netmask 255.255.255.0  broadcast 192.168.122.255
        ether 52:54:00:96:b5:c7  txqueuelen 1000  (Ethernet)
        RX packets 0  bytes 0 (0.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 0  bytes 0 (0.0 B)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

# 2. ip addr
[root@192 sha256]# ip addr
1: lo: <LOOPBACK,UP,LOWER_UP> mtu 65536 qdisc noqueue state UNKNOWN group default qlen 1000
    link/loopback 00:00:00:00:00:00 brd 00:00:00:00:00:00
    inet 127.0.0.1/8 scope host lo
       valid_lft forever preferred_lft forever
    inet6 ::1/128 scope host 
       valid_lft forever preferred_lft forever
2: ens33: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc pfifo_fast state UP group default qlen 1000
    link/ether 00:0c:29:bc:92:a5 brd ff:ff:ff:ff:ff:ff
    inet 192.168.1.108/32 brd 192.168.1.108 scope global noprefixroute ens33
       valid_lft forever preferred_lft forever
    inet 192.168.1.102/24 brd 192.168.1.255 scope global noprefixroute dynamic ens33
       valid_lft 6602sec preferred_lft 6602sec
    inet6 fe80::a55:7663:4e13:56c4/64 scope link noprefixroute 
       valid_lft forever preferred_lft forever
3: virbr0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default qlen 1000
    link/ether 52:54:00:96:b5:c7 brd ff:ff:ff:ff:ff:ff
    inet 192.168.122.1/24 brd 192.168.122.255 scope global virbr0
       valid_lft forever preferred_lft forever
4: virbr0-nic: <BROADCAST,MULTICAST> mtu 1500 qdisc pfifo_fast master virbr0 state DOWN group default qlen 1000
    link/ether 52:54:00:96:b5:c7 brd ff:ff:ff:ff:ff:ff
5: docker0: <NO-CARRIER,BROADCAST,MULTICAST,UP> mtu 1500 qdisc noqueue state DOWN group default 
    link/ether 02:42:50:e5:cd:ca brd ff:ff:ff:ff:ff:ff
    inet 172.17.0.1/16 brd 172.17.255.255 scope global docker0
       valid_lft forever preferred_lft forever


# 3. route -n   查看 Linux 内核路由表
[root@192 sha256]# route -n
Kernel IP routing table
Destination     Gateway         Genmask         Flags Metric Ref    Use Iface
0.0.0.0         192.168.1.1     0.0.0.0         UG    100    0        0 ens33
172.17.0.0      0.0.0.0         255.255.0.0     U     0      0        0 docker0
192.168.1.0     0.0.0.0         255.255.255.0   U     100    0        0 ens33
192.168.1.108   0.0.0.0         255.255.255.255 UH    100    0        0 ens33
192.168.122.0   0.0.0.0         255.255.255.0   U     0      0        0 virbr0

# 网络目的地址,列出了路由器连接的所有网段
# 网络掩码,列出了这个网段本身的子网掩码,而不是连接到这个网段的网卡的子网掩码.这基本上能够让路由器确定目的网络的地质类
# 网关.一旦路由器确定它要把这个数据包转发到哪个目的网络,路由器就要查看网关列表.网关表告诉路由器这个数据包应该转发到哪一个ip地址才能到达目的网络
# 接口列. 告诉路由器哪一个网卡连接到了合适的目的网络.从技术上说,接口列告诉路由器分配给网卡的ip地址.那个网卡把路由器连接到目的网络.然而,路由器很聪明,知道这个地址绑定到哪一个物理网卡
# 测量.该值越小,可信度越高
```



## 		Docker 网络原理

- Docker安装时若不指定网络，会有一个默认网卡  docker0 （通过 ip addr可以看到），通过桥接模式(NAT)连接到物理网卡
- Linux的虚拟网络化：veth-pair技术
  - veth-pair技术： 全称 Virtual Ethernet Pair，是一个成对出现的端口。所有冲一端进入的数据包都会从另一端出来。引入Network Namespace实现了网络的隔离，使网络协议栈之间互不干扰。使用veth-pair实现NetworkNamespace间的数据通信
  - veth-pair只能实现点对点的通信。可以通过网桥Bridge技术实现多个网络接口间的通信
- 每次启动一个docker容器，不指定网络的情况下，都是docker0路由的。docker会分配一个ip给容器

![image-20210313181531931](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313181531931.png)



## 容器互联

### 使用link  (不建议使用)

```bash
# --link  运行镜像时指定连接的容器
[root@192 sha256]# docker run -d -P --name tomcat3 --link tomcat2 tomcat
5c2c3f3a0a708833a89717cd4fb639d06c733e537067adaf86a821398bad320c

# 查看 tomcat3 的hosts文件
[root@192 sha256]# docker exec -it tomcat3 cat /etc/hosts
127.0.0.1	localhost
::1	localhost ip6-localhost ip6-loopback
fe00::0	ip6-localnet
ff00::0	ip6-mcastprefix
ff02::1	ip6-allnodes
ff02::2	ip6-allrouters
172.17.0.3	tomcat2 a7d48c1a21f0   # 发现 --link命令就是在tomcat3容器的hosts文件添加了tomcat2的映射ip,所以tomcat3可以ping通tomcat2
172.17.0.4	5c2c3f3a0a70

# 查看tomcat2的hosts文件,发现没有tomcat3的映射IP ,所以tomcat2 ping不通tomcat3
[root@192 sha256]# docker exec -it tomcat2 cat /etc/hosts
127.0.0.1	localhost
::1	localhost ip6-localhost ip6-loopback
fe00::0	ip6-localnet
ff00::0	ip6-mcastprefix
ff02::1	ip6-allnodes
ff02::2	ip6-allrouters
172.17.0.3	a7d48c1a21f0


```

### 自定义网络(推荐)



```bash
# docker network ls 查看docker 网络
[root@192 sha256]# docker network ls
NETWORK ID     NAME      DRIVER    SCOPE
352132d41571   bridge    bridge    local
edf4b26fa74c   host      host      local
b42996fc1cec   none      null      local

# docker network --help
Commands:
  connect     Connect a container to a network  # 将容器连接到网络
  create      Create a network		# 创建一个网络							
  disconnect  Disconnect a container from a network # 将容器从网络中断开
  inspect     Display detailed information on one or more networks # 显示一个或多个网络信息
  ls          List networks  # 显示所有的网络
  prune       Remove all unused networks # 删除所有未使用的网络
  rm          Remove one or more networks  # 删除一个或多个网络
  

# 启动一个镜像时,会有一个网络连接的默认命令  --net bridge ,默认连接docker0
docker run -d -P tomcat1 --net bridge tocat


# docker network create 创建一个网络 --driver bridge 创建一个桥接模式网络(默认也是桥接); --subnet 子网(无类别域间路由CIDR); --gateway 网关;网络名
[root@192 sha256]# docker network create --driver bridge --subnet 192.168.0.0/16 --gateway 192.168.0.1 mynet
6ad5502a15f54c6ef6b3b7cb04a9fce02d26309760be2451731e076acad08537

# 查看创建的网络
[root@192 sha256]# docker network ls
NETWORK ID     NAME      DRIVER    SCOPE
352132d41571   bridge    bridge    local
edf4b26fa74c   host      host      local
6ad5502a15f5   mynet     bridge    local  # 自定义的网络
b42996fc1cec   none      null      local

# docker network inspect 网络名  查看自定义网络详情
[root@192 sha256]# docker network inspect mynet
[
    {
        "Name": "mynet",
        "Id": "6ad5502a15f54c6ef6b3b7cb04a9fce02d26309760be2451731e076acad08537",
        "Created": "2021-03-13T20:54:53.80144764+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "192.168.0.0/16",  # 自定义的子网 和 网关
                    "Gateway": "192.168.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {},
        "Options": {},
        "Labels": {}
    }
]

# 通过自定义网络启动两个Tomcat
# tomcat1
[root@192 sha256]# docker run -d -P --name tomcat-net1 --net mynet tomcat
5657e4eda5238ca932a453949524596b810b41be75ff11bbb91076d3d950e871
# tomcat2
[root@192 sha256]# docker run -d -P --name tomcat-net2 --net mynet tomcat
a2855ba463ef31778865cf3abcb44cc6d0a22d879274d8a8cbe14cc06fb041ec

# docker network inspect 网络名  启动两个Tomcat后,查看自定义网络
[root@192 sha256]# docker network inspect mynet
[
    {
        "Name": "mynet",
        "Id": "6ad5502a15f54c6ef6b3b7cb04a9fce02d26309760be2451731e076acad08537",
        "Created": "2021-03-13T20:54:53.80144764+08:00",
        "Scope": "local",
        "Driver": "bridge",
        "EnableIPv6": false,
        "IPAM": {
            "Driver": "default",
            "Options": {},
            "Config": [
                {
                    "Subnet": "192.168.0.0/16",
                    "Gateway": "192.168.0.1"
                }
            ]
        },
        "Internal": false,
        "Attachable": false,
        "Ingress": false,
        "ConfigFrom": {
            "Network": ""
        },
        "ConfigOnly": false,
        "Containers": {
            "5657e4eda5238ca932a453949524596b810b41be75ff11bbb91076d3d950e871": {
                "Name": "tomcat-net1",   # 刚启动的Tomcat1
                "EndpointID": "31fb1c08aa8ac7b8faaf3fb8aff26a471dcf0d298f9a5cf00ee230b6ec258939",
                "MacAddress": "02:42:c0:a8:00:02",
                "IPv4Address": "192.168.0.2/16",
                "IPv6Address": ""
            },
            "a2855ba463ef31778865cf3abcb44cc6d0a22d879274d8a8cbe14cc06fb041ec": {
                "Name": "tomcat-net2", # 刚启动的Tomcat2
                "EndpointID": "1c4e1ff49d21a561b6021412a53dc2b2714f745c723cc814b30417dfb8e80919",
                "MacAddress": "02:42:c0:a8:00:03",
                "IPv4Address": "192.168.0.3/16",
                "IPv6Address": ""
            }
        },
        "Options": {},
        "Labels": {}
    }
]


# 测试两个Tomcat互相ping ,发现容器之间产生了互联
# Tomcat1  ping Tomcat2
[root@192 sha256]# docker exec -it tomcat-net1 ping tomcat-net2
PING tomcat-net2 (192.168.0.3) 56(84) bytes of data.
64 bytes from tomcat-net2.mynet (192.168.0.3): icmp_seq=1 ttl=64 time=0.067 ms
64 bytes from tomcat-net2.mynet (192.168.0.3): icmp_seq=2 ttl=64 time=0.051 ms

# Tomcat2  ping Tomcat1
[root@192 sha256]# docker exec -it tomcat-net2 ping tomcat-net1
PING tomcat-net1 (192.168.0.2) 56(84) bytes of data.
64 bytes from tomcat-net1.mynet (192.168.0.2): icmp_seq=1 ttl=64 time=0.044 ms
64 bytes from tomcat-net1.mynet (192.168.0.2): icmp_seq=2 ttl=64 time=0.049 ms


# 启动一个Tomcat1,不指定网络,默认使用docker0的网络配置
[root@192 sha256]# docker run -d -P --name tomcat1 tomcat
19659f87ba2aef8113b0eae0c6a242cf842cb52f4a1f5560376d74da5fb7fa5e

# 直接 Tomcat1 ping Tomcat-net1是ping不通的
[root@192 sha256]# docker exec -it tomcat1 ping tomcat-net1
ping: tomcat-net1: Name or service not known

# docker network connect 网络名 容器名   ,将一个容器加入到一个网络 (容器间跨网络连通  network connect)
[root@192 sha256]# docker network connect  mynet tomcat1

# 将tomcat1加入mynet网络后,再去ping,可以ping通了 
[root@192 sha256]# docker exec -it tomcat1 ping tomcat-net1
PING tomcat-net1 (192.168.0.2) 56(84) bytes of data.
64 bytes from tomcat-net1.mynet (192.168.0.2): icmp_seq=1 ttl=64 time=0.058 ms
64 bytes from tomcat-net1.mynet (192.168.0.2): icmp_seq=2 ttl=64 time=0.054 ms

```

​	自定义网络 ,Docker已经帮我们维护好了容器间的网络关系,解决了docker0的局限性



​	

## 无类别域间路由

### 概念

Classless Inter-Domain-Routing ,简称CIDR. CIDR提供了更灵活的在路由器中指定网络地址的方法 (无视网络分类 - - !)

基本思想是去雄地址的分类结构（ABC类网络,网络号+主机号）,取而代之的是允许以可变长分解的方式分配网络数.

"无类型"的意思是选路决策是基于整个32位IP地址的掩码操作,不管其IP地址是A类,B类,C类

IPv4

​	被分为四个部分,每个部分8bit  00000000.00000000.00000000.00000000,转位十进制能表示的范围

​	0.0.0.0 ~255.255.255.255

​	IPv4分为5类 , abc类主要是两个部分: 网络号 + 主机号

![image-20210313190343962](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313190343962.png)

ABC类的最大主机数和私有ip范围

![image-20210313190601923](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313190601923.png)

e.g CIDR

192.168.0.56/18

192.168.0.56是网络本身地址

18表示前18位是地址的网络部分,其他部分代表主机

容器编排

集群部署



# Docker部署Redis集群

```bash
# docker network create   创建一个redis集群网络
[root@192 sha256]# docker network create redis --subnet 192.168.0.0/16
1479b4eae9ddfbdb92a8607923bc5579730068ecfb7f861b543283ccf9269986
[root@192 sha256]# docker network ls 
NETWORK ID     NAME      DRIVER    SCOPE
352132d41571   bridge    bridge    local
edf4b26fa74c   host      host      local
b42996fc1cec   none      null      local
1479b4eae9dd   redis     bridge    local  # redis专用网络


# 创建一个shell脚本用来创建redis的6个节点
for port in $(seq 1 6);
do
mkdir -p /mydata/redis/node-${port}/conf
touch /mydata/redis/node-${port}/conf/redis.conf
cat << EOF >/mydata/redis/node-${port}/conf/redis.conf
port 6379
bind 0.0.0.0
cluster-enabled yes
cluster-config-file nodes.conf
cluster-announce-ip 192.168.0.1${port}
cluster-announce-port 6379
cluster-announce-bus-port 16379
appendonly yes
EOF
done

# 修改shell文件执行权限,增加可执行权限
[root@192 home]# chmod +x ./redisclust.sh 
# 执行shell脚本
[root@192 home]# ./redisclust.sh 

# 启动六个redis节点
# 参数说明 
# -p 端口映射;
# --name 自定义容器名;
# -v 数据卷挂载,这里挂载了data和conf; 
# -d 后台运行; 
# --net 指定网络; 
# --ip 指定容器ip; 
# redis:5.0.9-alpine3.11 redis版本; 
# redis-server 启动项; 
# /etc/redis/redis.conf启动加载的配置文件
[root@192 home]# docker run -p 6371:6379 -p 16371:16379 --name redis-1 -v /mydata/redis/node-1/data:/data -v /mydata/redis/node-1/conf/redis.conf:/etc/redis/redis.conf -d --net redis --ip 192.168.0.11 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf


# 进入redis1 配置集群
[root@192 home]# docker exec -it redis-1 /bin/sh
/data # ls 
appendonly.aof  nodes.conf

# 配置redis集群  
/data # redis-cli --cluster create 192.168.0.11:6379 192.168.0.12:6379 192.168.0.13:6379 192.168.0.14:6379 192.168.0.15:6379 1
92.168.0.16:6379 --cluster-replicas 1
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 192.168.0.15:6379 to 192.168.0.11:6379
Adding replica 192.168.0.16:6379 to 192.168.0.12:6379
Adding replica 192.168.0.14:6379 to 192.168.0.13:6379
M: df088802a2430c1dcac46219d9cb7c1cedf2f5e9 192.168.0.11:6379
   slots:[0-5460] (5461 slots) master
M: 12a115cf87de3cf0eeb20f14d67ecab598bffd59 192.168.0.12:6379
   slots:[5461-10922] (5462 slots) master
M: 492cf9bcda264e58159d05a4ae32a1a9e83f19e3 192.168.0.13:6379
   slots:[10923-16383] (5461 slots) master
S: 27a2f943f0ceac9c3edd217f317eb78f907cd647 192.168.0.14:6379
   replicates 492cf9bcda264e58159d05a4ae32a1a9e83f19e3
S: adcc67715652e53b1788fc7bc31621b0951cd604 192.168.0.15:6379
   replicates df088802a2430c1dcac46219d9cb7c1cedf2f5e9
S: d28b3b7de3511c8af6a8ed3aa9e2fa949bcc80c0 192.168.0.16:6379
   replicates 12a115cf87de3cf0eeb20f14d67ecab598bffd59
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
......
>>> Performing Cluster Check (using node 192.168.0.11:6379)
M: df088802a2430c1dcac46219d9cb7c1cedf2f5e9 192.168.0.11:6379
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: 27a2f943f0ceac9c3edd217f317eb78f907cd647 192.168.0.14:6379
   slots: (0 slots) slave
   replicates 492cf9bcda264e58159d05a4ae32a1a9e83f19e3
S: d28b3b7de3511c8af6a8ed3aa9e2fa949bcc80c0 192.168.0.16:6379
   slots: (0 slots) slave
   replicates 12a115cf87de3cf0eeb20f14d67ecab598bffd59
M: 492cf9bcda264e58159d05a4ae32a1a9e83f19e3 192.168.0.13:6379
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
M: 12a115cf87de3cf0eeb20f14d67ecab598bffd59 192.168.0.12:6379
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
S: adcc67715652e53b1788fc7bc31621b0951cd604 192.168.0.15:6379
   slots: (0 slots) slave
   replicates df088802a2430c1dcac46219d9cb7c1cedf2f5e9
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.

# 启动redis客户端测试
/data # redis-cli -c
# set一个key ,可以发现缓存到了192.168.0.13的redis节点了.
192.168.0.12:6379> set a a 
-> Redirected to slot [15495] located at 192.168.0.13:6379
OK

# cluster nodes 查看集群的节点信息 ,发现192.168.0.13是redis-3,master节点
192.168.0.12:6379> cluster nodes
492cf9bcda264e58159d05a4ae32a1a9e83f19e3 192.168.0.13:6379@16379 master - 0 1615645849000 3 connected 10923-16383
12a115cf87de3cf0eeb20f14d67ecab598bffd59 192.168.0.12:6379@16379 myself,master - 0 1615645849000 2 connected 5461-10922
d28b3b7de3511c8af6a8ed3aa9e2fa949bcc80c0 192.168.0.16:6379@16379 slave 12a115cf87de3cf0eeb20f14d67ecab598bffd59 0 1615645850491 6 connected
27a2f943f0ceac9c3edd217f317eb78f907cd647 192.168.0.14:6379@16379 slave 492cf9bcda264e58159d05a4ae32a1a9e83f19e3 0 1615645849486 4 connected
df088802a2430c1dcac46219d9cb7c1cedf2f5e9 192.168.0.11:6379@16379 master - 0 1615645850000 1 connected 0-5460
adcc67715652e53b1788fc7bc31621b0951cd604 192.168.0.15:6379@16379 slave df088802a2430c1dcac46219d9cb7c1cedf2f5e9 0 1615645851498 5 connected

# 将redis-3节点挂掉,观察集群的变化
192.168.0.14:6379> cluster nodes 
adcc67715652e53b1788fc7bc31621b0951cd604 192.168.0.15:6379@16379 slave df088802a2430c1dcac46219d9cb7c1cedf2f5e9 0 1615646685973 5 connected

# 这里显示192.168.0.13状态为fail,已经挂掉了 
492cf9bcda264e58159d05a4ae32a1a9e83f19e3 192.168.0.13:6379@16379 master,fail - 1615646461939 1615646459000 3 connected
12a115cf87de3cf0eeb20f14d67ecab598bffd59 192.168.0.12:6379@16379 master - 0 1615646685000 2 connected 5461-10922
df088802a2430c1dcac46219d9cb7c1cedf2f5e9 192.168.0.11:6379@16379 master - 0 1615646686980 1 connected 0-5460

# 哨兵模式,192.168.0.14是192.168.0.13的从节点.192.168.0.14成为了master
27a2f943f0ceac9c3edd217f317eb78f907cd647 192.168.0.14:6379@16379 myself,master - 0 1615646687000 8 connected 10923-16383
d28b3b7de3511c8af6a8ed3aa9e2fa949bcc80c0 192.168.0.16:6379@16379 slave 12a115cf87de3cf0eeb20f14d67ecab598bffd59 0 1615646687987 6 connected

# 从缓存中取数据时是从192.168.0.14 redis节点中取的
127.0.0.1:6379> get a 
-> Redirected to slot [15495] located at 192.168.0.14:6379
"a"

```



docker run -p 6373:6379 -p 16373:16379 --name redis-3 -v /mydata/redis/node-3/data:/data -v /mydata/redis/node-3/conf/redis.conf:/etc/redis/redis.conf -d --net redis --ip 192.168.0.13 redis:5.0.9-alpine3.11 redis-server /etc/redis/redis.conf

​							

# Springboot项目打包Docker镜像

```bash
一、首先修改Linux系统的Docker配置文件 打开2375端口

  vim /usr/lib/systemd/system/docker.service
	
	在ExecStart=/usr/bin/dockerd 后面加上-H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock

重新加载配置文件和重新启动docker：
sudo systemctl daemon-reload 
sudo systemctl restart docker

```



1. 创建一个Springboot项目

```java

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello,docker and springboot";
    }
}

```

2.打包springboot项目 

![image-20210313233850995](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313233850995.png)

3.打完包,得到一个jar包

![image-20210313233936107](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313233936107.png)

4.编写Dockerfile文件

```bash
FROM java:8
COPY target/*.jar demo-0.0.1-SNAPSHOT.jar  # 拷贝文件或目录到镜像中
CMD ["server.port=8080"]  
ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]  # 容器启动时需要运行的命令
EXPOSE 8080   # 容器暴露的端口

```

5.idea配置Docker环境

![image-20210313235223216](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313235223216.png)

6.Dockerfile启动配置

![image-20210313235342833](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210313235342833.png)

7.配置docker启动参数

![image-20210314011326831](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210314011326831.png)

8.启动前需要执行的操作

![image-20210314011423325](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210314011423325.png)

![image-20210314011440430](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210314011440430.png)

9.点击运行

![image-20210314011727355](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210314011727355.png)

```bash
Deploying 'viper-docker-springboot-container-01 Dockerfile: Dockerfile'...
Building image...
Preparing build context archive...
[==================================================>]97/97 files
Done

Sending build context to Docker daemon...
[==================================================>] 15.37MB
Done

Step 1/5 : FROM java:8
 ---> d23bdf5b1b1b
Step 2/5 : COPY target/*.jar demo-0.0.1-SNAPSHOT.jar
 ---> ec53c4f1abd4
Step 3/5 : CMD ["server.port=8080"]
 ---> Running in b61998d36a06
Removing intermediate container b61998d36a06
 ---> f983b80bf855
Step 4/5 : ENTRYPOINT ["java","-jar","demo-0.0.1-SNAPSHOT.jar"]
 ---> Running in 659cd447be86
Removing intermediate container 659cd447be86
 ---> 3fbadc869493
Step 5/5 : EXPOSE 8080
 ---> Running in f8d756ca216a
Removing intermediate container f8d756ca216a
 ---> a392984e69c7

Successfully built a392984e69c7
Successfully tagged viper-docker-springboot-01:latest
Existing container found: 5149161265f3d60a943fc855c58279816efd41bfab283457138cd89f0af83529, removing...
Creating container...
Container Id: 083757a322297e8764bd5df9db91cbfaf0e36f2d130178166a37057fd3cef295
Container name: 'viper-docker-springboot-container-01'
Attaching to container 'viper-docker-springboot-container-01'...
Starting container 'viper-docker-springboot-container-01'
'viper-docker-springboot-container-01 Dockerfile: Dockerfile' has been deployed successfully.

```



10. 可以在idea控制台看到运行结果

    ![image-20210314011831887](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210314011831887.png)

# Docker问题解决

## 删除镜像问题

### 第一种:容器不存在删镜像

删除镜像时提示 No such image

```bash
[root@192 viper]# docker rmi -f centos
Error: No such image: centos  # 删除镜像提示镜像不存在
```

#### 解决

dockerd 镜像文件存放目录 

/var/lib/docker/image/overlay2/imagedb/content/sha256

进入该目录, rm -rf 需要删除的镜像即可

```bash
# 1. 进入保存镜像的目录
[root@192 sha256]# pwd
/var/lib/docker/image/overlay2/imagedb/content/sha256

# 2. rm -rf 删除镜像即可
[root@192 sha256]# rm -rf 300e315adb2f96afe5f0b2780b87f28ae95231fe3bdd1e16b9ba606307728f55

# 3. 查看删除结果
[root@192 sha256]# docker images 
REPOSITORY   TAG       IMAGE ID   CREATED   SIZE #可以看到docker没有删除的镜像了,表示删除成功

```

### 第二种:容器存在删镜像

镜像启动后的容器存在时,是不能删除镜像的

#### 解决步骤

- 停止运行容器 docker stop 容器id/容器名称
- 删除容器         docker rm 容器id/容器名称
- 删除镜像         docker rmi 容器id/容器名称:版本号

