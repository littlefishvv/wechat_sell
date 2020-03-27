package com.zzu.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zzu.dataobject.ProductInfo;
import com.zzu.dto.CartDTO;
import com.zzu.enums.ProductStatusEnum;
import com.zzu.enums.ResultEnum;
import com.zzu.repository.ProductInfoRepository;

import exception.SellException;
//sercive层不要忘记写service注解
@Service
public class ProductServiceImpl implements ProductService{
    
	@Autowired
	private ProductInfoRepository repository;
	@Override
	//A container object which may or may not contain a non-null value.If a value is present, isPresent() will return true 
	//and get() will return the value. 
	public Optional<ProductInfo> findOne(String productId) {
		// TODO Auto-generated method stub
		return repository.findById(productId);
	}

	@Override
	//findupall是查所有已经上架的商品  down是下架的商品
	public List<ProductInfo> findUpAll() {
		// TODO Auto-generated method stub
		//通过枚举类来进行查询
		return repository.findByProductStatus(ProductStatusEnum.up.getCode());
	}

	@Override
	//用pageAble返回的是一个page对象
	public Page<ProductInfo> findAll(Pageable page) {
		// TODO Auto-generated method stub
		return repository.findAll(page);
	}
	@Override
    public Page<ProductInfo> findByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }
	@Override
	public ProductInfo save(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		return repository.save(productInfo);
	}

	@Override
	@Transactional
	public void decreaseStock(List<CartDTO> cartDTOList) {
		// TODO Auto-generated method stub
		for(CartDTO cartDTO : cartDTOList){
	        Optional<ProductInfo> productInfo=repository.findById(cartDTO.getProductId());
	        if(productInfo.isPresent()==false){
	        	throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
	        }
	       Integer result= productInfo.orElse(null).getProductStock()-cartDTO.getProductQuantity();
	       if(result<0){
	    	   throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
	    	   
	       }
	       productInfo.orElse(null).setProductStock(result);
	        repository.save(productInfo.orElse(null));
		}
	}

	@Override
	@Transactional
	public void increaseStock(List<CartDTO> cartDTOList) {
		// TODO Auto-generated method stub
		for(CartDTO cartDTO : cartDTOList) {
            //查找商品
            Optional<ProductInfo> productInfo = repository.findById(cartDTO.getProductId());
            if(null == productInfo) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer stock = productInfo.orElse(null).getProductStock() + cartDTO.getProductQuantity();
            productInfo.orElse(null).setProductStock(stock);
            repository.save(productInfo.orElse(null));
        }
	}

	@Override
	public ProductInfo offSafe(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductInfo onSafe(ProductInfo productInfo) {
		// TODO Auto-generated method stub
		return null;
	}
    
}
