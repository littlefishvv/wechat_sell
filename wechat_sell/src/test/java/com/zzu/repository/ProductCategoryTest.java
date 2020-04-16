package com.zzu.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzu.dataobject.ProductCategory;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryTest {
	@Autowired
	private ProductCategoryRepository repository;
	@Test
	public void findOne(){
		Optional<ProductCategory> pc=repository.findById(new Integer(1));
		System.out.println(pc.toString());
	} 
	@Test
	public void saveTest(){
	    ProductCategory product =new ProductCategory();
	    product.setCategoryName("男装");
	    product.setCategoryType(12);
	    repository.save(product);
	}
	@Test
	public void findByCategoryTypeInTest(){
		List<Integer> list=new ArrayList<>();
		list.add(1);
		list.add(3);
		List<ProductCategory> result=repository.findByCategoryTypeIn(list);
		Assert.assertNotEquals(0, result.size());
	}
	
}
