package com.littlelee.base.detect.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @ClassName ChatEntity
 * @Author littlelee
 * @Version 1.0
 * @Description ChatEntity
 **/
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChatEntity {
    private String currentUserName;
    private String message;
}
