# 同步任务 API 文档

## 接口设计说明

### DTO 使用规范
所有接口都使用专门的 DTO 类来接收前端数据，而不是使用 `record` 或内部类：

- **CreateTaskDTO**: 创建任务时使用
- **SaveOrUpdateTaskDTO**: 保存或更新任务时使用  
- **UpdateVersionDTO**: 更新版本时使用
- **RenderConfigDTO**: 渲染配置时使用

### DTO 的优势
1. **类型安全**: 编译时检查，避免运行时错误
2. **文档生成**: 支持 Swagger 自动生成 API 文档
3. **验证支持**: 可以添加 Bean Validation 注解
4. **可维护性**: 集中管理请求参数，便于修改和扩展
5. **代码复用**: DTO 可以在多个地方复用

## saveOrUpdateTask 接口

### 接口描述
保存或更新同步任务，支持创建新任务和更新现有任务。

### 请求方式
POST

### 请求路径
`/api/integration/task/saveOrUpdate`

### 请求参数
```json
{
  "id": 1,                              // 任务ID（更新时必填，新建时不填）
  "taskCode": "TASK_001",               // 任务编码（可选，不填会自动生成）
  "taskName": "ES到本地文件同步任务",      // 任务名称（必填）
  "description": "同步ES数据到本地文件",   // 任务描述
  "sourceDatasourceId": 1,              // 源数据源ID
  "sinkDatasourceId": 2,                // 目标数据源ID
  "sourceType": "elasticsearch",        // 源端类型
  "sinkType": "LocalFile",              // 目标端类型
  "jobMode": "BATCH",                   // 作业模式：BATCH/STREAMING
  "parallelism": 4,                     // 并行度
  "checkpointIntervalMs": 300000,       // 检查点间隔(毫秒)
  "scheduleMode": "MANUAL",             // 调度模式：MANUAL/CRON
  "cronExpression": "0 0 2 * * ?",      // CRON表达式（调度模式为CRON时必填）
  "timezone": "Asia/Shanghai",          // 时区
  "status": "ACTIVE",                   // 状态：ACTIVE/DISABLED/DRAFT/ARCHIVED
  "tags": "[\"prod\",\"deptA\"]",       // 标签(JSON字符串)
  "env": {                              // 环境配置JSON
    "jobMode": "BATCH",
    "parallelism": 4,
    "checkpointInterval": 300000
  },
  "source": {                           // 源端配置JSON
    "type": "elasticsearch",
    "hosts": ["localhost:9200"],
    "index": "test_index"
  },
  "transforms": [],                     // 转换配置JSON（可选）
  "sink": {                             // 目标端配置JSON
    "type": "LocalFile",
    "path": "/tmp/output",
    "format": "json"
  },
  "renderedText": "...",                // 渲染后的配置文本
  "renderedJson": {},                   // 渲染后的配置JSON
  "switchToCurrent": true,              // 是否切换为当前版本（更新时有效）
  "changeMemo": "初始版本"               // 变更备注
}
```

### 响应结果
```json
{
  "code": 200,
  "message": "操作成功",
  "data": 1                             // 返回任务ID
}
```

### 业务逻辑
1. **新建任务**（id 为空）：
   - 创建新的任务记录
   - 初始化版本号为 1
   - 如果未提供 taskCode，自动生成
   - 检查任务编码是否重复
   - 默认状态为 DRAFT

2. **更新任务**（id 不为空）：
   - 检查任务是否存在
   - 更新任务基本信息
   - 创建新版本记录
   - 可选择是否切换为当前版本

### 错误码
- 413: 任务不存在 (INTEGRATION_TASK_NOT_FOUND)
- 414: 任务编码已存在 (INTEGRATION_TASK_CODE_EXISTS)
- 415: 版本不存在 (INTEGRATION_VERSION_NOT_FOUND)
- 416: 配置渲染失败 (INTEGRATION_CONFIG_RENDER_FAILED)

### 异常处理优化
- 移除了前端应该处理的基础验证（如任务名称为空）
- 使用预定义的异常方法，提供更清晰的错误信息
- 简化了异常抛出逻辑，提高代码可读性

## getTaskDetail 接口

### 接口描述
获取任务详情

### 请求方式
GET

### 请求路径
`/api/integration/task/detail/{id}`

### 路径参数
- id: 任务ID

### 响应结果
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "taskCode": "TASK_001",
    "taskName": "ES到本地文件同步任务",
    "description": "同步ES数据到本地文件",
    "sourceDatasourceId": 1,
    "sinkDatasourceId": 2,
    "sourceType": "elasticsearch",
    "sinkType": "LocalFile",
    "jobMode": "BATCH",
    "parallelism": 4,
    "checkpointIntervalMs": 300000,
    "scheduleMode": "MANUAL",
    "cronExpression": null,
    "timezone": "Asia/Shanghai",
    "status": "ACTIVE",
    "currentVersionNo": 1,
    "tags": "[\"prod\",\"deptA\"]",
    "createTime": "2025-08-13T10:00:00",
    "updateTime": "2025-08-13T10:00:00"
  }
}
```

## 数据库表结构

### ubsp_di_sync_task（同步任务主表）
- id: 主键
- task_code: 任务编码
- task_name: 任务名称
- description: 任务描述
- source_datasource_id: 源数据源ID
- sink_datasource_id: 目标数据源ID
- source_type: 源端类型
- sink_type: 目标端类型
- job_mode: 作业模式
- parallelism: 并行度
- checkpoint_interval_ms: 检查点间隔
- schedule_mode: 调度模式
- cron_expression: CRON表达式
- timezone: 时区
- status: 状态
- current_version_no: 当前版本号
- tags: 标签
- create_time: 创建时间
- update_time: 更新时间
- is_deleted: 删除标记

### ubsp_di_sync_task_version（任务版本表）
- id: 主键
- task_id: 任务ID
- version_no: 版本号
- env_json: 环境配置JSON
- source_json: 源端配置JSON
- transforms_json: 转换配置JSON
- sink_json: 目标端配置JSON
- rendered_text: 渲染后的配置文本
- rendered_json: 渲染后的配置JSON
- change_memo: 变更备注
- create_time: 创建时间

## 其他接口

### createTask 接口
**请求方式**: POST  
**请求路径**: `/api/integration/task/create`  
**请求体**: `CreateTaskDTO`  
**响应**: 返回任务ID

### createVersion 接口
**请求方式**: POST  
**请求路径**: `/api/integration/task/version/create`  
**请求体**: `UpdateVersionDTO`  
**响应**: 返回版本号

### renderConfig 接口
**请求方式**: POST  
**请求路径**: `/api/integration/task/render`  
**请求体**: `RenderConfigDTO`  
**响应**: 返回渲染后的配置文本

## 异常处理说明

### 异常处理方式
项目统一使用 ResultCodeEnum 管理错误码，直接抛出 UBSPException：

```java
// 任务不存在
throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_NOT_FOUND);

// 任务编码已存在  
throw new UBSPException(ResultCodeEnum.INTEGRATION_TASK_CODE_EXISTS);

// 版本不存在
throw new UBSPException(ResultCodeEnum.INTEGRATION_VERSION_NOT_FOUND);

// 配置渲染失败
throw new UBSPException(ResultCodeEnum.INTEGRATION_CONFIG_RENDER_FAILED);
```

### ResultCodeEnum 数据集成错误码
```java
INTEGRATION_TASK_NOT_FOUND(413, "任务不存在"),
INTEGRATION_TASK_CODE_EXISTS(414, "任务编码已存在"),
INTEGRATION_VERSION_NOT_FOUND(415, "版本不存在"),
INTEGRATION_CONFIG_RENDER_FAILED(416, "配置渲染失败"),
```

### 验证策略
- **前端验证**：基础字段验证（必填、格式等）由前端处理
- **后端验证**：业务逻辑验证（唯一性、存在性等）由后端处理
- **异常统一**：直接使用 ResultCodeEnum 中定义的错误码抛出 UBSPException
- **错误码规范**：数据集成相关错误码统一使用 41x 系列
- **代码简洁**：不在异常类中定义静态方法，保持异常类的简洁性