package com.zzu.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzu.config.WechatAccountConfig;
import com.zzu.dto.OrderDTO;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

@Service
public class PushMessageImpl implements PushMessage{
    @Autowired
    private WxMpService wxMpService;
	@Autowired
	private WechatAccountConfig accountConfig;
	
	
	@Override
	public void orderStatus(OrderDTO orderDTO) {
		// TODO Auto-generated method stub
		WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
		templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));
		//这个setToUser设置的openid是指测试号的，而不是师兄干货那个
		templateMessage.setToUser("ogNZqt-6ZTAn2dTcb9F1xLDozXAY");
		List<WxMpTemplateData> data=Arrays.asList(
				new WxMpTemplateData("first","亲，记得收货"),
				new WxMpTemplateData("keyword1","微信点餐"),
				new WxMpTemplateData("keyword2","18638730828"),
				new WxMpTemplateData("keyword3",orderDTO.getOrderId()),				
		        new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMsg()),
		        new WxMpTemplateData("keyword5","￥"+orderDTO.getOrderAmount()),
		        new WxMpTemplateData("remark","￥"+"欢迎再次光临")		        
		        );
		templateMessage.setData(data);
		try {
			wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			System.out.println("微信模板消息发送失败");
		}
		
	}

	
}
