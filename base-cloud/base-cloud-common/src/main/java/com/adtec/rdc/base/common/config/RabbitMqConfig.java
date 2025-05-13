package com.adtec.rdc.base.common.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adtec.rdc.base.common.constants.MqQueueNameConstant;

/**
 * @author: JTao
 * @date: 2018/11/14 16:06
 */
@Configuration
@ConditionalOnProperty(value = "components.messageQueue", havingValue = "true")
public class RabbitMqConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        return factory;
    }

    @Bean
    public Queue sysLogQueue() {
        return new Queue(MqQueueNameConstant.SYS_LOG_QUEUE);
    }

    @Bean
    public Queue mobileCodeQueue() {
        return new Queue(MqQueueNameConstant.MOBILE_CODE_QUEUE);
    }
    
    @Bean
    public Queue mailCodeQueue() {
        return new Queue(MqQueueNameConstant.MAIL_CODE_QUEUE);
    }

    @Bean
    public Queue requestStatisticsQueue() {
        return new Queue(MqQueueNameConstant.REQUEST_STATISTICS_QUEUE);
    }
    
    @Bean
    public Queue rptAuthQueue() {
    	return new Queue(MqQueueNameConstant.REPORT_AUTH_QUEUE);
    }
    @Bean
    public Queue rptUserQueue() {
    	return new Queue(MqQueueNameConstant.REPORT_USER_QUEUE);
    }
    @Bean
    public Queue rptRoleQueue() {
    	return new Queue(MqQueueNameConstant.REPORT_ROLE_QUEUE);
    }
    @Bean
    public Queue rptBranchQueue() {
    	return new Queue(MqQueueNameConstant.REPORT_BRANCH_QUEUE);
    }
    @Bean
    public Queue rptBranchBatchQueue() {
    	return new Queue(MqQueueNameConstant.REPORT_BRANCH_BATCH_QUEUE);
    }
    @Bean
    public Queue rptRequestQueue() {
    	return new Queue(MqQueueNameConstant.REPORT_REQUEST_QUEUE);
    }
    @Bean
    public Queue elasticSearchRequestQueue() {
    	return new Queue(MqQueueNameConstant.ELASTICSEARCH_QUEUE);
    }
}
