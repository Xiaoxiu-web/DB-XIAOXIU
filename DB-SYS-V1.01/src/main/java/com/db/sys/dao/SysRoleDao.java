package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.common.vo.CheckBox;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleDao {
	
	int getRoleCount(String name);
	
	List<SysRole> findPageObjects(
			@Param("name")String name,
			@Param("startIndex")Integer StartIndex,
			@Param("pageSize")Integer pageSize);
	
	int deleteObject(Integer id);
	
	int insertObject(SysRole sysRole);
	
	SysRoleMenuVo findObjectById(Integer id);
	
	int updateObject(SysRole sysRole);
	
	List<CheckBox> findObjects();
	
	int getCountByName(String name);
}
