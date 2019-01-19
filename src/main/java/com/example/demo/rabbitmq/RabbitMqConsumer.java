package com.example.demo.rabbitmq;

import org.springframework.stereotype.Component;

/**
 * rabbitMQ 的消费者
 * @author Administrator
 *
 */
@Component
public class RabbitMqConsumer {
	/**
	 * 监听 队列里面的消息
	 * @param message
	 * @throws Exception
	 */
	public void handleMessage(String message) throws Exception {
		System.out.println("my comsumer the message : " + message);
	}
	
}
