package com.zzu.repository;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzu.dataobject.OrderMaster;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {
    private static final String OPENID = "123456";
	@Autowired
    private OrderMasterRepository orderMasterRepository;
    @Test
    public void saveTest() {
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("123456");
        orderMaster.setBuyerName("范先生");
        orderMaster.setBuyerAddress("东风东路699广东港澳中心403");
        orderMaster.setBuyerPhone("15626131368");
        orderMaster.setBuyerOpenid("123456");
        orderMaster.setOrderAmount(new BigDecimal(100));
        OrderMaster result = orderMasterRepository.save(orderMaster);

        Assert.assertEquals("123456" , result.getBuyerOpenid());
    }
    @Test
    public void findByBuyerOpenidTest() {
    	//of(int page,int size) pagerequest继承自AbstractPageRequest 继承自pageAble
        PageRequest request=PageRequest.of(0,1);
        Page<OrderMaster> result=orderMasterRepository.findByBuyerOpenid(OPENID, request);
        Assert.assertNotEquals(0, result.getTotalElements());
    }

    
}
