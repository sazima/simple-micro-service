# 
## 用户服务

- 用户登录
- 用户注册
- 用户基本信息查询
- 无状态, 无 session
- 单点登录


## 课程服务

- 登录验证
- 课程 curd

## 信息服务

- 发送邮件
- 发送短信

## 用户 edge-service
## 课程 edge-service
## API GATEWAY

***
# sql:

```sql
CREATE DATABASE db_course DEFAULT CHARSET utf8mb4;
use db_course;
CREATE TABLE `pe_course` (
  `id` int(11) NOT NULL,
  `title` varchar(64) NOT NULL,
  `description` varchar(125) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `pe_user_course` (
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `pe_course`
  ADD PRIMARY KEY (`id`);
ALTER TABLE `pe_course`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

----------------------
CREATE DATABASE db_user DEFAULT CHARSET utf8mb4;
use db_user;

CREATE TABLE `pe_teacher` (
  `user_id` int(11) NOT NULL,
  `intro` varchar(64) DEFAULT NULL,
  `description` varchar(125) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `pe_user` (
  `id` int(10) UNSIGNED NOT NULL,
  `username` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL,
  `real_name` varchar(32) DEFAULT NULL,
  `mobile` varchar(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE `pe_user`
  ADD PRIMARY KEY (`id`);

ALTER TABLE `pe_user`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
COMMIT;

```


# ports-list

```js
user-thrift-service 7911
user-edge-service 8080
message-thrift-service 19090
// course-service 8089
course-edge-service 8081
api-gatway-zuul  8889
```
