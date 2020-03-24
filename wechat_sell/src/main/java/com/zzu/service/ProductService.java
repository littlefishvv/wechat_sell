package com.zzu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zzu.dataobject.ProductInfo;

public interface ProductService {
	Optional<ProductInfo> findOne(String productId);
List<ProductInfo> findUpAll();
//按页查询
Page<ProductInfo> findAll(Pageable page);
ProductInfo save(ProductInfo productInfo);
//按页查询
Page<ProductInfo> findByPage(Pageable pageable);
//加库存
//void increaseStock(List<>);
//和减库存

}
