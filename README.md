# ECC-Auth-Platform

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
