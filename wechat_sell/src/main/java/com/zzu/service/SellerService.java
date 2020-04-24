package com.zzu.service;

import com.zzu.dataobject.SellerInfo;

public interface SellerService {

    //查询卖家端信息
	SellerInfo findSellerInfoByOpenid(String openid);
}
