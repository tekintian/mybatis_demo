#Mybatis Demo with Gradle

#数据库用户名创建与授权
数据库名称：mybatis_demo
用户名：mybatis
密码：888888

```sql

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名称',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 27 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '昆明王五', NULL, '2', '云南省');
INSERT INTO `user` VALUES (10, '张三', '2014-07-10', '1', '北京市');
INSERT INTO `user` VALUES (16, '张小明', NULL, '1', '云南省昆明');
INSERT INTO `user` VALUES (22, '陈小明', NULL, '1', '云南省昆明');
INSERT INTO `user` VALUES (24, '张三丰', NULL, '1', '四川成都');
INSERT INTO `user` VALUES (25, '陈小明', NULL, '1', '云南省昆明');
INSERT INTO `user` VALUES (26, '深圳王五', NULL, NULL, '广东深圳');



-- ----------------------------
-- create database user mybatis and 
-- grant all privilege to mybatis_demo database on localhost
-- ----------------------------
CREATE USER `mybatis`@`localhost` IDENTIFIED BY '888888';

GRANT Alter, Alter Routine, Create, Create Routine, Create Temporary Tables, Create View, Delete, Drop, Event, Execute, Grant Option, Index, Insert, Lock Tables, References, Select, Show View, Trigger, Update ON `mybatis\_demo`.* TO `mybatis`@`localhost`;
```
---
#mybatis数据库连接参数配置注意事项

- characterEncoding=utf-8 设置与数据库通讯所使用的编码；
- useSSL=false 
```text
高版本的MYSQL必须添加，否则无法连接，注意连接符 & 需要使用实体模式，即：&amp;
```

- characterEncoding=UTF-8 指定与数据库通讯使用的编码。

- useUnicode=true 如果你的数据库的编码非utf-8则需要指定此参数

   例如：mysql数据库用的是gbk编码，而项目数据库用的是utf-8编码。这时候如果添加了useUnicode=true&characterEncoding=UTF-8 ，那么作用有如下两个方面：
    
    1. 存数据时：数据库在存放项目数据的时候会先用UTF-8格式将数据解码成字节码，然后再将解码后的字节码重新使用GBK编码存放到数据库中。
    
    2. 取数据时：在从数据库中取数据的时候，数据库会先将数据库中的数据按GBK格式解码成字节码，然后再将解码后的字节码重新按UTF-8格式编码数据，最后再将数据返回给客户端。

---
完整的连接地址：
```text
jdbc:mysql://localhost:3306/mybatis_demo?characterEncoding=utf-8&amp;useSSL=false
```



