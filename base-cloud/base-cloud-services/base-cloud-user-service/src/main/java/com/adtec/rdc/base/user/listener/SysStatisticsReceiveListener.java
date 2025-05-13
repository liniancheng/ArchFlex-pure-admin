package com.adtec.rdc.base.user.listener;

import com.adtec.rdc.base.common.constants.MqQueueNameConstant;
import com.adtec.rdc.base.common.model.bo.StatisticalInfo;
import com.adtec.rdc.base.statistics.model.po.SysStatisticalInfo;
import com.adtec.rdc.base.statistics.service.SysStatisticalInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 类名：SysStatisticsReceiveListener</br>
 * 模块：</br>
 * 说明：</br>
 * <p>
 * ********************修订历史***********************</br>
 * 时间 作者 参考 描述</br>
 * <p>
 * ***************************************************</br>
 *
 * @author 何伟/hewei@adtec.com.cn
 * @version V1.0
 * @copyright ADTEC
 * @since 2020/1/17
 */
@Slf4j
@Component
@ConditionalOnProperty(value = "components.messageQueue", havingValue = "true")
@RabbitListener(queues = MqQueueNameConstant.REQUEST_STATISTICS_QUEUE)
public class SysStatisticsReceiveListener {

    @Autowired
    private SysStatisticalInfoService statisticalInfoService;

    @RabbitHandler
    public void handler(StatisticalInfo statisticalInfo) {
        SysStatisticalInfo sysStatisticalInfo = new SysStatisticalInfo();
        BeanUtils.copyProperties(statisticalInfo, sysStatisticalInfo);
        statisticalInfoService.save(sysStatisticalInfo);
    }
}
