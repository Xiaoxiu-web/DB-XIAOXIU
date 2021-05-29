package com.db.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.db.sys.entity.SysLog;

public interface SysLogDao {
	
	int deleteObject(@Param("ids")Integer... id);
	/**
	 * 1 查询方法
	 * @param username
	 * @return
	 */
	int getRowCount(@Param("username")String username);
	List<SysLog> findPageObjects(@Param("username")String username,
									@Param("startIndex")Integer startIndex,
									@Param("pageSize")Integer pageSize);
	int inSertObject(SysLog sysLog);
	
}
