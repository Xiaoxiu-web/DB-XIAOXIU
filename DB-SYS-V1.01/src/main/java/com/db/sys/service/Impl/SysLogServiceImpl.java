package com.db.sys.service.Impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.db.commom.util.pageUtil;
import com.db.common.annotation.RequiedLog;
import com.db.common.exception.ServiceException;
import com.db.common.vo.PageObject;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;

@Service
public class SysLogServiceImpl implements SysLogService{
	@Autowired
	private SysLogDao sysLogDao;

	@Override
	@RequiedLog("查询日志")
	public PageObject<SysLog> findPageObjects(String username, Integer pageCurrent) {
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("输入的页码值不正确");
		int row = sysLogDao.getRowCount(username);
		if(row==0)
			throw new ServiceException("没有找到关于 "+username+" 的记录");
		int pageSize=5;
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysLog> SysLogList = sysLogDao.findPageObjects(username, startIndex, pageSize);
		PageObject<SysLog> pageObject = pageUtil.newInstance(pageCurrent, row, pageSize, SysLogList);
		return pageObject;
	}

	@Override
	@RequiresPermissions("sys:log:delete")
	@RequiedLog("日志删除操作")
	public int deleteObjects(Integer... ids) {
		if(ids==null||ids.length==0)throw new ServiceException("请选择一条记录");
		int rows=0;
		try {
			rows=sysLogDao.deleteObject(ids);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServiceException("系统维护中...");
		}
		if(rows==0) throw new ServiceException("记录可能被删除");
		return rows;
	}
	
	
}
