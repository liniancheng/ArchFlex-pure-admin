-- ----------------------------
-- 选择数据库
-- ----------------------------
use arch-flex;

-- ----------------------------
-- Table structure for detect_img_records
-- ----------------------------
DROP TABLE IF EXISTS `detect_img_records`;
CREATE TABLE `detect_img_records`  (
   `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
   `input_img` varchar(255) NULL DEFAULT NULL COMMENT '输入图片路径',
   `out_img` varchar(255) NULL DEFAULT NULL COMMENT '输出图片路径',
   `confidence` varchar(255) NULL DEFAULT NULL COMMENT '置信度',
   `all_time` varchar(255) NULL DEFAULT NULL COMMENT '总耗时',
   `conf` varchar(255) NULL DEFAULT NULL COMMENT '最小置信度阈值',
   `weight` varchar(255) NULL DEFAULT NULL COMMENT '权重文件',
   `username` varchar(255) NULL DEFAULT NULL COMMENT '用户名',
   `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
   `label` varchar(255) NULL DEFAULT NULL COMMENT '标签',
   `kind` varchar(255) NULL DEFAULT NULL COMMENT '检测种类',
   `del_flag` int(11) COMMENT '是否删除 1-删除，0-未删除' DEFAULT '0',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='图片检测记录表';

-- ----------------------------
-- Table structure for detect_video_records
-- ----------------------------
DROP TABLE IF EXISTS `detect_video_records`;
CREATE TABLE `detect_video_records`  (
     `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
     `input_video` varchar(255) DEFAULT NULL COMMENT '输入视频路径',
     `out_video` varchar(255) DEFAULT NULL COMMENT '输出视频路径',,
     `username` varchar(255) NULL DEFAULT NULL COMMENT '用户名',
     `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
     `conf` varchar(255) DEFAULT NULL COMMENT '最小置信度阈值',
     `weight` varchar(255) DEFAULT NULL COMMENT '权重文件',
     `kind` varchar(255) DEFAULT NULL COMMENT '检测种类',
     `del_flag` int(11) COMMENT '是否删除 1-删除，0-未删除' DEFAULT '0',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='视频检测记录表';

-- ----------------------------
-- Table structure for detect_camera_records
-- ----------------------------
DROP TABLE IF EXISTS `detect_camera_records`;
CREATE TABLE `detect_camera_records`  (
      `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
      `weight` varchar(255) DEFAULT NULL COMMENT '权重文件',
      `conf` varchar(255) DEFAULT NULL COMMENT '最小置信度阈值',
      `username` varchar(255) DEFAULT NULL COMMENT '用户名',
      `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始时间',
      `out_video` varchar(255) DEFAULT NULL COMMENT '输出视频路径',,
      `kind` varchar(255) DEFAULT NULL COMMENT '检测种类',
      `del_flag` int(11) COMMENT '是否删除 1-删除，0-未删除' DEFAULT '0',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='摄像检测记录表';

-- ----------------------------
-- Table structure for detect_models
-- ----------------------------
DROP TABLE IF EXISTS `detect_models`;
CREATE TABLE `detect_models`  (
      `id` int NOT NULL AUTO_INCREMENT COMMENT '主键ID',
      `weight` varchar(255) DEFAULT NULL COMMENT '权重文件',
      `username` varchar(255) DEFAULT NULL COMMENT '用户名',
      `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
      `kind` varchar(255) DEFAULT NULL COMMENT '检测种类',
      `del_flag` int(11) COMMENT '是否删除 1-删除，0-未删除' DEFAULT '0',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='模型表';