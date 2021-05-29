package com.db.sys.service.Impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.exception.ServiceException;
import com.db.common.vo.Node;
import com.db.sys.dao.SysMenuDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.entity.SysMenu;
import com.db.sys.service.SysMenuService;

@Service
public class SysMenuServiceImpl implements SysMenuService{
	
	@Autowired 
	private SysMenuDao sysMenuDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	
	@Override
	public List<Map<String, Object>> findMenuObjects() {
		List<Map<String, Object>> menuList = sysMenuDao.findObjects();
		System.out.println("菜单列表Map"+menuList.toString());
		if(menuList==null||menuList.size()==0) {
			throw new ServiceException("菜单记录不存在");
		}
		return menuList;
	}

	@Override
	public int deleteObject(Integer id) {
		if(id==null||id<1) throw new IllegalArgumentException("Id值无效");
		int childCount = sysMenuDao.getChildCount(id);
		if(childCount>0)throw new ServiceException("请先删除子元素");
		sysRoleMenuDao.deleteObjectsByMenuId(id);
		int rows = sysMenuDao.deleteObject(id);
		if(rows==0)throw new ServiceException("记录可能已经不存在");
		return rows;
	}

	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> ZtreeMenuNodes = sysMenuDao.findZtreeMenuNodes();
		return ZtreeMenuNodes;
	}

	@Override
	public int insertObject(SysMenu sysMenu) {
		if(sysMenu==null)throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(sysMenu.getName()))throw new ServiceException("菜单名不能为空");
		//...
		int rows;
		try {
			rows=sysMenuDao.insertObject(sysMenu);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("保存失败");
		}
		return rows;
	}

	@Override
	public int updateObject(SysMenu sysMenu) {
		if(sysMenu==null)throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(sysMenu.getName()))throw new ServiceException("菜单名不能为空");
		int rows = sysMenuDao.updateObject(sysMenu);
		if(rows==0)throw new ServiceException("修改的记录可能已经被删除");
		return rows;
	}
	
}
