package com.zzu.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zzu.dataobject.OrderDetail;
import com.zzu.dataobject.OrderMaster;
import com.zzu.dataobject.ProductInfo;
import com.zzu.dto.OrderDTO;
import com.zzu.enums.ResultEnum;
import com.zzu.repository.OrderDetailRepository;
import com.zzu.repository.OrderMasterRepository;
import com.zzu.utils.KeyUtil;

import exception.SellException;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	@Override
	//创建订单 一个主订单要有多个订单详情 ，这个方法的目的是创建多个订单详情的记录放到数据库中，一开始的orderdetail并没有在
	//数据库中，因为只有经过了repository的save方法后才能放到数据库中去
	public OrderDTO create(OrderDTO orderDTO) {
		 String orderId=KeyUtil.genUniqueKey();
		

//A container object which may or may not contain a non-null value.If a value is present, 
		//isPresent() will return true and get() will return the value. 
        BigDecimal orderAmount=new BigDecimal(0);//定义一个总价
        //查询每一件商品   前端只传商品id和商品数量
		for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
			Optional<ProductInfo> productInfo=productService.findOne(orderDetail.getProductId());
			//判断商品是否存在
			if(productInfo.isPresent()==false) throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		
		//计算总价   optional类型的如果用get方法要先用一下orElse()
		    orderAmount =productInfo.orElse(null).getProductPrice()
                .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                .add(orderAmount);//相当于orderAmount=orderAmount+,,
			
		  
		    orderDetail.setDetailId(KeyUtil.genUniqueKey());
		    orderDetail.setOrderId(orderId);
		    //设置其他属性
		    BeanUtils.copyProperties(productInfo, orderDetail);
		    orderDetailRepository.save(orderDetail);
		
		}
		//3.写入订单数据库（OrderMaster和order'detail
	    OrderMaster orderMaster=new OrderMaster()	;
	    orderDTO.setOrderId(orderId);
        
        orderMaster.setOrderAmount(orderAmount);
        BeanUtils.copyProperties(orderDTO , orderMaster);
        orderMasterRepository.save(orderMaster);
		//4.扣库存
        
       
		return null;
	}

	@Override
	public OrderDTO findOne(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		// TODO Auto-generated method stub
		
		
		
		return null;
	}

	@Override
	public OrderDTO cancel(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDTO finish(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrderDTO pay(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
