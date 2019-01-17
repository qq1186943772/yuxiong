package com.example.demo.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.example.demo.bean.JwtToken;
import com.example.demo.util.JwtUtil;

public class DemoRealm extends AuthorizingRealm{

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof JwtToken;
	}
	
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("身份认证开始~");
		
		JwtToken userToken = (JwtToken) token;
		
		String username = JwtUtil.getUsername(userToken.getToken());
		
		String password = "tianba.0821";
		
		if(username == null || "".equals(username.trim())) {
			throw new AccountException("token不正确");
		}
		
		if(!"yuxiong".equals(username)) {
			 throw new AccountException("用户名不正确");
		}
		
		if(!JwtUtil.verify(userToken.getToken(), username, password)) {
			throw new AccountException("密码不正确");
		}
		
		return new SimpleAuthenticationInfo(userToken.getToken(),userToken.getToken(),getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("认证权限开始~");
		
		System.out.println(principals.getPrimaryPrincipal());
		
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo  info = new SimpleAuthorizationInfo ();
		Set<String> set = new HashSet<>();
		set.add("user");
		info.setRoles(set);
		return info;
	}

}
