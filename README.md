# 基础框架

## 1. 后端配置

JAVA版本：jdk1.8.0_321

Maven版本：apache-maven-3.9.9

MySQL版本：mysql-5.7.27-winx64

可视化工具：SQLyog

Redis版本：Redis-x64-5.0.14.1

- 启动Redis：在安装的Redis目录下执行 `redis-server .\redis.windows.conf`

- 出现问题：Could not create server TCP listening socket 127.0.0.1:6379: bind: 操作成功完成。

  - 原因：6379端口已绑定。应该是因为上次服务没有关闭

  - 解决：依次执行下面命令

    ![image-20250514144458912](assets/image-20250514144458912.png)


## 2. 前端配置

Node版本：node-v16.20.2-x64