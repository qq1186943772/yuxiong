package com.example.demo.config;

import java.io.IOException;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.entity.GlobalConfiguration;
import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;


@Configuration
@EnableConfigurationProperties(MybatisProperties.class)
public class MyBatisPulsConfig {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private MybatisProperties properties;
	
	@Autowired
	private ResourceLoader resourceLoader = new DefaultResourceLoader();
	
	@Autowired(required = false)
	private Interceptor[] interceptors;
	
	@Autowired(required = false)
    private DatabaseIdProvider databaseIdProvider;
	
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setDialectType("mysql");
		return paginationInterceptor;
	}
	
	@Bean
	public MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean() throws IOException{
		MybatisSqlSessionFactoryBean mybatisPuls = new  MybatisSqlSessionFactoryBean();
		
		mybatisPuls.setDataSource(dataSource); // 设置数据源
		mybatisPuls.setVfs(SpringBootVFS.class);
		
		// 使用 PathMatchingResourcePatternResolver 避免路径找不到
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		mybatisPuls.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
		
		if(StringUtils.hasText(this.properties.getConfigLocation()));
		if(!ObjectUtils.isEmpty(this.interceptors)) {
			mybatisPuls.setPlugins(this.interceptors);
		}
		
		GlobalConfiguration  globalConfig = new GlobalConfiguration();
		globalConfig.setDbType(DBType.MYSQL.name());	//设置数据库类型
		//使用ID_WORKER_STR，因为前后端分离使用整形，前端JS会有精度丢失
		globalConfig.setIdType(IdType.ID_WORKER_STR.getKey());
		globalConfig.setSqlInjector(new AutoSqlInjector());
		MybatisConfiguration mc = new MybatisConfiguration();
		 // 对于完全自定义的mapper需要加此项配置，才能实现下划线转驼峰
		mc.setMapUnderscoreToCamelCase(true);
		mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		
		mybatisPuls.setConfiguration(mc);
		
		if(this.databaseIdProvider != null) {
			mybatisPuls.setDatabaseIdProvider(this.databaseIdProvider);
		}
		if(StringUtils.hasLength(this.properties.getTypeAliasesPackage())) {
			mybatisPuls.setTypeAliasesPackage(this.properties.getTypeAliasesPackage());
		}
		if(StringUtils.hasLength(this.properties.getTypeHandlersPackage())) {
			mybatisPuls.setTypeHandlersPackage(this.properties.getTypeHandlersPackage());
		}
		if(!ObjectUtils.isEmpty(this.properties.resolveMapperLocations())) {
			mybatisPuls.setMapperLocations(this.properties.resolveMapperLocations());
		}
		return mybatisPuls;
	}
	
}
