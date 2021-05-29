package com.db.sys.service;

import java.util.List;

import com.db.common.vo.CheckBox;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysRole;
import com.db.sys.vo.SysRoleMenuVo;

public interface SysRoleService {
	
	PageObject<SysRole> findPageObjects(String name,Integer pageCurrent);
	
	int deleteObject(Integer id);
	
	int saveObject(SysRole sysRole,Integer[] menuIds);
	
	SysRoleMenuVo findObjectById(Integer id);
	
	int updateObject(SysRole sysRole,Integer[] menuIds);
	
	List<CheckBox> findObjects();
	
	boolean isRoleExist(String name);
}
