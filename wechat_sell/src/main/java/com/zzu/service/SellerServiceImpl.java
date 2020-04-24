package com.zzu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzu.dataobject.SellerInfo;
import com.zzu.repository.SellerInfoRespository;
@Service
public class SellerServiceImpl implements SellerService {
    
	@Autowired
	private SellerInfoRespository repository;
	@Override
	public SellerInfo findSellerInfoByOpenid(String openid) {
		// TODO Auto-generated method stub
		return repository.findByOpenid(openid);
	}

	
}
