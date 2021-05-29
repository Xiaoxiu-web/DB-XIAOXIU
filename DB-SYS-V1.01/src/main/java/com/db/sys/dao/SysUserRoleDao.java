package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysUserRoleDao {
	
	int deleteObjectByRoleId(Integer roleId);
	
	int insertObjects(@Param("userId")Integer userId,@Param("roleIds")Integer[] roleIds);

	List<Integer> finRoleIdsByUserId(Integer userId);
	
	int deleteObjectsByUserId(Integer userId);
	
	//shiro
	List<Integer> findRoleIdsByUserId(Integer userId);
}
