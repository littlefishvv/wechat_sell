package com.zzu.aspect;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.zzu.constant.CookieConstant;
import com.zzu.constant.RedisConstant;
import com.zzu.utils.CookieUtil;

import exception.SellerAuthorizeException;

@Aspect
@Component
public class SellerAuthorizeAspect {
	@Autowired 
	private StringRedisTemplate redisTemplate;
    //定义切入点，切入点是以seller开头的controller
	@Pointcut("execution(public * com.zzu.controller.Seller*.*(..))"+"&& !execution(public * com.zzu.controller.SellerUserController.*(..))")
	//在切入点之前做下面这个操作
	public void verify(){}
	@Before("verify()")
	public void doVerity(){
		ServletRequestAttributes attributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request=attributes.getRequest();
		//查询cookie
		Cookie cookie= CookieUtil.get(request, CookieConstant.TOKEN);
		if(cookie==null){
			System.out.println("cookie中查不到token");
			throw new SellerAuthorizeException();
		}
		//去redis里查询token所对应的value
		String tokenValue=redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
		if(StringUtils.isEmpty(tokenValue)){
			System.out.println("redis中查不到token");
			throw new SellerAuthorizeException();
		}
	}
	
}
