package configTest;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;

public class TestDataSurce extends TestConfig{
	
	@Test
	public void testDataSource() throws Exception {
		DruidDataSource dataSource = ctx.getBean("dataSource",DruidDataSource.class);
		DruidPooledConnection conn = dataSource.getConnection();
		System.out.println(conn+"链接对象");
	}
}
