package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.ResponseCode;
import com.example.demo.bean.ResponseResult;
import com.example.demo.entity.system.DemoMeaasge;
import com.example.demo.rabbitmq.RabbitMqProducer;

import io.swagger.annotations.ApiOperation;

@RestController
public class RabbitMQController {

	@Autowired
	RabbitMqProducer Producer;
	
	@PostMapping(value="message/producer")
	@ApiOperation(value="生产消息",notes="生产DemoQueue队列的消息 ")
	public ResponseResult<Boolean> producerMessage(@RequestBody DemoMeaasge message) {
		return ResponseResult.e(ResponseCode.SIGN_IN_OK,Producer.send("123456", JSON.toJSONString(message)));
	}
	
}
