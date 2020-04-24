package com.zzu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.zzu.dataobject.ProductInfo;
import com.zzu.dto.CartDTO;

public interface ProductService {
	Optional<ProductInfo> findOne(String productId);
List<ProductInfo> findUpAll();
//按页查询
Page<ProductInfo> findAll(Pageable page);
ProductInfo save(ProductInfo productInfo);
//按页查询
Page<ProductInfo> findByPage(Pageable pageable);
/**减少库存.*/
//这里传的参数是购物车
void decreaseStock(List<CartDTO> cartDTOList);

/**增加库存.*/  //增减库存都是一批一批的，因为一个订单对应多个商品详情
void increaseStock(List<CartDTO> cartDTOList);

/**商品下架.*/
ProductInfo offSale(String productId);

/**商品上架.*/
ProductInfo onSale(String productId);

}
