package com.zzu.enums;

public enum ResultEnum {
	
	 PARAM_ERROR(1,"参数不正确"),
     PRODUCT_NOT_EXIST(10,"商品不存在"),
     PRODUCT_STOCK_ERROR(11,"库存不正确"),
     ORDERDETAIL_NOT_FOUND(12,"订单详情不存在"), 
     ORDER_STATUS_ERROR(13,"订单状态不正确"),
     ORDER_UPDATE_ERROR(14,"订单更新失败"),
     ORDER_PAY_STATUS_ERROR(15,"订单支付状态异常"),
     ORDER_DETAIL_ERROR(16,"订单详情为空"),
     CART_EMPTY(18,"购物车为空"), 
	;
	
	
	
	
    ResultEnum(Integer code, String msg) {		
		this.code = code;
		this.msg = msg;
	}
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
	private Integer code;
    private String msg;
}
