package com.db.common.vo;

import java.io.Serializable;

/**
 * 在新增用户时，封装数据库中的所有角色信息
 * @author ThinkPad
 *
 */
public class CheckBox implements Serializable{

	private static final long serialVersionUID = 282985653226440750L;
	private Integer id;
	private String name;
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
	@Override
	public String toString() {
		return "CheckBox(封装数据中所有角色信息) [id=" + id + ", name=" + name + "]";
	}
}
