package com.zzu.enums;

public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
	FINISHED(1,"完结"),
	CANCEL(2,"已取消"),
	;
	private Integer code;
	private String msg;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
    OrderStatusEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	//构造方法
	
	
}
