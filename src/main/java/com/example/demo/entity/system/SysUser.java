package com.example.demo.entity.system;

import java.io.Serializable;
import java.math.BigInteger;

import com.baomidou.mybatisplus.annotations.TableId;

import lombok.Data;


/**
 * The persistent class for the sys_user database table.
 * 
 */
@Data
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	private String userCode;

	private String loginName;

	private String loginPass;

	private int state;

	private String userEmall;

	private String userName;

	private BigInteger userPhone;

	private String userRemark;


}