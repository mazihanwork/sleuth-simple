# Spring Cloud Sleuth

Spring Cloud Sleuth是Spring Cloud系列用来进行分布式日志追踪的工具。它在Spring Boot和Spring Cloud系列相关的框架和组件里已经实现了分阶段追踪的功能，使用时只需要在配置文件（如application.yml）中设置即可。

# 单独使用

本工程演示了单独使用Sleuth的过程，非常简单易懂，引入自己的spring工程里也是一样的步骤。

### 1. 引入maven依赖

在自己的pom文件中添加依赖（必要）：

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
```

可以使用spring cloud的bom统一管理这系列的组件引用版本号（可选）：

```xml
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-dependencies</artifactId>
			<version>${spring-cloud.version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```
`${spring-cloud.version}`是当前使用的cloud版本，这里用的是`Finchley.RELEASE`。

### 2. 配置文件

在application.yml里指定应用的名称和端口号：

```yaml
spring:
  application:
    name: TDF-sleuth-simple
server:
  port: 3001
```

### 3. 记录日志

Sleuth可以追踪spring boot框架里的任意流程，此处以最简单的Controller为例，添加一个`SimpleController`类，定义`/`路径的方法：

```java
@RestController
public class SimpleController {

	private static Logger log = LoggerFactory.getLogger(SimpleController.class);
	
	@RequestMapping("/")
	public String home() {
		log.info("home get");
		return "home page";
	}
	
}
```

使用slf4j的Logger类记录日志，此条日志相关的追踪信息会由Sleuth自动生成并记录。

### 4. 运行查看

启动工程后，访问http://localhost:3001/ ，此时控制台输出日志：

```
2018-07-05 13:55:47.279  INFO [TDF-sleuth-simple,b96b89c7e70efa0c,b96b89c7e70efa0c,false] 13612 --- [nio-3001-exec-1] cn.com.taiji.sleuth.SimpleController     : home get
```

其中`[]`内是Sleuth生成的追踪信息，其含义分别是：

```
[appname,traceId,spanId,exportable]
```

`traceId`跟随整个请求流程，`spanId`则是具体某个步骤的标识。
`exportable`表示是否将此条日志输出给ZipKin服务器（如果有），本工程仅演示最单纯的Sleuth效果，不包含此项，所以为false。



