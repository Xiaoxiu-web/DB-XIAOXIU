package com.db.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.common.vo.PageObject;
import com.db.sys.entity.SysRole;
import com.db.sys.service.SysRoleService;
import com.db.sys.vo.SysRoleMenuVo;

@Controller
@RequestMapping("/role/")
public class SysRoleController {
	
	@Autowired
	private SysRoleService sysRoleService;
	@RequestMapping("doRoleListUI")
	public String doRoleListUI() {
		return "sys/role_list";
	}
	@RequestMapping("doFindPageObjects")
	@ResponseBody
	public JsonResult doFindPageObjects(String name,Integer pageCurrent) {
		PageObject<SysRole> roleList = sysRoleService.findPageObjects(name, pageCurrent);
		return new JsonResult(roleList);
	}
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysRoleService.deleteObject(id);
		return new JsonResult("delete ok");
	}
	@RequestMapping("doRoleEditUI")
	public String doRoleEditUI() {
		return "sys/role_edit";
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysRole sysRole,Integer[] menuIds) {
		sysRoleService.saveObject(sysRole, menuIds);
		return new JsonResult("save success!");
	}
	@RequestMapping("doFindObjectById")
	@ResponseBody
	public JsonResult doFindObjectById(Integer id) {
		SysRoleMenuVo vo = sysRoleService.findObjectById(id);
		return new JsonResult(vo);
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysRole sysRole,Integer[] menuIds) {
		sysRoleService.updateObject(sysRole, menuIds);
		return new JsonResult("update seccuess!");
	}
	@RequestMapping("doFindRoles")
	@ResponseBody
	public JsonResult doFindRoles() {
		return new JsonResult(sysRoleService.findObjects());
	}
	@RequestMapping("doCheckExist")
	@ResponseBody
	public JsonResult doCheckExist(String name) {
		sysRoleService.isRoleExist(name);
		return new JsonResult("is ok");
	}
	
}
