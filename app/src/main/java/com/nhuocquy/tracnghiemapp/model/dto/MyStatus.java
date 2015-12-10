package com.nhuocquy.tracnghiemapp.model.dto;

public class MyStatus {
	public static int CODE_SUCCESS = 1;
	public static int CODE_FAIL = 0;
	public static String MESSAGE_SUCCESS = "success";
	public static String MESSAGE_FAIL = "fail";
	private int code;
	private String message;
	private Object obj;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	@Override
	public String toString() {
		return "MyStatus [code=" + code + ", message=" + message + ", obj=" + obj + "]";
	}

}
