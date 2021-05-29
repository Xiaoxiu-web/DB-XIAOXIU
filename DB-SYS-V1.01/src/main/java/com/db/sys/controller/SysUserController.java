package com.db.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.sys.entity.SysUser;
import com.db.sys.service.SysUserService;

@Controller
@RequestMapping("/user/")
public class SysUserController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@RequestMapping("doUserListUI")
	public String doUserListUI() {
		return "sys/user_list";
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		return new JsonResult(sysUserService.findPageObjects(username, pageCurrent));
	}
	@RequestMapping("doValidById")
	@ResponseBody
	public JsonResult doValidById(Integer id,Integer valid) {
		SysUser user = (SysUser)SecurityUtils.getSubject().getPrincipal();
		System.out.println(user);
		sysUserService.ValidById(id, valid, user.getUsername());
		return new JsonResult("状态修改成功");
	}
	@RequestMapping("doUserEditUI")
	public String doUserEditListUI() {
		return "sys/user_edit";
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysUser sysUser,Integer[] roleIds) {
		sysUserService.saveObject(sysUser, roleIds);
		return new JsonResult("用户创建成功！");
	}
	
	/**
	 * 修改用户需要先登录
	 * @param sysUser
	 * @param roleIds
	 * @return
	 */
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysUser sysUser,Integer[] roleIds) {
		sysUserService.updateObject(sysUser, roleIds);
		return new JsonResult("update success!");
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		Map<String, Object> map = sysUserService.findObjectById(id);
		return new JsonResult(map);
	}
	@RequestMapping("doLogin")
	@ResponseBody
	public JsonResult doLogin(String username,String password,boolean isRememberMe) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken uToken = new UsernamePasswordToken(username,password);
		if(isRememberMe) {
			uToken.setRememberMe(true);
		}
		subject.login(uToken);
		return new JsonResult("login success");
	}
	@RequestMapping("doUpdatePasswordUI")
	public String doUpdatePasswordUI() {
		return "sys/pwd_edit";
	}
	@RequestMapping("doUpdatePassword")
	@ResponseBody
	public JsonResult doUpdatePassword(String pwd,String newPwd,String cfgPwd) {
		sysUserService.updatePassword(pwd, newPwd, cfgPwd);
		return new JsonResult("修改成功");
	}
}






