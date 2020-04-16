package com.zzu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/weixin")
public class WeixinController {
    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code){
    	RestTemplate restTemplate=new RestTemplate();
    	System.out.println("进入auto方法");
    	System.out.println("auto-code:"+code);
    	String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx8cb25fa950a26b8e"
    			+ "&secret=0c20506ffd3e9ff0197d2027a311cc6e"
    			+ "&code="+code+"&grant_type=authorization_code";
    	//返回的是一个json格式
    	String response=restTemplate.getForObject(url, String.class);
    	System.out.println("/auto-response"+response);
    }
}
