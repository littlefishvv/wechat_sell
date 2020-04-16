package com.zzu.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.zzu.dto.OrderDTO;
import com.zzu.enums.ResultEnum;
import com.zzu.utils.MathUtil;

import exception.SellException;
@Service
public class PayServiceImpl implements PayService {
	 private static final String ORDER_NAME="微信点餐订单";
	@Autowired
	private BestPayServiceImpl bestPayService;
	@Autowired 
	private OrderService orderService;
	@Override
	public PayResponse create(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		PayRequest payRequest=new PayRequest();
		payRequest.setOpenid(orderDTO.getBuyerOpenid());
		//bigDecimal转成double型
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        System.out.println("payservice-create-request:"+JsonUtil.toJson(payRequest));
        PayResponse payResponse=bestPayService.pay(payRequest);
        System.out.println("payservice-response-create:"+JsonUtil.toJson(payResponse));
        return payResponse;
	}
	@Override
	public PayResponse  notify(String notifyData) {
		// TODO Auto-generated method stub
		//异步通知注意事项  前两个sdk已经做了 我们要做的是第三个 第四步没有用到
				//1.验证签名  （是不是真正微信过来的参数）
				//2.支付的状态（发送的通知不一定是支付成功）
				//3.支付金额
				//4.支付的人和下单的人
		PayResponse payResponse = bestPayService.asyncNotify(notifyData);
		OrderDTO orderDTO =orderService.findOne(payResponse.getOrderId());
		if(orderDTO==null){
			throw new SellException(ResultEnum.ORDER_NOT_EXISTS);
		}
		//判断金额是否一致  类型一致再进行比较 注意0.10和0.1是有可能不相等的
		//bigDecimal转double用.doubleValue()
		if(!MathUtil.equals(orderDTO.getOrderAmount().doubleValue(),payResponse.getOrderAmount() ))
		{
			throw new SellException(ResultEnum.WXPAY_NOTIFY_VERITY_ERROR);
		}
		//修改支付状态 修改完之后要告诉微信你的处理结果
		orderService.paid(orderDTO);
		System.out.println("payservice-notify:"+JsonUtil.toJson(payResponse));
	    
		return payResponse;
	}
	public RefundResponse refund(OrderDTO orderDTO){
		RefundRequest refundRequest=new RefundRequest();
		refundRequest.setOrderId(orderDTO.getOrderId());
		refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
		refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
		System.out.println("RefundResponse--refundRequest: "+JsonUtil.toJson(refundRequest));
		RefundResponse refundResponse=bestPayService.refund(refundRequest);
		System.out.println("RefundResponse: "+JsonUtil.toJson(refundResponse));
		return refundResponse;
	}

	

}
