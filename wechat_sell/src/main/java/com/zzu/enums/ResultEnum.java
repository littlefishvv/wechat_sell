package com.zzu.enums;

public enum ResultEnum {
	 SUCCESS(0,"成功"),
	 ORDER_CANCLE_SUCCESS(22,"订单取消成功"),
	 PARAM_ERROR(1,"参数不正确"),
     PRODUCT_NOT_EXIST(10,"商品不存在"),
     PRODUCT_STOCK_ERROR(11,"库存不正确"),
     ORDERDETAIL_NOT_FOUND(12,"订单详情不存在"), 
     ORDER_STATUS_ERROR(13,"订单状态不正确"),
     ORDER_UPDATE_ERROR(14,"订单更新失败"),
     ORDER_PAY_STATUS_ERROR(15,"订单支付状态异常"),
     ORDER_DETAIL_ERROR(16,"订单详情为空"),
     CART_EMPTY(18,"购物车为空"), 
     ORDER_OWNER_ERROR(19,"该订单不属于当前用户"),
     ORDER_NOT_EXISTS(20,"订单不存在"), 
     WECHAT_MP_ERROR(21,"微信公众账号错误"), 
     WXPAY_NOTIFY_VERITY_ERROR(22,"微信异步通知金额校验不通过"), 
     ORDER_UPDATE_FAIL(23,"订单状态更新错误"),
     ORDER_FINISH_SUCCESS(24,"订单完结成功"),
     PRODUCT_STATUS_ERROR(23,"商品状态不正确"),
     LOGIN_FAIL(25,"登陆失败，登陆信息不正确"),
     LOGOUT_SUCCESS(26,"登出成功");
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
