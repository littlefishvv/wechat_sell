package com.zzu.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.zzu.config.ProjectUrlConfig;

import exception.SellerAuthorizeException;

@ControllerAdvice
public class SellerExceptionHandler {
	@Autowired
	private ProjectUrlConfig projectUrlConfig;
	//拦截登陆异常
	@ExceptionHandler(value=SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
    	return new ModelAndView("redirect:".concat("http://sell.springboot.cn")
    			                            .concat("/sell/wechat/qrAuthorize")
    			                            .concat("?returnUrl=")
    			                            .concat(projectUrlConfig.getSell())
    			                            .concat("/sell/seller/login"));
    }
	
}
