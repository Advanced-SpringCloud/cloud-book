## Spring Cloud 微服务架构进阶

> 《Spring Cloud 微服务架构进阶》，重点介绍微服务架构的Spring Cloud框架，在实践的基础上进行用法的拓展与相关组件的源码分析，包括：服务发现、负载均衡、断路、声明式HTTP调用客户端、网关、分布式配置中心、安全权限、消息总线等组件。本书适合Java开发人员，特别适合正在进行微服务化改造的开发人员、架构师，在改造过程中进行参考与进阶应用。 

本项目为《Spring Cloud 微服务架构进阶》各章节附录源码。我们为项目中每个章节涉及到的组件（redis、rabbitmq、consul等）提供了docker-compose一键启动，进入项目根目录运行即可。

**谢谢支持正版图书，购买链接：https://item.jd.com/12453340.html**

如遇刊印和错误，请加入读者群核实和校正，感谢您的支持。

### 12/11 更新
修复第八章，Hystrix过滤器使用过程产生的`com.netflix.hystrix.exception.HystrixRuntimeException: fallbackcmd failed and fallback failed.`

增加 `- RemoveRequestHeader=Origin`过滤器。


#### 加入读者交流群

![微信公众号](http://image.blueskykong.com/wechat-public-code.jpg)
