package com.zzu.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzu.converter.OrderForm2OrderDTOConverter;
import com.zzu.dto.OrderDTO;
import com.zzu.enums.ResultEnum;
import com.zzu.form.OrderForm;
import com.zzu.service.OrderService;
import com.zzu.utils.ResultVOUtil;
import com.zzu.viewObject.ResultVO;

import exception.SellException;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;
@RestController
@RequestMapping("/buyer/order")
@Slf4j

public class BuyerOrderController {
	
	
	@Autowired
	private OrderService orderService;
//创建订单    @Valid和BindingResult配套使用，@Valid用在参数前，BindingResult作为校验结果绑定返回。
	@PostMapping("/create")
	public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,BindingResult bindResult){
		if(bindResult.hasErrors()){
			throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindResult.getFieldError().getDefaultMessage());
		}
	//这里要把order FORM转成orderDTo
		OrderDTO orderDTO=OrderForm2OrderDTOConverter.convert(orderForm);
         //如果购物车是空的，就要报错因为下订单不能一件商品都没有
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
           // log.error("【创建订单】购物车不能为空，orderDTO={}" , orderDTO);
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        //然后就可以创建订单了
		OrderDTO result = orderService.create(orderDTO);

        Map<String,String> map = new HashMap<>();
        //resultVo格式 code msg data 这里的 map格式（orderId:orderId）
        map.put("orderId" , result.getOrderId());
        //这里通过success方法设置resultVO  其实就是设置这个data  把map当作data传递过去
        return ResultVOUtil.success(map);
	}
}
