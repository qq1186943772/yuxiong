package com.example.demo.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class DemoRealm extends AuthorizingRealm{

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("身份认证开始~");
		
		UsernamePasswordToken userToken = (UsernamePasswordToken) token;
		
		String username = userToken.getUsername();
		Object passObj = userToken.getCredentials();
		
		if(!"yuxiong".equals(username)) {
			 throw new AccountException("用户名不正确");
		}
		if(passObj == null ) {
			 throw new AccountException("密码为空");
		}
		
		String password  = new String((char[]) passObj); 
		if(!"tianba.0821".equals(password)) {
			 throw new AccountException("密码不正确");
		}
		
		return new SimpleAuthenticationInfo(token.getPrincipal(),password,getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("认证权限开始~");
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		SimpleAuthorizationInfo  info = new SimpleAuthorizationInfo ();
		Set<String> set = new HashSet<>();
		set.add("user");
		info.setRoles(set);
		return info;
	}

}
