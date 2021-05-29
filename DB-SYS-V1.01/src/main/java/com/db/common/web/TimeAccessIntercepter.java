package com.db.common.web;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.db.common.exception.ServiceException;

@Component
public class TimeAccessIntercepter extends HandlerInterceptorAdapter{
	
	private Logger log = LoggerFactory.getLogger(TimeAccessIntercepter.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.debug("HandlerIntercepter  startting!!!");
		
		//允许访问开始时间
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 9);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		long start = c.getTimeInMillis();
		//访问关闭时间
		c.set(Calendar.HOUR_OF_DAY, 21);
		long end = c.getTimeInMillis();
		
		long time = System.currentTimeMillis();
		
		if(time<start||time>end) {
			log.debug(start+","+time+","+end);
			throw new ServiceException("当前时间点不允许访问");
		}
		return true;
	}
}
