package com.littlelee.base.detect.model.po;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @ClassName ChatRecord 数据库表所对应的持久层对象
 * @Author littlelee
 * @Version 1.0
 * @Description ChatRecord
 **/
@Data
@ToString
public class ChatRecord {

    private String id;
    private String content;
    private String chatType;
    private LocalDateTime chatTime;
    private String familyMember;

}
