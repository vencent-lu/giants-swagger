# giants-swagger

[![Maven Central](https://img.shields.io/maven-central/v/com.github.vencent-lu/giants-swagger.svg?label=Maven%20Central)](https://central.sonatype.com/artifact/com.github.vencent-lu/giants-swagger)
[![License](https://img.shields.io/badge/license-Apache%202.0-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)
[![JDK](https://img.shields.io/badge/JDK-1.8%2B-orange.svg)](https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html)

扩展 [Springfox](https://springfox.github.io/springfox/) 功能，适配自定义协议，使生成的接口文档更易于理解，并排除不必要的参数和返回值。

## 功能特性

- **统一响应封装**：为所有接口返回值自动套上封装对象（如 `JsonResult`），业务数据置于指定属性（如 `data`）中，使文档与真实响应协议一致。
- **忽略参数类型（全局）**：指定类型在请求入参和返回结果中都不会出现。
- **忽略请求参数类型**：指定类型仅在请求入参中被忽略，避免对象被展开成一堆查询参数（如登录态对象）。
- **忽略模型属性**：从所有模型定义中移除指定名称的属性（如审计字段、逻辑删除标记）。
- **零侵入、自动装配**：基于 Spring Boot Starter，引入依赖即生效，无需改动业务代码。

## 环境要求

| 项目 | 版本 |
| --- | --- |
| JDK | 1.8+ |
| Spring Boot | 2.4.x |
| Springfox | 3.0.0（`springfox-boot-starter`） |

## 在项目中集成 giants-swagger

引入 maven 依赖，项目会通过 Spring Boot 自动装配机制加载 giants-swagger 相关配置类，maven 依赖配置如下：

```xml
<dependency>
  <groupId>com.github.vencent-lu</groupId>
  <artifactId>giants-swagger</artifactId>
  <version>2.3.2</version>
</dependency>
```

> giants-swagger 是对 Springfox 的增强，需保证项目中已启用 Springfox（存在可用的 `Docket` 配置）。

## giants-swagger 配置说明

支持 `yaml` 与 `properties` 两种配置方式，以 yml 举例，配置说明如下：

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

### 配置项速查

| 配置项 | 作用范围 | 匹配方式 | 典型用途 |
| --- | --- | --- | --- |
| `return-result-class` | 返回结果 | 类型 | 统一响应封装 |
| `ignore-parameter-types` | 入参 + 返回 | 类型 | 忽略框架级无关类型 |
| `ignore-request-parameter-types` | 仅入参 | 类型 | 忽略登录态等注入对象 |
| `ignore-model-property-names` | 所有模型 | 属性名 | 隐藏审计/内部字段 |

## 文档

完整的配置详解、工作原理、完整示例与常见问题，请参阅 **[用户使用手册](docs/用户使用手册.md)**。

## 许可协议

本项目基于 [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0) 开源。
