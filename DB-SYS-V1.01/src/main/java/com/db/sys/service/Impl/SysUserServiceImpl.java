package com.db.sys.service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.db.commom.util.pageUtil;
import com.db.common.exception.ServiceException;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysUserDao;
import com.db.sys.dao.SysUserRoleDao;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;
import com.db.sys.vo.SysUserDeptVo;

@Service
public class SysUserServiceImpl implements SysUserService{
	
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private SysUserRoleDao sysUserRoleDao;
	
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		if(pageCurrent==null||pageCurrent<1)throw new IllegalArgumentException("页码值无效");
		int rowCount = sysUserDao.getRowCount(username);
		if(rowCount==0)throw new ServiceException("未找到关于： "+username+"的相关记录");
		int pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysUserDeptVo> userList = sysUserDao.findPageObjects(username, startIndex, 5);
		return pageUtil.newInstance(pageCurrent, rowCount, pageSize, userList);
	}
	@RequiresPermissions("sys:user:valid")
	@Override
	public int ValidById(Integer id, Integer valid, String modifiedUser) {
		if(id==null||id<1)throw new IllegalArgumentException("id值无效");
		if(valid==null||(valid!=0&&valid!=1))throw new IllegalArgumentException("无效状态值");
		int row = sysUserDao.validById(id, valid, modifiedUser);
		if(row==0)throw new ServiceException("状态修改失败，原因记录可能已经不存在");
		return row;
	}
	@Override
	@Transactional( rollbackFor = Throwable.class,isolation = Isolation.READ_COMMITTED,timeout = -1,readOnly = false)
	public int saveObject(SysUser sysUser, Integer[] roleIds) {
		if(roleIds==null||roleIds.length==0)throw new ServiceException("必须为用户分配角色信息");
		if(sysUser==null)throw new IllegalArgumentException("保存的用户对象不能为空");
		if(StringUtils.isEmpty(sysUser.getUsername()))throw new IllegalArgumentException("用户名不能为空");
		if(StringUtils.isEmpty(sysUser.getPassword()))throw new IllegalArgumentException("密码不能为空");
		
		String salt = UUID.randomUUID().toString();
		sysUser.setSalt(salt);
		SimpleHash simpleHash = new SimpleHash("MD5", sysUser.getPassword(), salt,1);
		sysUser.setPassword(simpleHash.toHex());
		int rows = sysUserDao.insertObject(sysUser);
		sysUserRoleDao.insertObjects(sysUser.getId(), roleIds);
		return rows;
	}
	@Override
	public Map<String, Object> findObjectById(Integer userId) {
		if(userId==null||userId<=0)throw new IllegalArgumentException("无效的UserId值"+userId);
		SysUserDeptVo user = sysUserDao.findObjectById(userId);
		List<Integer> roleIds = sysUserRoleDao.finRoleIdsByUserId(userId);
		HashMap<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	@Override
	public int updateObject(SysUser sysUser, Integer[] roleIds) {
		if(roleIds==null||roleIds.length==0)throw new ServiceException("必须为用户分配角色信息");
		if(sysUser==null)throw new IllegalArgumentException("保存的用户对象不能为空");
		if(StringUtils.isEmpty(sysUser.getUsername()))throw new IllegalArgumentException("用户名不能为空");
		
		int rows = sysUserDao.updateObject(sysUser);
		if(rows==0)throw new ServiceException("记录可能已经不存在");
		sysUserRoleDao.deleteObjectsByUserId(sysUser.getId());
		sysUserRoleDao.insertObjects(sysUser.getId(), roleIds);
		return rows;
	}
	@Override
	public int updatePassword(String password, String newPassword, String cfgPassword) {
		if(StringUtils.isEmpty(password))throw new IllegalArgumentException("输入的原密码无效");
		if(StringUtils.isEmpty(newPassword)||!newPassword.equals(cfgPassword))throw new ServiceException("两次密码输入不一致");
		
		Subject subject = SecurityUtils.getSubject();
		SysUser user=(SysUser)subject.getPrincipal();
		String oldpwd = new SimpleHash("MD5", password, user.getSalt(), 1).toHex();
		if(!oldpwd.equals(user.getPassword()))throw new ServiceException("修改失败，原密码不正确");
		String newSalt = UUID.randomUUID().toString();
		String newPWD = new SimpleHash("MD5", newPassword, newSalt, 1).toHex();
		int row = sysUserDao.updatePassword(newPWD, newSalt, user.getId());
		if(row==0)throw new ServiceException("未找到需要修改的记录");
		return row;
	}

}
