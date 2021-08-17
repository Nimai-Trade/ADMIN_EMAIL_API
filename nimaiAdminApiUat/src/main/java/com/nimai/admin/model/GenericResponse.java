package com.nimai.admin.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class GenericResponse <T> implements Serializable {
	private static final long serialVersionUID = 1L;

private String message;
	
	private T data;
	
	private List list;
	
	private int flag;
	 
	private Map map;
	
	private String errCode ;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}



}
