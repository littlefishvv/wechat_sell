package com.zzu.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzu.dto.OrderDTO;
import com.zzu.enums.ResultEnum;
import com.zzu.service.OrderService;

import exception.SellException;

@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {
    @Autowired 
    private OrderService orderService;
	//page 第几页 从第一页开始 size是一页调出多少数据 map是返回给模板的
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value="page",defaultValue="1") Integer page,
			                 @RequestParam(value="size",defaultValue="10") Integer size,
			                 Map<String,Object> map){
			//我们传入的是从第1页开始，但接口里用的是第0页 ，所有要查的话先减	1				
		PageRequest request=PageRequest.of(page-1, size);
		Page<OrderDTO> orderDTOPage=orderService.findList(request);
	    map.put("orderDTOPage", orderDTOPage);
	    map.put("currentPage", page);
	    map.put("size", size);
		return new ModelAndView("order/list",map);
		
		
	}
	@GetMapping("/cancel")
	public ModelAndView cancel(@RequestParam("orderId") String orderId,
			                    Map<String,Object> map){
		try{
			OrderDTO orderDTO=orderService.findOne(orderId);
			
			orderService.cancel(orderDTO);
			//除了订单查找异常，那么取消过程中产生的任何异常都在这里捕获
		}catch(SellException e){
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("common/error",map);
		}
		
		map.put("msg", ResultEnum.ORDER_CANCLE_SUCCESS.getMsg());
		map.put("url", "/sell/seller/order/list");
		
		//orderService.cancel(orderDTO);
		return new ModelAndView("common/success");
		
	}
	@GetMapping("/detail")
	public ModelAndView detail(@RequestParam("orderId") String orderId,
		                    Map<String,Object> map){
		OrderDTO orderDTO=new OrderDTO();
		try{
			orderDTO=orderService.findOne(orderId);
		}catch(SellException e){
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("/common/error",map);
		}
		map.put("orderDTO", orderDTO);
		return new ModelAndView("order/detail",map);
	}
	@GetMapping("/finish")
	public ModelAndView finish(@RequestParam("orderId") String orderId,
            Map<String,Object> map){
		OrderDTO orderDTO=new OrderDTO();
		try{
			orderDTO=orderService.findOne(orderId);
			orderService.finish(orderDTO);
		}catch(SellException e){
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/order/list");
			return new ModelAndView("/common/error",map);
		}
		map.put("msg", ResultEnum.CART_EMPTY.getMsg());
		map.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success");
		
	}
	
	

}

