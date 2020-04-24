package com.zzu.service;

import com.zzu.dto.OrderDTO;

public interface PushMessage {
    //订单状态变更消息
	void orderStatus(OrderDTO orderDTO);
}
