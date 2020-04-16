package com.zzu.controller;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.utils.JsonUtil;
import com.zzu.dto.OrderDTO;
import com.zzu.enums.ResultEnum;
import com.zzu.service.OrderService;
import com.zzu.service.PayService;

import exception.SellException;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    //引入这个的目的是要解决无法读取create.ftl文件错误的e
   // @ResponseBody
	@GetMapping("/create")
	public ModelAndView create(@RequestParam("orderId") String orderId,
			           @RequestParam("returnUrl") String returnUrl,
			           Map<String,Object> map){
		//1.查询订单
		OrderDTO orderDTO=orderService.findOne(orderId);
		if(orderDTO == null) {
           // log.error("【微信下单】订单不存在，orderId={}" , orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
        }
		
		//2.发起支付
		PayResponse payResponse=payService.create(orderDTO);
		
		map.put("payResponse", payResponse);
		
		//这里非但不编码，反而要解码，真奇怪
		map.put("returnUrl", URLDecoder.decode(returnUrl));
		System.out.println("payController--create-returnUrl: "+URLDecoder.decode(returnUrl));
		//这里返回的是pay/create是在templates目录下的ftl文件的内容
		return new ModelAndView("pay/create",map);
	}
	@PostMapping("/notify")
	public ModelAndView notify(@RequestBody String notifyData){
		
		PayResponse payResponse = payService.notify(notifyData);
		//返回给微信处理结果
		System.out.println("notify-payResponse"+JsonUtil.toJson(payResponse));
		//返回给微信处理结果 避免微信一直提醒
		return new ModelAndView("pay/success");
	}
}
