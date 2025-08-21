-- ----------------------------
-- 选择数据库
-- ----------------------------
use arch-flex;

-- ----------------------------
-- Table structure for detect_img_records
-- ----------------------------
DROP TABLE IF EXISTS `detect_img_records`;
CREATE TABLE `detect_img_records`  (
   `id` int NOT NULL AUTO_INCREMENT,
   `input_img` varchar(255) NULL DEFAULT NULL,
   `out_img` varchar(255) NULL DEFAULT NULL,
   `confidence` varchar(255) NULL DEFAULT NULL,
   `all_time` varchar(255) NULL DEFAULT NULL,
   `conf` varchar(255) NULL DEFAULT NULL,
   `weight` varchar(255) NULL DEFAULT NULL,
   `username` varchar(255) NULL DEFAULT NULL,
   `start_time` varchar(255) NULL DEFAULT NULL,
   `label` varchar(255) NULL DEFAULT NULL,
   `kind` varchar(255) NULL DEFAULT NULL,
   `del_flag` int(11) DEFAULT '1',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='图片检测记录表';

-- ----------------------------
-- Table structure for detect_video_records
-- ----------------------------
DROP TABLE IF EXISTS `detect_video_records`;
CREATE TABLE `detect_video_records`  (
     `id` int NOT NULL AUTO_INCREMENT,
     `input_video` varchar(255) DEFAULT NULL,
     `out_video` varchar(255) DEFAULT NULL,
     `username` varchar(255) NULL DEFAULT NULL,
     `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `conf` varchar(255) DEFAULT NULL,
     `weight` varchar(255) DEFAULT NULL,
     `kind` varchar(255) DEFAULT NULL,
     `del_flag` int(11) DEFAULT '1',
     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='视频检测记录表';

-- ----------------------------
-- Table structure for detect_camera_records
-- ----------------------------
DROP TABLE IF EXISTS `detect_camera_records`;
CREATE TABLE `detect_camera_records`  (
      `id` int NOT NULL AUTO_INCREMENT,
      `weight` varchar(255) DEFAULT NULL,
      `conf` varchar(255) DEFAULT NULL,
      `username` varchar(255) DEFAULT NULL,
      `start_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `out_video` varchar(255) DEFAULT NULL,
      `kind` varchar(255) DEFAULT NULL,
      `del_flag` int(11) DEFAULT '1',
      PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COMMENT='摄像检测记录表';