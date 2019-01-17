package com.example.demo.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.shiro.DemoRealm;
import com.example.demo.shiro.JwtFilter;

@Configuration
public class ShiroConfig {

	@Bean
	public DemoRealm demoRealm() {
		return new DemoRealm();
	}
	
	@Bean
	public SecurityManager securityManager(DemoRealm demoRealm) {
		DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
		securityManager.setRealm(demoRealm);
		return securityManager;
	}
	
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		
		ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
		factory.setSecurityManager(securityManager);
		
		factory.setUnauthorizedUrl("/user/notRole");
		
		Map<String,Filter> filters = new HashMap<>();
		filters.put("perms",new JwtFilter());
		factory.setFilters(filters);						// 设置过滤器
		
		Map<String, String> map = new LinkedHashMap<>();
		
		map.put("/swagger-ui.html", "anon");				// swagger-ui.html 访问界面
		map.put("/swagger-resources/**", "anon");			// swagger-ui.html 访问界面
		map.put("/v2/api-docs/**", "anon");					// swagger-ui.html 访问界面
		map.put("/webjars/**", "anon");						// swagger-ui.html 访问界面
		map.put("/visitor/**", "anon");						// 设置此路径不需要认证
		map.put("/user/**", "roles[user]");					// 设置访问此路径需要 user 权限
		map.put("/admin/**", "roles[admin]");				// 是个访问此路劲需要 admin权限
		map.put("/login/**", "anon");						// 设置此路劲不需要认证
		
		map.put("/**", "perms");							// 添加自定义过滤器

		factory.setFilterChainDefinitionMap(map);			// 设置拦截路径
		return factory;
	}
	
}
