## Auth-Platform

一个身份认证的仿真平台，主要模拟一些基于 ECC 的轻量认证过程，环境

- JDK 17
- JPBC 2.0.0
- Maven 3.9.1
- Springboot 3.0.2
- Thymeleaf 3.1.1
- Data JPA 3.0.2
- Spring Security 6.0.1
- Redis 3.0.1

准备实现的需求

1. Redis 做一个基于 IP 的黑名单
2. 考虑在注册和登陆的时候添加验证码
   - Spring Security 实现权限控制
   - MySQL 存储用户表、密钥表
3. 多线程认证测试
   - 对认证时间的测定
   - 用户可选择认证次数和线程个数（有限的选择）

## 部署

jpbc 导出需要配置 maven

```xml
<!-- JPBC 打包 -->
<dependency>
    <groupId>jpbc.api</groupId>
    <artifactId>api</artifactId>
    <version>2.0.0</version>
    <scope>system</scope>
    <systemPath>${pom.basedir}/lib/jpbc-api-2.0.0.jar</systemPath>
</dependency>

<dependency>
    <groupId>jpbc-plaf</groupId>
    <artifactId>plaf</artifactId>
    <version>2.0.0</version>
    <scope>system</scope>
    <systemPath>${pom.basedir}/lib/jpbc-plaf-2.0.0.jar</systemPath>
</dependency>
```

后台跑

```bash
nohup java -jar Auth-Platform.jar &
```

查看进程

```bash
ps -ef | grep java
```

根据进程号查询

```bash
netstat -anop | grep 3802588
```

根据端口查询状态

```bash
netstat -tuln | grep 8080
```

杀死进程

```bash
kill -9 3802588
```

防火墙配置，首先是控制台要打开，然后是 iptables 规则

```bash
sudo iptables -L -n
```

确保 8080 端口允许外部访问，若不允许用以下命令开放

```bash
sudo iptables -A INPUT -p tcp --dport 8080 -j ACCEPT
sudo iptables-save
```

最后检查 ufw

```bash
sudo ufw status
sudo ufw allow 8080/tcp
```

