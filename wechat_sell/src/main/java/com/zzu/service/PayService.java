package com.zzu.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.zzu.dto.OrderDTO;

public interface PayService {

	
	PayResponse create(OrderDTO orderDTO);
	PayResponse notify(String notifyDate);
	
	RefundResponse  refund(OrderDTO orderDTO);
}
