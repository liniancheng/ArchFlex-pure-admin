package com.adtec.rdc.base.user;

import com.adtec.rdc.base.common.base.service.MessageQueueService;
import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.enums.SmsMessageChannnelEnum;
import com.adtec.rdc.base.common.template.sms.SmsMessageTemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseUserServiceApplicationTests {

    @Autowired
    private MessageQueueService messageQueueService;

    @Test
    public void contextLoads() {
        SmsMessageTemplate smsMessageTemplate = new SmsMessageTemplate();
        smsMessageTemplate.setMobile("15079155614");
        smsMessageTemplate.setSignName("程序咖啡厅");
        String[] params =  {"567821"};
        smsMessageTemplate.setParams(params);
        smsMessageTemplate.setTemplate("238684");
        smsMessageTemplate.setChannel(SmsMessageChannnelEnum.TENCENT_CLOUD.getCode());
        messageQueueService.convertAndSend(MqQueueNameConstant.MOBILE_CODE_QUEUE,smsMessageTemplate);
    }

}
