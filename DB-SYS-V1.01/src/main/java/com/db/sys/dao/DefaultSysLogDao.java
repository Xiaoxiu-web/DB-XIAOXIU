package com.db.sys.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//@Repository
//public class DefaultSysLogDao implements SysLogDao {

//	@Autowired
//	private SqlSessionFactory sqlSessionFactory;
//	@Override
//	public int deleteObject(Integer... id) {
//		SqlSession session = sqlSessionFactory.openSession();
//		SysLogDao dao = session.getMapper(SysLogDao.class);
//		int rows = dao.deleteObject(id);
//		session.commit();
//		session.close();
//		return rows;
//	}
//}
