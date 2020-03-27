package com.zzu.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.zzu.dataobject.OrderMaster;
import com.zzu.dto.OrderDTO;

public class OrderMaster2OrderDTOConverter {
    
	//把单个的order master转乘单个的orderdot
	public static OrderDTO convert(OrderMaster orderMaster) {
		// TODO Auto-generated method stub
		OrderDTO orderDTO=new OrderDTO();
		BeanUtils.copyProperties(orderMaster, orderDTO);
		return orderDTO;
	}
	public static List<OrderDTO> convert(List<OrderMaster> orderMasterList) {
		// TODO Auto-generated method stub
	
		return orderMasterList.stream().map(e -> convert(e)).collect(Collectors.toList());
	}
	


}
