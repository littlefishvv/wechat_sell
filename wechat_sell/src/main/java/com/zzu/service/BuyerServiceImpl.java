package com.zzu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzu.dto.OrderDTO;
import com.zzu.enums.ResultEnum;

import exception.SellException;
@Service
public class BuyerServiceImpl implements BuyerService{
    @Autowired
    private OrderService orderService;
	@Override
	public OrderDTO findOrderOne(String openId, String orderId) {
		// TODO Auto-generated method stub
		

        return checkOwner(openId,orderId);
	}

	@Override
	public OrderDTO cancleOrderOne(String openId, String orderId) {
		// TODO Auto-generated method stub
		OrderDTO orderDTO=checkOwner(openId,orderId);
		if(orderDTO==null){
			throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
		}
		//如果订单已存在就调用orderservice 的方法取消就行了
		return orderService.cancel(orderDTO);
	}
	 private OrderDTO checkOwner(String openid , String orderId) {
		     openid= "wxd898fcb01713c658";
	        OrderDTO orderDTO = orderService.findOne(orderId);

	        if(orderDTO == null) {
	            return null;
	        }

	       /* if(!openid.equals(orderDTO.getBuyerOpenid())) {
	           // log.error("【查找订单】openid不一致，openid={}，orderId={}" , openid , orderId);
	        	System.out.println("openid:"+openid);
	        	System.out.println("openid:"+orderDTO.getBuyerOpenid());
	            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
	        }
*/
	        return orderDTO;
	    }
	
}
