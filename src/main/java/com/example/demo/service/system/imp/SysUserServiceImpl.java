package com.example.demo.service.system.imp;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.entity.system.SysUser;
import com.example.demo.mapper.system.SysUserMapper;
import com.example.demo.service.system.SysUserService;

@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService{

	@Override
	public Page<SysUser> loadUserPage(Page<SysUser> page) {
		EntityWrapper<SysUser>  wrapper = new EntityWrapper<>();
		return this.selectPage(page,wrapper);
	}

}
