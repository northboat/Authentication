# Auth-Platform

## 环境

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

## 编码



## 部署

### 导出

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

后台运行

```bash
nohup java -jar Auth-Platform-0.0.1-SNAPSHOT.jar &
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

### 防火墙

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

### HTTPS

其实是因为我打算从博客里请求`/image`接口去动态的展示随机图片，这个接口从数据库表中随机取一个图片路径，返回图片的二进制流，非常简单捏

但是 GitHub Page 的默认域名，即`xxx.github.io`强制使用 HTTPS，即我直接在服务器上部署 jar 包（HTTP），他会打不过去，从而加载不了图片

所以需要配置 HTTPS，又因为我没域名，所以只能用自生成的证书，步骤如下

服务器上生成证书

```sh
keytool -genkeypair -alias springboot -keyalg RSA -keysize 2048 \
        -dname "CN=localhost, OU=MyOrg, O=MyCompany, L=City, ST=State, C=US" \
        -validity 365 -keystore keystore.p12 -storetype PKCS12 \
        -storepass 123456
```

会在当前目录得到一个 PKCS12 格式的证书 keystore.p12，将这个证书拷入 resources 目录，对 SpringBoot 配置

```yaml
server:
  port: 8443
  ssl:
    key-store: classpath:keystore.p12
    key-store-password: "123456"
    key-store-type: PKCS12
    key-alias: springboot
```

打包上传部署，通过`ip:8443`进行访问，成功，经测试 GitHub Page 同样允许自编译的 SSL 证书
