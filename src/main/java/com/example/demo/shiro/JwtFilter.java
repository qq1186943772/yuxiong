package com.example.demo.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.example.demo.bean.JwtToken;
import com.example.demo.bean.RequestException;
import com.example.demo.bean.ResponseCode;
import com.example.demo.bean.ResponseResult;

/**
 * jwtFilter 实现
 * @author Administrator
 *
 */
public class JwtFilter extends BasicHttpAuthenticationFilter{

	/**
	 * 判断用户是否想要登录
	 * 检测hander里面是否包含了 Authorization 字段
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String authorization = req.getHeader("Authorization");
		return authorization != null;
	}

	/**
	 * 登录认证
	 */
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		System.out.println("token验证");
		HttpServletResponse res = WebUtils.toHttp(response);
		if(!isLoginAttempt(request, response)) {
			writerResponse(res,ResponseCode.NOT_SING_IN.code,"无身份认证权限标示");
			return false;
		}
		try {
			executeLogin(request, response); // 执行登录
		} catch (RequestException e) {
			 writerResponse(res,403,e.getMessage()); // 抛出认证错误，跟错误信息
			 return false;
		} 
		Subject subject = getSubject(request, response);
		if(mappedValue != null) {
			String[] value = (String[]) mappedValue;
			for(String permission : value) {
				if(permission == null || "".equals(permission.trim())) {	// 判断权限表示是否为空
					continue;
				}
				if(subject.isPermitted(permission)) { 	// 验证是否有该方法的权限
					return true;
				}
			}
		}
		if(subject.getPrincipal() == null) { // 表示没有登录 ，返回登录提示
			 writerResponse(res,ResponseCode.NOT_SING_IN.code,ResponseCode.NOT_SING_IN.msg);
		}else {
			 /*writerResponse(res,ResponseCode.FAIL.code,"无权限访问");*/
			 return true;
		}
		return false;
	}
	
	/**
	 * 具体的登录操作
	 */
	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws RequestException {
		 HttpServletRequest res = (HttpServletRequest) request;
		 String authorization = res.getHeader("Authorization");			// 获取请求带过来的 token 认证信息
		 if(authorization == null || "".equals(authorization.trim())) {
			 throw RequestException.fail("未含授权标示，禁止访问");
		 }
		 JwtToken token = new JwtToken(authorization);		// 使用 token 认证
		 Subject subject = SecurityUtils.getSubject();
		 try {
			subject.login(token);
		} catch (DisabledAccountException e) {
			 if(e.getMessage().equals("verifyFail")){
	                throw new DisabledAccountException("身份已过期，请重新登录",e);
	            }
	            throw new DisabledAccountException(e.getMessage(),e);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	/**
	 * 回传信息
	 * @param response
	 * @param status
	 * @param content
	 */
	private void writerResponse(HttpServletResponse response,Integer status,String content){
        response.setHeader("Content-Type", "application/json;charset=utf-8");
        try {
            response.getWriter().write(JSON.toJSONString(ResponseResult.builder()
                    .status(status)
                    .msg(content)
                    .build()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	/**
	 * 当 isAccessAllowed 和 onAccessDenied 都返回false 的时候 会阻止后面的filter和servlet的执行 。
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return false;
	}
	
}
