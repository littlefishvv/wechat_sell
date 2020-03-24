package com.zzu.repository;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzu.dataobject.ProductInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {
@Autowired
private ProductInfoRepository repository;
@Test
public void saveTest(){
	ProductInfo productInfo=new ProductInfo();
	productInfo.setProductId("1234567");
    productInfo.setProductName("肠粉");
    productInfo.setProductDescription("好吃");
    productInfo.setProductIcon("http://xxxxx");
    productInfo.setProductPrice(new BigDecimal(3.2));
    productInfo.setProductStock(100);
    productInfo.setProductType(new Integer(2));

    ProductInfo result = repository.save(productInfo);
    Assert.assertNotNull(result);
	
}
@Test
public void findByProductStatus() throws Exception {
          List<ProductInfo> productInfo =repository.findByProductStatus(new Integer(0));
          Assert.assertNotEquals(0, productInfo.size());
}

}
