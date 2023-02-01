## cheese-cli-- 快速搭建springweb的服务demo
### 一、cheese-cli-springboot
#### 工程结构

```markdown
cheese-cli-springboot
├── common -- 定义全局使用的缓存、属性配置、常量维护、系统工具等
├    ├── constant -- 全局常量包
├    ├── tool -- 全局工具包
├── core -- 模块集成核心包
├    ├── executor -- 线程模块
├    ├── log -- 日志模块
├    ├── secure -- 安全模块，集成springSecurity
├    ├── workflow -- 工作流模块，集成Activity7，提供常用的流程处理操作
├    ├── metadata -- 元数据模块，集成elasticsearch，对系统中的原始数据进行资源管理
├    ├── oss -- 对象存储模块，集成Qiniu云
├         ├── qiniu -- 集成qiniu云
├         ├── aliyun -- 集成aliyun
├         ......
├    ├── ops -- 运维中心
├         ├── admin -- 集成springAdmin，提供后台管理功能
├         ├── codenav -- 集成cheese-util-codenav，提供代码生成功能
├         ......
├── modules -- 业务模块
├    ├── system -- 系统业务
├    ......
├──  └── borrow -- 借阅业务
```
****

- tip:路漫漫其修远兮，剩余的工作还有很多(截止20230201，提交了安全模块与工作流模块)

### 二、cheese-cli-springcloud