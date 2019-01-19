package com.example.demo.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 自定义  Listener 
 * 由于是由 RabbitMQ 回调 不在Spring 管理范围之内 无法使用spring 给其注入属性
 * @author Administrator
 *
 */
public class DemoListener implements MessageListener{

	/**
	 * 监听消息的回调函数
	 */
	@Override
	public void onMessage(Message message) {
		
		RabbitMqConsumer rabbitMqConsumer = new RabbitMqConsumer();
		
		try {
			String content = new String(message.getBody(),"UTF-8");
			rabbitMqConsumer.handleMessage(content);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
	}

}
