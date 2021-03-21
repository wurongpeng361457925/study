# Git

Linux Torvalds邮箱：

torvalds@linux-foundation.org

linux内核源码版本控制工具

分布式版本管理工具：

- tarball

- BitKeeper  

​	商业化，分布式的版本控制工具 ，bitkeeper允许Linux的版本管理可以在bitkeeper上免费使用，条件是	    	Linux Torvalds不能做反向工程，也不能做比他keeper的竞争产品。（2004-2007 ？）

- Mercurial

性能

分布式

代码原样

[离线工作]

## Git

做了两年多，编码和设计

可靠，高效，分布的版本控制工具

重点是分布式的。

为什么分布式这么重要？

​	版本控制，SCM： Source Code Management

​	分布式的含义：

- 不用一个集中地点取追踪数据
- 没有哪个地方是比其他地方更重要的（每个地点都是平等的）



提交代码权限的问题

​			分布式环境

​					每个人都有自己的分支（一个或多个？） branch

​			集中式版本控制

​				“他大概不是一个蠢货吧”的阶层 ---> 可以提交代码到代码仓库

分布式对任何代码控制工具SCM都是核心问题

Git默认分支 master  （GitLib前段时间把Master改名为Main）

## Git性能问题

Git追踪的是你的内容，它不会追踪单个的文件。在Git中不能追踪一个文件的

Git不会把项目中的所有文件视为一个个的文件，它会把整个项目看作一个完整的内容，当作一组文件来追踪

 所有Git的历史是基于整个项目内容的历史，这对Git的性能是有深渊影响的

## 项目中的共用代码

Git内部用的是**内容可寻址的文件系统**。对于内容完全相同的文件，Git会让它们使用同一个对象

这样可以节省大量的存储空间，还能让它们作为不同的实体

SHA-1 最好的Hash，Git中用来保证一致性 （信任你的数据的问题）

# 版本控制

## 概念

是一种记录一个或者若干文件内容的变化，以便以后查阅特定版本修改情况的系统。可以对任何类型的文件进行版本控制

## 版本控制分类

- 本地版本控制

  - RCS 	在硬盘上保存补丁集（文件修改前后的变化），通过应用所有补丁集，可以重新计算出各个版本的文件内容

- 集中化版本控制  CVCS Centralized Version Control Systems

  - 有一个集中管理的服务器，保存所有文件的修订版本。coder通过Client端连接Server端，进行拉取和push操作
  - CVS，Subversion，Perforce等
  - 缺点：server挂掉的话，无法进行更新和push操作。server的磁盘损坏的话，会丢失项目的所有数据，包括变更历史（除了Client端的项目快照）

- 分布式版本控制 DVCS Distributed Version Control System

  - Client端每次的clone操作，都是一次对代码仓库的完整备份。若server发生故障，可以从任意的Client端的镜像进行恢复。
  - Git , Mercurial,Bazaar，Darcs

  # 	Git简史

同生活中的许多伟大事物一样，Git 诞生于一个极富纷争大举创新的年代。

Linux 内核开源项目有着为数众广的参与者。 绝大多数的 Linux 内核维护工作都花在了提交补丁和保存归档的繁琐事务上（1991－2002年间）。 到 2002 年，整个项目组开始启用一个专有的分布式版本控制系统 BitKeeper 来管理和维护代码。

到了 2005 年，开发 BitKeeper 的商业公司同 Linux 内核开源社区的合作关系结束，他们收回了 Linux 内核社区免费使用 BitKeeper 的权力。 这就迫使 Linux 开源社区（特别是 Linux 的缔造者 Linus Torvalds）基于使用 BitKeeper 时的经验教训，开发出自己的版本系统。 他们对新的系统制订了若干目标：

- 速度
- 简单的设计
- 对非线性开发模式的强力支持（允许成千上万个并行开发的分支）
- 完全分布式
- 有能力高效管理类似 Linux 内核一样的超大规模项目（速度和数据量）

自诞生于 2005 年以来，Git 日臻成熟完善，在高度易用的同时，仍然保留着初期设定的目标。 它的速度飞快，极其适合管理大项目，有着令人难以置信的非线性分支管理系统（参见 Git 分支）。

Checksum 校验和

## Git管理文件的三种状态

- 工作区
  - 已修改 modified  Changes not staged for commit 
- 暂存区 
  - 已暂存 staged   Changes to be committed
- 本地仓库
  - 已提交 committed



## Git的配置文件

三个位置  Linux上

- /etc/gitconfig   
  - 系统上每一个用户以及各个用户的仓库配置
- ~/.gitconfig 或 ~/.config/git/config    
  - 只针对当前用户。可以传递 -global选项让git读写此配置文件
- .git/config 当前使用仓库的config文件
  - 针对该仓库的配置

每一个级别覆盖上一个级别的配置。 如 .git/config 会覆盖 /etc/gitconfig 的配置

​							

当前仓库配置   ------覆盖-----> 当前用户配置 -----覆盖----> 系统配置



## 配置用户信息

安装完git，先设置user和Email。Git的每次提交都会使用这些信息，将这些信息写入每次的提交中。在仓库中此信息用来区分代码提交者

```bash
# git config --global user.name 'xxx'   配置用户信息
# git config --global user.email 'xxx@xxx.com'
git config --global user.name "viper"
git config --global user.email "wyp3614@163.com"
```

查看配置信息

```bash
# git config --list 查看配置信息
[root@192 .git]# git config --list
user.name=viper
user.email=wyp3614@163.com
core.repositoryformatversion=0
core.filemode=true
core.bare=false
core.logallrefupdates=true

# 可能会看到重复的变量名。因为Git从不同位置的配置文件读取同一个配置（1./etc/gitconfig 2.~/.gitconfig 3. ./git/config）。使用它找到的每个变量的最后一个配置


# git config key 查看特定的配置
[root@192 .git]# git config user.name
viper
[root@192 .git]# git config user.email
wyp3614@163.com

```

## Git基本操作

```bash
# git init 初始化本地仓库, .git目录下的文件即目录
branches  config  description  HEAD  hooks  info  objects  refs

# git status 查看工作区状态
$ git status
On branch master
Untracked files:   # 未被跟踪的文件
  (use "git add <file>..." to include in what will be committed)

        test.txt

nothing added to commit but untracked files present (use "git add" to track)
# 没有添加任何要提交的内容，但存在未跟踪的文件 (使用“git add”来跟踪)

# git add 文件名  暂存操作会为该文件计算校验和（SHA-1 哈希算法），暂存区保存的是该'校验和'
$ git add test.txt
 # Windows中,CRLF表示一行的结束.Linux/Unix中,LF表示一行的结束。默认将入库的文件威航结束符设置为LF，检出文件时文件尾行设置为CRLF。Windows系统下，文件添加到工作区时会有该提示
warning: LF will be replaced by CRLF in test.txt. 
The file will have its original line endings in your working directory
# 在您的工作目录中，该文件将有其原始的行结尾
	# git add 三种作用:
		1. 跟踪新文件
		2. 把已跟踪的文件放到暂存区
		3. 合并时,把有冲突的文件标记为'已解决' 状态
		
# 修改 暂存区的文件，查看状态
$ git status
On branch master
Changes to be committed:  # 已暂存
  (use "git reset HEAD <file>..." to unstage) # 放弃跟踪文件

        new file:   test.txt

Changes not staged for commit: # 已更改未暂存
  (use "git add <file>..." to update what will be committed) # 更新将要提交的内容
  (use "git checkout -- <file>..." to discard changes in working directory) # 丢弃工作目录中的更改

        modified:   test.txt
        
# git rm --cache 文件名  从暂存区删除文件
$ git rm --cache checkout.txt
rm 'checkout.txt'  # 从暂存区删除的文件为'未跟踪'状态,不被git管理


$ git status -s
 M reset.txt   # 右M：被修改未暂存
M  test.txt	   # 左M：被修改已暂存
A  test3.txt   # A ： 新添加到暂存区的文件
AM test4.txt   # AM：新加入的文件被修改未暂存
?? test2.txt   # ??: 未被追踪的文件
MM test5.txt   # MM：在工作区被修改并提交到暂存区，又在工作区被修改。暂存区和工作区都有该文件的被修改记录


# git reset --hard commitID   恢复指定的提交记录 且 覆盖暂存区和工作区，commitID之后的提交记录也会被删除
$ git reset --hard 8bb3a01b4c3d767bfd5a8afae930943b2d21a8e0
HEAD is now at 8bb3a01 第二次提交

# git checkout -- 文件名   丢弃工作目录中的更改

```

## Git分支

分支branch概念

Git保存的是一系列不同时刻的快照 Snapshot

Git分支的本质是一个指向**提交对象**的指针。

分支与分支  在开发过程中要没有任何联系的。在切换分支前要保证当前分支的工作区和暂存区是干净的。

- 首次提交 
  - 产生的提交对象没有父对象
- 普通提交
  - 产生的提交对象有一个父对象
- 多分支合并
  - 产生的提交对象有多个父对象

```bash
# git branch 查看所有分支
$ git branch
  dev      # 其他分支
* master   # 当前所在的分支

# git checkout 分支名称  切换分支  切换分支会导致未提交的内容出现在切换后的分支，所以，切换分支前要保证当前工作区和暂存区是干净的
$ git checkout dev
Switched to branch 'dev'
 
# git merge  要合并的分支名称  合并分支
$ git merge dev  # 把dev分支合并到当前分支上
Updating 8bb3a01..e58d1f6
Fast-forward
 dev.txt | 0
 1 file changed, 0 insertions(+), 0 deletions(-)
 create mode 100644 dev.txt

# git branch -d 分支名  删除已经与主分支合并过的分支
$ git branch -d dev  # dev分支commit的内容已经合并到master分支上了，可以被删除
Deleted branch dev (was e58d1f6).

# 分支上commit的内容没有合并到当前分支，会删除失败
$ git branch -d test
error: The branch 'test' is not fully merged.  # 分支 test 没有完全合并
If you are sure you want to delete it, run 'git branch -D test'.

# git branch -D 分支名 强制删除分支，被强制删除的分支上未合并的内容也会被删除

```

暂时保存修改

```bash
# git stash  把当前分支的修改先暂存起来，当前分支会初始化到刚创建分支的状态
$ git stash
	#保存的工作目录和索引状态WIP在dev上
Saved working directory and index state WIP on dev: 5d33975 主分支第一次提交

# git stash pop  恢复改动
$ git stash pop
On branch dev
Changes to be committed:
  (use "git reset HEAD <file>..." to unstage)

        new file:   dev.txt

Dropped refs/stash@{0} (7872d5ff32f92981434300b34adead6edb212a16)


```

## 远程仓库操作

```bash
# git remote add 名称 git@github.com:wurongpeng361457925/test.git   给远程仓库起个别名
$ git remote add origin git@github.com:wurongpeng361457925/test.git

# git push 远程别名 远程分支  使用远程仓库的别名推送内容到远程仓库
$ git push origin master
Enumerating objects: 5, done.
Counting objects: 100% (5/5), done.
Delta compression using up to 8 threads
Compressing objects: 100% (3/3), done.
Writing objects: 100% (3/3), 370 bytes | 370.00 KiB/s, done.
Total 3 (delta 0), reused 0 (delta 0)
To github.com:wurongpeng361457925/test.git
   99464df..2f91b78  master -> master

# git push -u origin master  让git记住本次提交的远程仓库地址和远程分支，下次提交直接 git push 就可以了 
# git push   经过 -u之后的提交，直接git push就可以提交了 

# git fetch origin dev 从远程地址origin拉取dev到本地  origin: 远程地址别名

# git merge 合并

# git pull origin dev 从远程地址origin拉取dev分支   等于 git fetch + merge

# rebase 变基 ，可以线性的看到每一次提交，并且没有增加提交节点。不过也有些项目，不建议使用rebase, 这就得看公司与项目的规定
# git rebase origin/dev    origin/dev是从远程fetch的分支，合并到当前分支。rebase命令与pull命令不同，这里使用rebase不会产生分叉

# 如果 git rebase 产生冲突的话
# 先解决冲突
# 再 git rebase --continue


# git remote show origin 显示远程分支信息
$ git remote show origin
* remote origin
  Fetch URL: git@github.com:wurongpeng361457925/test.git
  Push  URL: git@github.com:wurongpeng361457925/test.git
  HEAD branch: master
  Remote branch:
    master tracked
  Local branch configured for 'git pull':
    master merges with remote master
  Local ref configured for 'git push':
    master pushes to master (up to date)

```

GitHub添加合作者

![image-20210321174818215](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210321174818215.png)

输入合作者的username，全名，邮箱 之一

![image-20210321174930275](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210321174930275.png)

复制仓库链接发给合作人

![image-20210321175236760](C:\Users\alienware\AppData\Roaming\Typora\typora-user-images\image-20210321175236760.png)

Git日志

```bash
# git log 查看日志

# git log --graph  以图形化方式显示日志

# git log --graph --pretty=format:"%h %s" 简化的图形化 方式显示日志
$ git log --graph --pretty=format:"%h %s"
*   dc96440 conflict2
|\
| *   f3b690e conflact
| |\
| * | 0523432 CentOS2
* | | 4a02180 windows2
| |/
|/|
* | 71a1979 windows修改
|/
* a28c3cf CentOS
* 4e4e2ad B第二次提交
* 0dded0c 第四次提交
* 9443340 第三次提交
* 2f91b78 给远程仓库地址起别名
* 99464df dev第一次提交
* 5d33975 主分支第一次提交

```

