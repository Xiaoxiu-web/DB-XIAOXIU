package com.db.common.vo;

import java.io.Serializable;
/**
 * 添加菜单中树节点关系对象
 * @author ThinkPad
 *
 */
public class Node implements Serializable{
	
	private static final long serialVersionUID = 6169404874469160004L;
	private Integer id;
	private String name;
	private Integer parentId;
	
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
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Override
	public String toString() {
		return "Node [id=" + id + ", name=" + name + ", parentId=" + parentId + "]";
	}
	
}
