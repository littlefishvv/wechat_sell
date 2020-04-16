package com.zzu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.zzu.dto.OrderDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PayServiceImplTest {
    private static final String ORDER_NAME="微信点餐订单";
	@Autowired
	private PayService payService;
	@Autowired
	private OrderService orderService;
	@Test
	public void create() throws Exception{
		
		OrderDTO orderDTO=orderService.findOne("1585058376959963261");
		payService.create(orderDTO);
        
	}
}
