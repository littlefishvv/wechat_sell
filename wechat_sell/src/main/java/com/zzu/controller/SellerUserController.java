package com.zzu.controller;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzu.config.ProjectUrlConfig;
import com.zzu.constant.CookieConstant;
import com.zzu.constant.RedisConstant;
import com.zzu.dataobject.SellerInfo;
import com.zzu.enums.ResultEnum;
import com.zzu.service.SellerService;
import com.zzu.utils.CookieUtil;

@Controller
@RequestMapping("/seller")
public class SellerUserController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrl;
	@GetMapping("/login")
	public ModelAndView login(@RequestParam("openid") String openid,
			          HttpServletResponse response,
			          Map<String,Object> map){
		//1.openid去和数据库里的数据匹配  相当于登陆凭证  也就是keyvalue中的key
		SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid("oTgZpwbIFdr-HQ9fYd2P-xtm6jUM");
        if (sellerInfo == null) {
        	System.out.println("login--sellerInfo错误");
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url", "/sell/seller/order/list");
            return new ModelAndView("common/error");
        }
		//2.设置token至redis
        String token=UUID.randomUUID().toString();
        Integer expire=RedisConstant.EXPIRE;
        //第一个是redis的key（格式化以token_为开头），第二个是value。第三个是过期时间。第四个参数是时间的单位
		redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid,expire,TimeUnit.SECONDS);
		
		//3.设置token至cookie 
		//第一个参数name，第二个参数value值，就是uuid
		CookieUtil.set(response, CookieConstant.TOKEN, token, CookieConstant.EXPIRE);
		//注意：地址跳转最好用绝对地址而不是相对地址
        return new ModelAndView("redirect:"+projectUrl.getSell()+"/sell/seller/order/list");
	}
	//登出是不要传参数的
	@GetMapping("/logout")
	public ModelAndView loggut(HttpServletRequest request,
			           HttpServletResponse response,
			           Map<String,Object> map) {
		//登出就是把redis和cookie的值给清除掉 
		//1.从cookie里查询 查询name=token的cookie
		Cookie cookie=CookieUtil.get(request,CookieConstant.TOKEN);
		//2.清除redis
		if(cookie!=null){
			redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
		}
		//3.清除cookie
		//value值设为null，清除时间设置为0
		CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
		map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMsg());
		map.put("url", "/sell/seller/order/list");
		return new ModelAndView("common/success",map);
	}
	
}
