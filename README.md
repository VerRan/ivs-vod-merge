## 处理逻辑
### 触发逻辑
通过手机APP调用后端JAVA API，实现文件合并，APP传递要合并的文件地址

### 后端API实现
下拨API逻辑中调用MediaConvert API实现合并 ，同时记录jobId到数据库

### 合并任务完成通知：

通过Cloudwatcht 配置事件监听MediaConvert完成事件，然后通过触发的Lambda，调用后端回掉接口实现状态更新。
同时可以尝试java 异步客户端API

## 使用方法
1. 通过AWS 控制台的 MediaConvert 服务导入template目录下的模版
2. 创建MediaConvert所需角色具体方法[参考](https://github.com/aws-samples/aws-media-services-simple-vod-workflow/tree/master/1-IAMandS3)
2. 执行MediaConvertTest类


### 周五交付物
1. 架构图
2. json模版&java mdediaconvert demo&mediaconvert 配置（后端的API部署的 ak，sk配置增加media-convert权限 ，具体权限我提供）
3. Cloudwatch 配置，Lambda Python Demo （部署在VPC内，需和EKS所在VPC一致，调用内网的gateway）

