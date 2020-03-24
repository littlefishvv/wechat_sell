package com.zzu.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzu.enums.*;
import com.zzu.dataobject.ProductInfo;
import com.zzu.repository.ProductInfoRepository;
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {
@Autowired
ProductServiceImpl productService;
@Test
public void findOne() throws Exception {
	Optional<ProductInfo> productInfo = productService.findOne("123456");
	//注 optional如果想用里面的方法，那么必须要实现orelse方法，即如果不存在，返回orelse里面的东西，否则返回自己的方法
    Assert.assertEquals("123456", productInfo.orElse(null).getProductId());
}

@Test
public void findUpAll() throws Exception {
    List<ProductInfo> productInfos = productService.findUpAll();
    Assert.assertNotEquals(0 , productInfos.size());
}

@Test
public void findByPage() throws Exception {
    PageRequest pageRequest=PageRequest.of(0, 2);
    Page<ProductInfo> productInfos = productService.findByPage(pageRequest);
    Assert.assertNotNull(productInfos);
}

@Test
public void save() throws Exception {
	ProductInfo productInfo=new ProductInfo();
	productInfo.setProductId("12345");
    productInfo.setProductName("肠粉");
    productInfo.setProductDescription("好吃");
    productInfo.setProductIcon("http://xxxxx");
    productInfo.setProductPrice(new BigDecimal(3.2));
    productInfo.setProductStock(100);
    productInfo.setProductType(new Integer(5));
    productInfo.setProductStatus(ProductStatusEnum.up.getCode());
    System.out.println(ProductStatusEnum.up.getCode());
    ProductInfo result = productService.save(productInfo);
    Assert.assertNotNull(result);

   
}
}
