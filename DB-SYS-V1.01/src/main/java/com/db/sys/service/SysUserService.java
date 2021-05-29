package com.db.sys.service;

import java.util.Map;

import com.db.common.vo.PageObject;
import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserService {
	
	PageObject<SysUserDeptVo> findPageObjects(String username,Integer pageCurrent);
	
	int ValidById(Integer id,Integer valid,String modifiedUser);
	
	int saveObject(SysUser sysUser,Integer[] roleIds);
	
	Map<String,Object> findObjectById(Integer userId);
	
	int updateObject(SysUser sysUser,Integer[] roleIds);
	
	/**
	 * 
	 * @param password 原密码
	 * @param newPassword 新密码
	 * @param cfgPassword 确认密码
	 * @return
	 */
	int updatePassword(String password,String newPassword,String cfgPassword);
}
