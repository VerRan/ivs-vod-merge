## 更新记录
### 2021-1-21 日添加校验m3u8文件时长为0导致mediaConvert 转换异常修复。
1. 测试方法 执行MediaConvertTest类 checkFile方法
2. 对应逻辑通过S3Service类实现


## 处理逻辑
### 触发逻辑
通过手机APP调用后端JAVA API，实现文件合并，APP传递要合并的文件地址

### 后端API实现
下拨API逻辑中调用MediaConvert API实现合并 ，同时记录jobId到数据库，通过回掉函数实现状态更新，展示点播列表

## 使用方法
1. 通过AWS 控制台的 MediaConvert 服务导入template目录下的模版
2. 创建MediaConvert所需角色具体方法[参考](https://github.com/aws-samples/aws-media-services-simple-vod-workflow/tree/master/1-IAMandS3)
3. 执行MediaConvertTest类,该类提供了异步调用，通过回掉函数实现当任务执行完成后的服务调用
4. 
