package com.db.common.web;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	private Logger log=LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody
	public JsonResult doHandleRunTimeException(RuntimeException e) {
		e.printStackTrace();
		System.out.println("全局异常类捕捉到一个RunTimeException");
		return new JsonResult(e);
	}
	@ExceptionHandler(ShiroException.class)
	@ResponseBody
	public JsonResult doHandleShiroException(ShiroException e) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setState(0);
		if(e instanceof UnknownAccountException) {
			jsonResult.setMessage("账户未被注册，请重新输入");
		}else if(e instanceof LockedAccountException){
			jsonResult.setMessage("账户已被禁用");
		}else if(e instanceof IncorrectCredentialsException) {
			jsonResult.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException) {
			jsonResult.setMessage("没有操作权限");
		}else {
			jsonResult.setMessage("系统维护中");
		}
		log.error("全局异常 shiro:",e.getMessage());
		return jsonResult;
	}
}
