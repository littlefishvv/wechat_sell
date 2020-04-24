package com.zzu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
@Component
public class WechatOpenConfig {

	@Autowired
	private WechatAccountConfig accountConfig;
	//注意，不管是开放平台还是公众平台，用的都是wxopenService这个接口
	@Bean
	public WxMpService wxOpenService(){
		
		WxMpService wxOpenService=new WxMpServiceImpl();
		//下面的方法作为这个set的参数
		wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
		return wxOpenService;
	}
	@Bean 
	public WxMpConfigStorage wxOpenConfigStorage(){
		WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
		wxMpInMemoryConfigStorage.setAppId(accountConfig.getOpenAppId());
		System.out.println("wxopenconfig :  "+accountConfig.getOpenAppId());
		wxMpInMemoryConfigStorage.setSecret(accountConfig.getOpenAppSecret());
		System.out.println(accountConfig.getOpenAppSecret());
		return wxMpInMemoryConfigStorage;
	}
}
