package com.littlelee.base.common.base.service;

public interface MessageQueueService {
	/**
	 * 发送消息
	 * @param rabbitTemplate
	 * @param queue
	 * @param object
	 */
	void convertAndSend(String queue, Object object);
}
