package com.zzu.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
@Data
@Component
@ConfigurationProperties(prefix="wechat")
public class WechatAccountConfig {
//公众平台账号 用于授权
private String mpAppId;
private String mpAppSecret;
//开放平台账号
private String openAppId;
private String openAppSecret;

public String getOpenAppId() {
	return openAppId;
}
public void setOpenAppId(String openAppId) {
	this.openAppId = openAppId;
}
public String getOpenAppSecret() {
	return openAppSecret;
}
public void setOpenAppSecret(String openAppSecret) {
	this.openAppSecret = openAppSecret;
}
//商户号
private String mchId;
//商户密钥
private String mchKey;
//商品证书路径
private String keyPath;

//异步通知地址
private String notifyUrl;

//微信模板消息推送
private Map<String,String> templateId;

public Map<String, String> getTemplateId() {
	return templateId;
}
public void setTemplateId(Map<String, String> templateId) {
	this.templateId = templateId;
}
public String getNotifyUrl() {
	return notifyUrl;
}
public void setNotifyUrl(String notifyUrl) {
	this.notifyUrl = notifyUrl;
}
public String getMchId() {
	return mchId;
}
public void setMchId(String mchId) {
	this.mchId = mchId;
}
public String getMchKey() {
	return mchKey;
}
public void setMchKey(String mchKey) {
	this.mchKey = mchKey;
}
public String getKeyPath() {
	return keyPath;
}
public void setKeyPath(String keyPath) {
	this.keyPath = keyPath;
}

public String getMpAppId() {
	return mpAppId;
}
public void setMpAppId(String myAppId) {
	this.mpAppId = myAppId;
}
public String getMpAppSecret() {
	return mpAppSecret;
}
public void setMpAppSecret(String myAppSecret) {
	this.mpAppSecret = myAppSecret;
}
}
