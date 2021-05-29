package com.db.commom.util;

import java.util.List;

import com.db.common.vo.PageObject;
/**
 * 分页数据工具类
 * @author ThinkPad
 *
 */
public abstract class pageUtil {
	
	public static <T>PageObject<T> newInstance(Integer pageCurrent, int rows, int pageSize, List<T> RoleList) {
		
		PageObject<T> pageObject = new PageObject<T>();
		pageObject.setRecords(RoleList);
		pageObject.setRowCount(rows);
		pageObject.setPageCount((rows-1)/pageSize+1);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		return pageObject;
	}
}
