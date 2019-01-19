package com.example.demo.config.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ 的 交换器 配置
 * @author Administrator
 *
 */
@Configuration
public class ExchangeConfig {

	/**
	 * 交换器配置
	 * @return
	 */
	@Bean
	public DirectExchange directExchange() {
		DirectExchange directExchange = new DirectExchange(
				RabbitMqConfig.EXCHANGE,
				true,
				true
			);
		return directExchange;
	}
	
}
