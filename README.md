## 简介
springboot-api-project-seed 是一个基于Spring Boot&MyBatis的种子项目，用于快速构建中小型RESTful API项目，减少重复编写，专注于业务代码，开箱即用。

## 特征&功能
- 统一响应结果封装
- 统一异常管理
- shiro整合 token认证及授权
- spring-data-redis整合
- 整合swagger-ui接口文档
- Druid数据库连接池
- Multiple Datasource多数据源切换
- xss过滤 sql注入拦截
- mybatis generator代码生成 
- 简单文件上传下载 图片预览API示例

## 开始&安装
`git clone git@github.com:AnthonyZero/springboot-api-project-seed.git`

`execute database sql`

`mvn clean install -DskipTests`

`import the project, update mysql connection properties`

`run Application.java`

* 访问swagger-ui：http://localhost:8860/seed/swagger-ui.html
* 用帐号test密码123456 进行登录接口测试 获取访问令牌token 后续测试文件相关请带上

## 建议&提醒
- 因为项目中使用了Lombok包，它是在编译的时期，自动生成get set方法，并不影响运行，如果觉得提示错误难受，请自行下载lombok包插件，[lombok官网]( https://www.projectlombok.org/)

- 在用mybatis generator生成代码的时候,推荐安装eclipse MBG插件使用，原因在于MBG默认是不支持Java代码合并的，只支持XML合并，当您使用Eclipse插件时, MBG可以自动合并 Java 文件
  
