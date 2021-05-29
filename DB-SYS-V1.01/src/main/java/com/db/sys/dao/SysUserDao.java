package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysUser;
import com.db.sys.vo.SysUserDeptVo;

public interface SysUserDao {
	
	List<SysUserDeptVo> findPageObjects(
			@Param("username")String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	int getRowCount(@Param("username")String username);
	
	int validById(@Param("id")Integer id,
				@Param("valid")Integer valid,
				@Param("modifiedUser")String modifiedUser);
	
	int insertObject(SysUser sysUser);
	
	int updateObject(SysUser sysUser);
	
	SysUserDeptVo findObjectById(Integer id);
	
	SysUser findUserByUserName(String username);
	
	int updatePassword(@Param("password")String password,@Param("salt")String salt,@Param("id")Integer id);
}
