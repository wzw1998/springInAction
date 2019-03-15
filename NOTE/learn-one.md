# PowerCenter 应用架构
## 架构解析
- 数据源
- Domain 域
- 数据仓库 repository
- PowerCenter 服务端
- Informatica 客户端
- 目标数据库
- 管路员页面

从源数据库获取到的源数据，经过PowerCenter Server的处理，将转换的数据给到目标，其中来自元数据的指令将会存储到资料库 repository

## PowerCenter 产品组件
1. server 组件
  - Informatica Service：PowerCenter服务引擎
  - Integration Service：数据抽取、转换、装载服务
  - Repository Service：元数据资料库服务
2. client 组件
  - Administratortion Console：基于Web的管理控制台
  - Repository Manager：资料库内容管理客户端工具
  - Desinger：ETL映射流程设计客户端工具
  - Workflow Manager：ETL任务执行流程设计客户端工具
  - Workflow Monitor：ETL任务执行监控客户端工具
## 系统管理
1. Informatica Server管理
  - http://IP-Address:6008
  - Domain 域管理
  - Node 节点管理
  - Repository Service 资料库管理
  - Integration Service 集成服务管理
  - WebService Hub 管理
  - License 管理
2. 资料库内容管理
  -  Repository Manager
  - 目录管理
  - 权限管理
  - 版本管理

## 概念解析
- Source 源
- Target 目标
- Transformation 转换组件
- Mapplet 转换组件集
- Mapping 映射
  就是定义ETL处理过程：按照源系统数据和目标数据之间的映射关系，经过数据的抽取(Extraction)、转换(Transformation)和加载(Loading)等环节方可进入目标库
    - 从数据源读取数据
    - 定义数据清洗转换规则
    - 将数据装载到目标
- Task 任务
  可以执行的最小任务单元
    - Session 运行Mapping 的指令集；
    - Command 运行Shell Scripts 或者OS Command；
    - Email 发送邮件；
    - Control组件：如decision、Event等控制组件
- Connection 链接
- Worklet 任务集
- Workflow 工作流
  ETL工作流，使数据抽取可以基于时间、事件
    - 包含各种Task。
    - 可以配置各个Task的执行流程。
    - 可以配置执行计划Schedule。
- Scheduler 计划任务
  定时启动执行workflow的执行计划
    - 可以针对某一个workflow单独定制执行计划
    - 可以制作可重复利用的执行计划
    - 细化到年、月、星期、天、时、分等粒度。
- Monitor 监控
- 元数据：描述数据的数据，即关于某个数据的信息
## 开发步骤

Designer：

1. 定义源
2. 定义目标
3. 创建映射

Workflow Manager:

4. 定义任务
5. 创建工作流

Workflow Monitor:

6. 工作流调度监控
