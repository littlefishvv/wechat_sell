package com.zzu.enums;

public enum ProductStatusEnum implements CodeEnum{
	//这其实就是调用下面的构造函数
     up(0,"上架"),
	downj(1,"下架")
	
	;
    private Integer code;
    private String message;
    
	public Integer getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	private ProductStatusEnum(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	
}
