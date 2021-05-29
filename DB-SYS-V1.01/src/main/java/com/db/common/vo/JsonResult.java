package com.db.common.vo;

import java.io.Serializable;

public class JsonResult implements Serializable{
	private static final long serialVersionUID = -4138033536625725437L;
	private int state=1;
	private String message="返回值正常";
	private Object data;
	public JsonResult() {
	}
	public JsonResult(String message) {
		this.message=message;
	}
	public JsonResult(Object data) {
		this.data=data;
	}
	public JsonResult(Throwable e) {
		this.state=0;
		this.message=e.getMessage();
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
