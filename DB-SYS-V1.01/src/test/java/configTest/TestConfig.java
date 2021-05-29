package configTest;

import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.db.sys.dao.SysLogDao;

public class TestConfig {
	
	protected ClassPathXmlApplicationContext ctx;
	
	@Before
	public void init() {
		System.out.println("加载配置文件创建Bean对象");
		ctx = new ClassPathXmlApplicationContext("spring-configs.xml");
	}
	
	@Test
	public void testctx() {
		System.out.println(ctx);
	}
	@Test
	public void testProperties() {
		Properties properties = ctx.getBean("cfg",Properties.class);
		System.out.println(properties);
	}
	@Test
	public void testSqlsessionfactoryBean() {
		SqlSessionFactory sqlsessionfactoryBean = ctx.getBean("sqlSessionFactory", SqlSessionFactory.class);
		System.out.println(sqlsessionfactoryBean);
	}
	@Test
	public void testMyBatis() {
		SqlSessionFactory sqlsessionFactory = ctx.getBean("sqlSessionFactory",SqlSessionFactory.class);
		SqlSession session = sqlsessionFactory.openSession();
		Integer[] ids = {1,2,3};
		SysLogDao mapper = session.getMapper(SysLogDao.class);
		int rows = mapper.deleteObject(ids);
		session.commit();
		System.out.println(rows);
		session.close();
	}
	@After
	public void destory() {
		System.out.println("配置文件关闭");
		ctx.close();
	}
}

