package com.example.demo.config.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.rabbitmq.DemoConfirmCallBacl;
import com.example.demo.rabbitmq.DemoListener;

/**
 * rabbitMQ 配置文件
 * @author Administrator
 *
 */
@Configuration
public class RabbitMqConfig {

	public static final String EXCHANGE = "EXCHANGE_DEMO";	// 交换机名
	
	public static final String QUEUE = "QUEUE_DEMO";		// 队列的路由键

	@Autowired
	private QueueConfig queueConfig;
	
	@Autowired
	private ExchangeConfig exchangeConfig;

	@Autowired
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private DemoConfirmCallBacl demoConfirmCallBacl;
	
	/**
	 * 将队列与交换器绑定
	 * @return
	 */
	@Bean
	public Binding binding_one() {
		return BindingBuilder.bind(
				queueConfig.DemoQueue())
				.to(exchangeConfig.directExchange()
				).with(RabbitMqConfig.QUEUE);
	}
	
	/**
	 * 设置消息监听模式
	 * 当有消息到达时会通知监听在对应的队列上的监听对象
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer simpleMessageListenerContainer() {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
		simpleMessageListenerContainer.addQueues(queueConfig.DemoQueue());
		simpleMessageListenerContainer.setExposeListenerChannel(true);
		simpleMessageListenerContainer.setMaxConcurrentConsumers(5);
		simpleMessageListenerContainer.setConcurrentConsumers(1);

		simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);	// 设置手动确认消息
		
		MessageListener adapter = new DemoListener();
		// 设置消息监听器
		simpleMessageListenerContainer.setMessageListener(adapter);
		
		return simpleMessageListenerContainer;
	}
	
	/**
	 * 定义 rabbit template 用户数据接收和发送
	 * @return
	 */
	public RabbitTemplate rabbitTemplate() {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setConfirmCallback(demoConfirmCallBacl);
		return template;
	}
	
}
