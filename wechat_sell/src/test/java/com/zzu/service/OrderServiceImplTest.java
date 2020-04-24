package com.zzu.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;


import com.zzu.dataobject.OrderDetail;
import com.zzu.dto.OrderDTO;
import com.zzu.enums.OrderStatusEnum;
import com.zzu.enums.PayStatusEnum;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
	  @Autowired
	    private OrderServiceImpl orderService;

	    final static String BUYER_OPENID = "110110110";

	    final static String ORDER_ID = "150800666666370209";
	    //测试创建订单的功能
	    //@Test
	    public void create() throws Exception {

	        OrderDTO orderDTO = new OrderDTO();
	        orderDTO.setBuyerOpenid(BUYER_OPENID);
	        orderDTO.setBuyerPhone("123456789012");
	        orderDTO.setBuyerAddress("广州从师科技有限公司");
	        orderDTO.setBuyerName("范先生");
            //购物车
	        List<OrderDetail> orderDetailList = new ArrayList<>();
	        OrderDetail o1 = new OrderDetail();
	        o1.setProductId("2");
	        o1.setProductQuantity(2);
	        OrderDetail o2 = new OrderDetail();
	        o2.setProductId("1");
	        o2.setProductQuantity(2);


	        orderDetailList.add(o1);
	        orderDetailList.add(o2);
	      
	        orderDTO.setOrderDetailList(orderDetailList);

	        OrderDTO result = orderService.create(orderDTO);
	    
	         Assert.assertNotNull(result);
}
	    @Test
	    public void findOne() throws Exception {
	        OrderDTO orderDTO = orderService.findOne("123456");
	   
	        Assert.assertNotNull(orderDTO);
	    }
	    
	    @Test
	    public void findList() throws Exception {
	        Pageable pageable =PageRequest.of(0 , 5);
	        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID , pageable);
	      
	        Assert.assertNotNull(orderDTOPage);
	    }
	   // @Test
	    public void cancel() throws Exception {
	        OrderDTO orderDTO = orderService.findOne("1508001584066370209");
	      
	        OrderDTO result = orderService.cancel(orderDTO);
	        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode() , result.getOrderStatus());
	    }
	   // @Test
	    public void finish() throws Exception {
	        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
	        OrderDTO result = orderService.finish(orderDTO);
	        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode() , result.getOrderStatus());
	    }
	    @Test
	    public void pay() throws Exception {
	        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
	        OrderDTO result = orderService.pay(orderDTO);
	        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(), result.getPayStatus());
	    }
	    @Test
	    public void list(){
	    	PageRequest request = PageRequest.of(0,2);
	    	Page<OrderDTO> orderDTOPage=orderService.findList(request);
	    	Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
	    }

}