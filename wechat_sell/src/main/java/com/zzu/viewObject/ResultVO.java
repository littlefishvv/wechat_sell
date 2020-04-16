package com.zzu.viewObject;




public class ResultVO<T> {
	//错误码
    private Integer code;
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
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	//提示信息
    private String msg;
    //具体内容
    private T data;
}
