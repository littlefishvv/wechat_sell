package com.zzu.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    //设置cookie
	public static void set(HttpServletResponse response,
			               String name,
			               String value,
			               int maxAge){
		Cookie cookie=new Cookie(name,value);
		cookie.setPath("/");
		cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
		
	}
	//查询获取cookie 通过name查询cookie
	public static Cookie get(HttpServletRequest request,
			               String name){
		
		Map<String,Cookie> cookieMap=readCookieMap(request);
		if(cookieMap.containsKey(name)){
			return cookieMap.get(name);
		}else{
			return null;
		}
	}
	//将cookie封装成map
	private static Map<String,Cookie> readCookieMap(HttpServletRequest request){
		Map<String,Cookie> cookieMap=new HashMap<>();
		//得到的是一个数组
		Cookie[] cookies=request.getCookies();
		//我们通过遍历整个数组获得cookie
		if(cookies!=null){
			for(Cookie cookie:cookies){
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
}
