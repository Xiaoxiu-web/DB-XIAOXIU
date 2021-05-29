package com.db.sys.vo;

import java.io.Serializable;
import java.util.List;

public class SysRoleMenuVo implements Serializable{

	private static final long serialVersionUID = -50964583630538965L;
	
	private Integer id;
	private String name;
	private String note;
	private List<Integer> menuIds;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<Integer> getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(List<Integer> menuIds) {
		this.menuIds = menuIds;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "SysRoleMenuVo [id=" + id + ", name=" + name + ", note=" + note + ", menuIds=" + menuIds + "]";
	}
}
