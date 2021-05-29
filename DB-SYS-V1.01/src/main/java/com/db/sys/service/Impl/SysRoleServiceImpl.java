package com.db.sys.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.commom.util.pageUtil;
import com.db.common.exception.ServiceException;
import com.db.common.vo.CheckBox;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysRoleDao;
import com.db.sys.dao.SysRoleMenuDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.SysRoleMenuVo;

@Service
public class SysRoleServiceImpl implements SysRoleService{

	@Autowired
	private SysRoleDao sysRoleDao;
	@Autowired
	private SysRoleMenuDao sysRoleMenuDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		if(pageCurrent==null||pageCurrent<1)throw new IllegalArgumentException("页码值不正确");
		int rows = sysRoleDao.getRoleCount(name);
		int pageSize = 5;
		int StartIndex = (pageCurrent-1)*pageSize;
		if(rows==0)throw new ServiceException("没有关于‘"+name+"’的记录");
		List<SysRole> RoleList = sysRoleDao.findPageObjects(name, StartIndex, pageSize);
		PageObject<SysRole> pageObject = pageUtil.newInstance(pageCurrent, rows, pageSize, RoleList);
		return pageObject;
	}
	@Override
	public int deleteObject(Integer id) {
		if(id==null||id<1)throw new IllegalArgumentException("角色Id有误");
		int rows = sysRoleDao.deleteObject(id);
		if(rows==0)throw new ServiceException("记录可能已经不存在");
		sysRoleMenuDao.deleteObjectsByRoleId(id);
		sysUserRoleDao.deleteObjectByRoleId(id);
		return rows;
	}
	@Override
	public int saveObject(SysRole sysRole, Integer[] menuIds) {
		if(sysRole==null)throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(sysRole.getName()))throw new IllegalArgumentException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)throw new IllegalArgumentException("必须为角色分配菜单权限");
		
		int rows = sysRoleDao.insertObject(sysRole);
		Integer roleId = sysRole.getId();
		sysRoleMenuDao.insertObjects(roleId, menuIds);
		return rows;
	}
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		if(id==null||id<1)throw new IllegalArgumentException("参数无效");
		SysRoleMenuVo vo = sysRoleDao.findObjectById(id);
		if(vo==null)throw new ServiceException("记录可能已经不存在");
		return vo;
	}
	@Override
	public int updateObject(SysRole sysRole, Integer[] menuIds) {
		if(sysRole==null)throw new IllegalArgumentException("保存对象不能为空");
		if(StringUtils.isEmpty(sysRole.getName()))throw new IllegalArgumentException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)throw new IllegalArgumentException("必须为角色分配菜单权限");
		int rows = sysRoleDao.updateObject(sysRole);
		int row2 = sysRoleMenuDao.deleteObjectsByRoleId(sysRole.getId());
		int row3 = sysRoleMenuDao.insertObjects(sysRole.getId(), menuIds);
		return rows;
	}
	@Override
	public List<CheckBox> findObjects() {
		List<CheckBox> list = sysRoleDao.findObjects();
		if(list==null||list.isEmpty())throw new ServiceException("未找到相关角色信息");
		return list;
	}
	@Override
	public boolean isRoleExist(String name) {
		if(StringUtils.isEmpty(name))throw new ServiceException("请输入角色名");
		int row = sysRoleDao.getCountByName(name);
		if(row>0)throw new ServiceException("角色名已存在");
		return false;
	}
	
	

}
