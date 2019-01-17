package com.example.demo.mapper.system;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.entity.system.SysUser;

@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser>{

	
	
}
