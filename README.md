# giants-swagger
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.vencent-lu/giants-swagger/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.vencent-lu/giants-swagger)

扩展swagger功能，适配自定协议，使生成接口文档更易于理解，排除不必要的参数和返回值。

## 在项目中集成giants-swagger
引入maven 依赖，项目会自动加载 giants-swagger 相关配置类，maven依赖配置如下：
```xml
<dependency>
  <groupId>com.github.vencent-lu</groupId>
  <artifactId>giants-swagger</artifactId>
  <version>2.3.1</version>
</dependency>
```

## giants-swagger 配置说明
支持 yaml 与 properties 两种配置方式，以yml举例，配置说明如下：
```yaml
giants:
  swagger:
    # 返回结果封装对象
    return-result-class:
      # 结果对象类型
      type: com.giants.web.springmvc.json.JsonResult
      # 结果对象保存数据属性名称
      data-property: data
    # 忽略参数类型，入参 和 返回结果对象都会忽略
    ignore-parameter-types:
      - javax.servlet.http.HttpSession
      - javax.servlet.http.Cookie
    # 忽略请求参数类型，入参对象都会忽略
    ignore-request-parameter-types:
      - com.giants.auth.user.domain.LoginEmployee
      - javax.servlet.http.HttpSession
    # 忽略model中的属性名称列表
    ignore-model-property-names:
      - delete
      - createUserId
      - createTime
      - lastUpdateUserId
      - lastUpdateTime    
```