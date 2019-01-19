package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.bean.JwtToken;
import com.example.demo.bean.RequestException;
import com.example.demo.bean.ResponseCode;
import com.example.demo.bean.ResponseResult;
import com.example.demo.service.DemoService;
import com.example.demo.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value="一个用来测试的Controller{Spring mvc，shiro，swagger，mybatis}")
public class TestController {

	@Autowired
	DemoService<Map<String, Object>> demo; 

	
	@ResponseBody
	@RequestMapping(value="/visitor/test",method=RequestMethod.GET)
	@ApiOperation(value = "游客访问")
	public String test() {
		return "Hello Visitor";
	}
	
	@ResponseBody
	@RequestMapping(value="/jwt/test",method=RequestMethod.GET)
	@ApiOperation(value = "测试Jwt访问")
	public String testJwt() {
		return "Hello Jwt testing Man ~ ";
	}
	
	@ResponseBody
	@RequestMapping(value="/user/userLoad",method=RequestMethod.GET)
	@ApiOperation(value = "加载用户信息")
	public List<Map<String,Object>> userLoad(){
		Map<String, Object> map = new HashMap<String, Object>();
		return demo.loadList("User.findById", map);
	}
	
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/login",method=RequestMethod.GET)
	@ApiOperation(value = "用户登录")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query", name = "username", value = "用户登录名", required = true, dataType = "String"),
		@ApiImplicitParam(paramType="query", name = "password", value = "用户登录密码", required = true, dataType = "String")
	})
	public ResponseResult login(String username,String password) {
		
		String sign = JwtUtil.sign(username, password);
		
		JwtToken token = new JwtToken(sign);
        
		Subject subject = SecurityUtils.getSubject();
        
		try {
            subject.login(token);
            if(!subject.isAuthenticated()){
                throw new RequestException(ResponseCode.SIGN_IN_INPUT_FAIL);
            }
        }catch (DisabledAccountException e){
            throw new RequestException(ResponseCode.SIGN_IN_INPUT_FAIL.code,e.getMessage(),e);
        }
        
		return ResponseResult.e(ResponseCode.SIGN_IN_OK,subject.getPrincipal());
	}
	
	@ResponseBody
	@ApiOperation(value = "管理员访问")
	@RequestMapping(value="/admin/adminLoad")
	public String adminLoad(){
		return "Hello Admin";
	}
	
	
}
