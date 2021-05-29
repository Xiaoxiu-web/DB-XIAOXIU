package com.db.sys.service;

import java.util.List;
import java.util.Map;

import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;

public interface SysMenuService {
	
	List<Map<String,Object>> findMenuObjects();
	
	int deleteObject(Integer id);
	
	List<Node> findZtreeMenuNodes();
	
	int insertObject(SysMenu sysMenu);
	
	int updateObject(SysMenu sysMenu);
}
