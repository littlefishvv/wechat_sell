package com.zzu.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzu.dataobject.SellerInfo;

public interface SellerInfoRespository extends JpaRepository<SellerInfo, String> {

	SellerInfo findByOpenid(String openid);

}
