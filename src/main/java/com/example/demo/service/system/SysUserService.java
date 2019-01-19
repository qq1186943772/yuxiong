package com.example.demo.service.system;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.example.demo.entity.system.SysUser;

public interface SysUserService extends IService<SysUser>{

	public Page<SysUser> loadUserPage(Page<SysUser> page);
	
}
