-- ----------------------------
-- 选择数据库
-- ----------------------------
use base31;

-- ----------------------------
-- Table structure for video_records
-- ----------------------------
DROP TABLE IF EXISTS `video_records`;
CREATE TABLE `video_records`  (
     `id` int NOT NULL AUTO_INCREMENT,
     `input_video` varchar(255) DEFAULT NULL,
     `out_video` varchar(255) DEFAULT NULL,
     `username` varchar(255) NULL DEFAULT NULL,
     `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `conf` varchar(255) DEFAULT NULL,
     `weight` varchar(255) DEFAULT NULL,
     `kind` varchar(255) DEFAULT NULL,
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='视频检测记录表';

-- ----------------------------
-- Table structure for camera_records
-- ----------------------------
DROP TABLE IF EXISTS `camera_records`;
CREATE TABLE `camera_records`  (
      `id` int NOT NULL AUTO_INCREMENT,
      `weight` varchar(255) DEFAULT NULL,
      `conf` varchar(255) DEFAULT NULL,
      `username` varchar(255) DEFAULT NULL,
      `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `out_video` varchar(255) DEFAULT NULL,
      `kind` varchar(255) DEFAULT NULL,
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='摄像检测记录表';