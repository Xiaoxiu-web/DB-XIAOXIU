package com.db.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.db.common.vo.JsonResult;
import com.db.common.vo.Node;
import com.db.sys.entity.SysMenu;
import com.db.sys.service.SysMenuService;

@RequestMapping("/menu/")
@Controller
public class SysMenuController {
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@RequestMapping("doMenuListUI")
	public String doMenuListUI() {
		return "sys/menu_list";
	}
	@ResponseBody
	@RequestMapping("doFindObjects")
	public JsonResult doFindObjects() {
		return new JsonResult(sysMenuService.findMenuObjects());
	}
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		sysMenuService.deleteObject(id);
		return new JsonResult("delete success!");
	}
	@RequestMapping("doMenuEditUI")
	public String doMenuEditUI() {
		System.out.println("进入菜单添加页面");
		return "sys/menu_edit";
	}
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		List<Node> ZtreeNode = sysMenuService.findZtreeMenuNodes();
		for (Node node : ZtreeNode) {
			System.out.println(node.toString());
		}
		return new JsonResult(ZtreeNode);
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doSaveObject(SysMenu sysMenu) {
		sysMenuService.insertObject(sysMenu);
		return new JsonResult("save seccuess!");
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu sysMenu) {
		sysMenuService.updateObject(sysMenu);
		return new JsonResult("update seccuess!");
	}
}
