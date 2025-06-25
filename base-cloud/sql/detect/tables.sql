DROP TABLE IF EXISTS `detect_image_record`;
CREATE TABLE `detect_image_record` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `original_image` varchar(255) NOT NULL,
   `predicted_image` varchar(255) NOT NULL,
   `recognition_weight` varchar(255) DEFAULT NULL,
   `min_threshold` decimal(5,2) NOT NULL,
   `ai_assistant` varchar(255) NOT NULL,
   `ai_suggestion` text,
   `recognition_time` datetime NOT NULL,
   `recognition_user` varchar(50) NOT NULL,
   `detection_result` json DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '图像检测记录表';

DROP TABLE IF EXISTS `detect_video_record`;
CREATE TABLE `detect_video_record` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `original_video` varchar(255) NOT NULL,
   `predicted_video` varchar(255) NOT NULL,
   `recognition_weight` varchar(255) DEFAULT NULL,
   `min_threshold` decimal(5,2) NOT NULL,
   `recognition_time` datetime NOT NULL,
   `recognition_user` varchar(50) NOT NULL,
   `detection_result` json DEFAULT NULL,
   PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT = '视频检测记录表'