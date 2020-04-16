package com.zzu.service;

import com.zzu.dto.OrderDTO;

public interface BuyerService {

	//查询订单
	OrderDTO findOrderOne(String openId,String orderId);
	//取消订单
	OrderDTO cancleOrderOne(String openId,String orderId);

}
