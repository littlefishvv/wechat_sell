package com.zzu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
@Component
public class WechatMpConfig {
    @Autowired
    WechatAccountConfig wechatAccountConfig;
	@Bean
	public WxMpService wxMpService(){
		WxMpService wxMpService=new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
		return wxMpService;
	}
	//这个是进行微信授权时的设置
	@Bean
	public WxMpConfigStorage wxMpConfigStorage(){
		WxMpInMemoryConfigStorage wxMpConfigStorage=new WxMpInMemoryConfigStorage();
		wxMpConfigStorage.setAppId(wechatAccountConfig.getMpAppId());
		wxMpConfigStorage.setSecret(wechatAccountConfig.getMpAppSecret());
		return wxMpConfigStorage;
	}
	@Bean //使用了@Bean.这样在其他地方就能使用Autowired了
	public BestPayServiceImpl bestpayservice(){
		BestPayServiceImpl bestPayService=new BestPayServiceImpl();
		bestPayService.setWxPayH5Config(wxPayH5Config());
		return bestPayService;
		
	}
	//这个是进行微信支付时的设置
	@Bean
	public WxPayH5Config wxPayH5Config(){
		WxPayH5Config wxPayH5Config = new WxPayH5Config();
        wxPayH5Config.setAppId("wxd898fcb01713c658");
        wxPayH5Config.setAppSecret(wechatAccountConfig.getMpAppSecret());
        wxPayH5Config.setKeyPath(wechatAccountConfig.getKeyPath());
        wxPayH5Config.setMchId(wechatAccountConfig.getMchId());
        wxPayH5Config.setMchKey(wechatAccountConfig.getMchKey());
        wxPayH5Config.setNotifyUrl(wechatAccountConfig.getNotifyUrl());
        return wxPayH5Config;
	}
}
