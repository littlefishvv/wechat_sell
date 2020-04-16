package com.zzu.controller;


import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zzu.enums.ResultEnum;

import exception.SellException;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
//和RestController有什么区别呢？
@Controller
@RequestMapping("/wechat")
public class WeChatController {
	@Autowired
    private WxMpService wxMpService;
	@GetMapping("/authorize")
	//returnUrl的用处就是最后经过得到openid时，能够返回 returnUrl/?openid=""这个链接。
    public String authorize(@RequestParam("returnUrl") String returnUrl){
    	
    	//1.配置
    	
    	//2.调用
		//第一个参数是用户授权后的重定向链接，无需urlencode，第二个是scope.第三个是state
		String url="http://wechat-gsy.natapp1.cc/sell/wechat/userInfo/";
		String redirectUrl=wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO,URLEncoder.encode(returnUrl) );
		System.out.println("authorize--redirectUrl:"+redirectUrl);
		//这个redirect是spring中的规定，带了这个就意味着重定向
		return "redirect:"+redirectUrl;
    }
	//如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
	@GetMapping("/userInfo")
	public String userInfo(@RequestParam("code") String code,
			             @RequestParam("state") String returnUrl){
		WxMpOAuth2AccessToken accessToken=new WxMpOAuth2AccessToken();
		try {
			//code说明 ： code作为换取access_token的票据
			 accessToken=wxMpService.oauth2getAccessToken(code);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			throw new SellException(ResultEnum.WECHAT_MP_ERROR.getCode(),e.getError().getErrorMsg());
		}
		String openid=accessToken.getOpenId();
		System.out.println("userInfo--openid:"+openid);
		System.out.println("userInfo--returnUrl:"+returnUrl);
		return "redirect:"+returnUrl+"?openid="+openid;
	}
}
