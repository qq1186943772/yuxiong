package com.example.demo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.example.demo.bean.ResponseCode;
import com.example.demo.bean.ResponseResult;
import com.example.demo.entity.system.SysUser;
import com.example.demo.service.system.SysUserService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
public class SysUserController {
	
	@Resource(name="sysUserService")
	SysUserService sysUserService;
	
	@ResponseBody
	@RequestMapping(value="/sys/user",method=RequestMethod.GET)
	@ApiOperation(value = "加载用户信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="pageSize",value="页面大小",required=true,dataType="int"),
		@ApiImplicitParam(paramType="query",name="pageNo",value="页码",required=true,dataType="int")
	})
	public Page<SysUser> userPlus(Page<SysUser> page){
		return sysUserService.loadUserPage(page);
	}
	
	@RequestMapping(value="/sys/user",method=RequestMethod.POST)
	@ApiOperation(value = "增加用户")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="userCode",value="用户编码",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="loginName",value="用户登录名",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="loginPass",value="用户登录密码",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="state",value="用户状态",required=true,dataType="int"),
		@ApiImplicitParam(paramType="query",name="userEmall",value="用户电子邮箱",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="userName",value="用户姓名",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="userPhone",value="用户手机号",required=true,dataType="int"),
		@ApiImplicitParam(paramType="query",name="userRemark",value="用户备注",required=true,dataType="String")
	})
	public ResponseResult<Boolean> userSave(SysUser entity){
		return ResponseResult.e(ResponseCode.SIGN_IN_OK,sysUserService.insertOrUpdateAllColumn(entity));
	}
	
	
	
	
}
