package com.zzu.service;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zzu.converter.*;
import com.zzu.dataobject.OrderDetail;
import com.zzu.dataobject.OrderMaster;
import com.zzu.dataobject.ProductInfo;
import com.zzu.dto.*;
import com.zzu.enums.ResultEnum;
import com.zzu.repository.OrderDetailRepository;
import com.zzu.repository.OrderMasterRepository;
import com.zzu.utils.KeyUtil;
import com.zzu.enums.*;
import exception.SellException;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private ProductService productService;
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private OrderMasterRepository orderMasterRepository;
	@Autowired 
	private PayService payService;
	@Override
	//创建订单 一个主订单要有多个订单详情 ，这个方法的目的是创建多个订单详情的记录放到数据库中，一开始的orderdetail并没有在
	//数据库中，因为只有经过了repository的save方法后才能放到数据库中去
	
	//这个是事务注解，通过这个注解可以使下面的方法变成事务，下面的操作要么全做，要么全不做，当有一步有异常时，事务会回滚
    @Transactional
	public OrderDTO create(OrderDTO orderDTO) {
		 String orderId=KeyUtil.genUniqueKey();
		

//A container object which may or may not contain a non-null value.If a value is present, 
		//isPresent() will return true and get() will return the value. 
        BigDecimal orderAmount=new BigDecimal(0);//定义一个总价
        //查询每一件商品   前端只传商品id和商品数量
		for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
			Optional<ProductInfo> productInfo=productService.findOne(orderDetail.getProductId());
			//判断商品是否存在
			System.out.println(productInfo.isPresent());
			if(productInfo.isPresent()==false) throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
		
		//计算总价   optional类型的如果用get方法要先用一下orElse()
		    orderAmount =productInfo.orElse(null).getProductPrice()
                .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                .add(orderAmount);//相当于orderAmount=orderAmount+,,
		    System.out.println(productInfo.orElse(null).getProductIcon());
		    
		  //这里有个极大的误区，因为下面copy时不报错，则productInfo会忘记加orElse(null)导致复制失败，实际上只要是optional类型的
		    //无论再什么情况下使用总是要加上orElse()，否则所有方法都不会执行，
		       BeanUtils.copyProperties(productInfo.orElse(null), orderDetail);
		       System.out.println(orderDetail.getProductIcon());
		    orderDetail.setDetailId(KeyUtil.genUniqueKey());
		    orderDetail.setOrderId(orderId);
		   
		   System.out.println(orderDetail.getProductIcon());
		    orderDetailRepository.save(orderDetail);
		
		}
		//3.写入订单数据库（OrderMaster
	    OrderMaster orderMaster=new OrderMaster()	;
	    orderDTO.setOrderId(orderId);
	    BeanUtils.copyProperties(orderDTO , orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);
		//4.扣库存   CartDTO只包含数量和id
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId() , e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
		return orderDTO;
	}

	@Override
	//通过orderid来找orderDTO
	public OrderDTO findOne(String orderId) {
		// TODO Auto-generated method stub
        //只要遇到optional的对象，在任何情况下都要加orelse，否则会当作null来判断
		Optional<OrderMaster> orderMaster=orderMasterRepository.findById(orderId);
		System.out.println("orderId:"+orderId);
		if(orderMaster.isPresent()==false) throw new SellException(ResultEnum.ORDER_NOT_EXISTS);

        //2.查看订单详情  通过查看订单ID找到订单详情列表
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
       if(orderDetailList.size()==0) {
           throw new SellException(ResultEnum.ORDERDETAIL_NOT_FOUND);
       }

       OrderDTO orderDTO = new OrderDTO();
       BeanUtils.copyProperties(orderMaster.orElse(null) , orderDTO);
       orderDTO.setOrderDetailList(orderDetailList);
		
		return orderDTO;
	}

	@Override
	//通过openid查一页一页的orderDTO
	public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
		// TODO Auto-generated method stub
		 //1.查找用户订单
        Page<OrderMaster> orderMasterList = orderMasterRepository.findByBuyerOpenid(buyerOpenid , pageable);

        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterList.getContent());

        return new PageImpl<OrderDTO>(orderDTOList , pageable , orderMasterList.getTotalElements());
	}

	@Override//取消订单 返回的是取消后的订单
	public OrderDTO cancel(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		OrderMaster orderMaster=new OrderMaster();
		
		//先判断订单的状态如果是已取消或者是完结状态则不能被取消  NEW是指新订单，处于已
		
		if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())) {
         // Logger.error("【取消订单】 订单状态不正确：orderId={},orderStatus={}" , orderDTO.getOrderId() , orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
		//修改订单状态  注意这里是对order DTO进行修改，然后再进行拷贝
		orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
		BeanUtils.copyProperties(orderDTO, orderMaster);
		OrderMaster updateResult=orderMasterRepository.save(orderMaster);
		//如果updateResult为空，说明更新失败
		if(updateResult==null){
			throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
		}
		//返还库存
		List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId() , e.getProductQuantity()))
                .collect(Collectors.toList());
		
		 productService.increaseStock(cartDTOList);
		//如果已支付，需要退款
		 if(PayStatusEnum.SUCCESS.getCode().equals(orderDTO.getPayStatus())) {
	         //TODO   
			 payService.refund(orderDTO);
	        }

	        return orderDTO;
	    
	}

	@Override
	public OrderDTO finish(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		//1判断订单状态 只有新下单的状态可以被完结
		//判断订单状态
        if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())) {
          //  log.error("【完结订单】 订单状态不正确：orderId={},orderStatus={}" , orderDTO.getOrderId() , orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }


        //更新订单
        OrderMaster orderMaster = new OrderMaster();
        //先改DTO
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        //然后再复制//2修改状态
        BeanUtils.copyProperties(orderDTO , orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if( null == updateResult) {
         //   log.error("【取消订单】更新订单状态失败：orderMaster={}" , orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
		
		
		return orderDTO;
	}

	@Override
	@Transactional 
	public OrderDTO pay(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		//先判断订单状态
		 if(!OrderStatusEnum.NEW.getCode().equals(orderDTO.getOrderStatus())) {
	          //  log.error("【完结订单】 订单状态不正确：orderId={},orderStatus={}" , orderDTO.getOrderId() , orderDTO.getOrderStatus());
	            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
	        }
		//判断支付状态
		if(!PayStatusEnum.WAIT.getCode().equals(orderDTO.getPayStatus())) {
         //   log.error("【支付订单】 订单支付状态不正确：orderId={},orderStatus={}" , orderDTO.getOrderId() , orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //更新订单  如果订单是新订单，并且未支付，则可以修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO , orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if( null == updateResult) {
          //  log.error("【支付订单】更新订单支付状态失败：orderMaster={}" , orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        return orderDTO;
		
	}

	@Override
	public Page<OrderDTO> findList(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
          //  log.error("【订单支付完成】订单状态不正确, orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
          //  log.error("【订单支付完成】订单支付状态不正确, orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if (updateResult == null) {
           // log.error("【订单支付完成】更新失败, orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }
}
