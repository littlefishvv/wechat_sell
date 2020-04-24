package com.zzu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzu.dto.OrderDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageTest {

	@Autowired
	private PushMessage pushMessage;
	
	@Autowired
	private OrderService orderService;

	 @Test 
	 public void orderStatus() throws Exception {

	   OrderDTO orderDTO = orderService.findOne("1587305233161729262");
		        pushMessage.orderStatus(orderDTO);
		    }	 	     	    
	
}
