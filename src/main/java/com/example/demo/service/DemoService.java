package com.example.demo.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

/**
 * 基本 Service 之后的 service 继承这个service
 * @author Administrator
 *
 * @param <T>
 */
public interface DemoService<T> {
	/**
	 * 增加方法
	 * @param form	参数值
	 * @return
	 */
	public boolean add(String str,T form);
	
	/**
	 * 根据主键删除一条
	 * @param id	主键ID
	 * @return
	 */
	public boolean deleteOne(String str,String id);
	
	/**
	 * 根据主要删除多条
	 * @param id	主键数组
	 * @return
	 */
	public boolean deleteNum(String str,List<String> ids);
	
	/**
	 * 修改方法
	 * @param from	修改参数
	 * @return
	 */
	public boolean edit(String str,T from);
	
	/**
	 * 搜索列表
	 * @param form
	 * @return
	 */
	public List<Map<String, Object>> loadList(String str,T form);
	
	/**
	 * 统计列表
	 * @param page
	 * @param from
	 * @return
	 */
	public PageInfo<Map<String, Object>> pageList(String str,PageInfo<Map<String, Object>> page,T from);
	
}
