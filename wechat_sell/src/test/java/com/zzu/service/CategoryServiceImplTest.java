package com.zzu.service;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.zzu.dataobject.ProductCategory;

/**
 * Created by Administrator on 2017/10/13 0013.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> categories = categoryService.findAll();

        Assert.assertNotEquals(0 , categories.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        List<ProductCategory> categories = categoryService.findCategoryTypeIn(Arrays.asList(1,2,3,4));
        for(int i=0;i<categories.size();i++){
        	System.out.println(categories.get(i).toString());
        }
        
        Assert.assertNotEquals( 0 , categories.size());

    }

    @Test
    @Transactional
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(6);
        productCategory.setCategoryName("男生专区");

        productCategory = categoryService.save(productCategory);
        Assert.assertNotEquals(null , productCategory);

    }

}