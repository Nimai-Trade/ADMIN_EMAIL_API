package com.nimai.lc.payload;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class GenericResponse<T> implements Serializable {
	private String errMessage;
	private T data;
	private List list;
	private String errCode;
	private String status;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
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

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}