package configTest;

import java.util.List;

import org.junit.Test;

import com.db.common.vo.PageObject;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.service.SysLogService;

public class testDelete extends TestConfig{
	
	@Test
	public void testDefaultSysLogDao() {
		SysLogDao SysLogDao = ctx.getBean("defaultSysLogDao", SysLogDao.class);
		int rows = SysLogDao.deleteObject(10,11,12);
		System.out.println(rows);
	}
	@Test
	public void testAutoSysLogDao() {
		SysLogDao AutoSysLogDao = ctx.getBean("sysLogDao", SysLogDao.class);
		int rows = AutoSysLogDao.deleteObject(10,11,12);
		System.out.println(rows);
	}
	
	/**
	 * 测试查询方法
	 */
	@Test
	public void testSelectCount() {
		SysLogDao SysLogDao = ctx.getBean("sysLogDao", SysLogDao.class);
		int rows = SysLogDao.getRowCount("1");
		System.out.println(rows);
	}
	@Test
	public void testSelectObject() {
		SysLogDao sysLogDao = ctx.getBean("sysLogDao", SysLogDao.class);
		List<SysLog> ListSysLog = sysLogDao.findPageObjects("admin", 0, 5);
		System.out.println(ListSysLog);
	}
	@Test
	public void testFindObject() {
		SysLogService sysLogService = ctx.getBean("sysLogServiceImpl", SysLogService.class);
		PageObject<SysLog> sysLogList = sysLogService.findPageObjects("admin", 1);
		System.out.println(sysLogList);
	}
	
}
