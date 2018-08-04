## 简介
这是一个基于SpringBoot，结合MySQL、hibernate、JPA和JWT用户认证，提供RESTful API的博客后台。

## 主要功能
- Spring Security + JWT 权限控制
- 较为完整的用户权限管理（查看文章，管理文章，管理用户等）
- 文章的新建、修改、删除
- 随时可将文章加入、移出草稿箱（草稿箱的文章仅管理员及作者可见）
- 文章可以归纳到不同的分类、标签、专栏
- 分类、专栏、标签管理
- 文章可以存储markdown原文及HTML
- 搜索功能：根据关键字检索文章
- 文章归档
- 友链的增删查改
- 简易的文章评论

## 依赖
- JDK >= 1.8
- MySQL >= 5.5
- Maven >= 3.5

## 快速开始
1. **安装**
```
git clone git@github.com:staneyffer/BlogServer.git
cd BlogServer
mvn install
```
2. **创建数据库**
执行 ```src/main/resources/```下的```script.sql``` MySQL脚本文件

3. **修改配置文件**
本项目的属性配置文件默认是 ```src/main/resources/application.properties.example```，还需要将```application.properties.example```改成:```application.properties```，并将application.properties中对应的字段改成你自己的配置
(本项目中默认的数据库名字是: ```blog```, 如果需要修改请修改 ```src/main/resources/script.sql```及```src/main/resources/application.properties```两个文件中对应的配置)
4. **运行项目**
```mvn spring-boot:run```
