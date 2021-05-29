package com.db.common.aspectj;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.db.common.annotation.RequiedLog;
import com.db.sys.dao.SysLogDao;
import com.db.sys.entity.SysLog;
import com.db.sys.entity.SysUser;

@Aspect
@Service
public class LogAspect {
	private Logger log=LoggerFactory.getLogger(LogAspect.class);
	
	@Autowired
	private SysLogDao sysLogDao;
	
	@Around("bean(sysLogServiceImpl)")
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		log.debug("AOP - 切面开始");
		long t1 = System.currentTimeMillis();
		Object result = jp.proceed();
		long t2 = System.currentTimeMillis();
		saveObject(jp,(t2-t1));
		log.debug("AOP - 切面执行完毕");
		return result;
	}

	private void saveObject(ProceedingJoinPoint jp, long time) throws Exception {
		Method targetMethod = getTargetMethod(jp);
		String targetMethodName = getTargetMethodName(targetMethod);
		String params = Arrays.toString(jp.getArgs());
		SysUser sysUser = (SysUser)SecurityUtils.getSubject().getPrincipal();
		String username = sysUser.getUsername();
		
		RequiedLog rolg = targetMethod.getDeclaredAnnotation(RequiedLog.class);
		String operation = "operation";
		if(rolg!=null&&!StringUtils.isEmpty(rolg.value())) {
			operation=rolg.value();
		}
		String ip = "模拟IP";
		SysLog sysLog = new SysLog();
		sysLog.setUsername(username);
		sysLog.setIp(ip);
		sysLog.setParams(params);
		sysLog.setOperation(operation);
		sysLog.setTime(time);
		sysLog.setCreatedTime(new Date());
		sysLog.setMethod(targetMethodName);
		int row = sysLogDao.inSertObject(sysLog);
		log.debug(row+"/-/"+operation+"insertObject方法输出的信息");
	}

	private String getTargetMethodName(Method targetMethod) {
		return new StringBuffer(targetMethod.getClass().getName()).append(".").append(targetMethod.getName()).toString();
		
	}

	/**
	 * 获取目标方法
	 * @param jp
	 * @return Method
	 * @throws Exception
	 */
	private Method getTargetMethod(ProceedingJoinPoint jp) throws Exception {
		Class<? extends Object> TargetClass = jp.getTarget().getClass();
		MethodSignature signatureMethod = (MethodSignature)jp.getSignature();
		Method method = TargetClass.getDeclaredMethod(signatureMethod.getName(), signatureMethod.getParameterTypes());
		return method;
	}
}













