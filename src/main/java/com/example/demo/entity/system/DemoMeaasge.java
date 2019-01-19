package com.example.demo.entity.system;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="rebbitMQ Demo 队列使用的消息",description="消息对象")
@Data
public class DemoMeaasge implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value="消息状态",required=true)
	private int status;
	
	@ApiModelProperty(value="消息类别",required=true)
	private String type;
	
	@ApiModelProperty(value="消息主体",required=true)
	private String content;
	
}
