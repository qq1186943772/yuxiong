package com.example.demo.rabbitmq;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ 消息队列的监听回调
 * @author Administrator
 *
 */
@Component
public class DemoConfirmCallBacl implements ConfirmCallback{

	/**
	 * 监听消息
	 */
	@Override
	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
		
		System.out.println("接收消息回调");
		if(ack) {
			System.out.println("回调成功~");
		}else {
			System.out.println("回调失败~");
		}
		
	}

}
