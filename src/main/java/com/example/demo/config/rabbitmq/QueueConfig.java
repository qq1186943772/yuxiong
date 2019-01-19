package com.example.demo.config.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置 rabbitMq 的队列，一个Queue 一个消息队列
 * @author Administrator
 *
 */
@Configuration
public class QueueConfig {

	/**
	 * 一个消息队列
	 * @return
	 */
	@Bean
	public Queue DemoQueue() {
		
		/**
		 * 队列名
		 * 是否持久化
		 * 表示消息队列没有在使用时将被自动删除 默认是false
		 * 表示该消息队列是否只在当前connection生效,默认是false
		 */
		return new Queue("QUEUE_DEMO",true,false,false);
	}
	
}
