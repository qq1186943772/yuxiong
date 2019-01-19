package com.example.demo.rabbitmq;

import java.util.UUID;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.config.rabbitmq.RabbitMqConfig;

/**
 * rabbitMQ 的消息生产者
 * @author Administrator
 *
 */
@Component
public class RabbitMqProducer {

	@Autowired
	private RabbitTemplate rabbit;
	
	/**
	 * 发送消息
	 * @param uuid		消息的ID
	 * @param message	消息的内容
	 * @return
	 */
	public boolean send(String uuid,String message) {
		
		// 构件一个 RabbitMQ 的消息
		Message content = MessageBuilder.withBody(message.getBytes())
				.setContentEncoding("utf-8")
				.setContentType(MessageProperties.CONTENT_TYPE_JSON)
				.setMessageId(UUID.randomUUID().toString())
				.build();
		
		// 使用 RabbitMQ 的 Template 发送消息
		rabbit.convertAndSend(
				RabbitMqConfig.EXCHANGE,
				RabbitMqConfig.QUEUE,
				content
			);
		
		return true;
	}
	
}
