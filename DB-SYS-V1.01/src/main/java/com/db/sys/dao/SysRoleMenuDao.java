package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface SysRoleMenuDao {
	
	int deleteObjectsByMenuId(Integer menuId);
	
	int deleteObjectsByRoleId(Integer roleId);
	
	int deleteObjectByColumnId(String column,Integer id);
	
	int insertObjects(@Param("roleId")Integer roleId,@Param("menusId")Integer[] menusId);
	
	//shiro
	List<Integer> findMenuIdsByRoleIds(@Param("roleIds")Integer[] roleIds);
}
