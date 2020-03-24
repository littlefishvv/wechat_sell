package com.zzu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zzu.dataobject.ProductCategory;
//这其实就是DAO层，只不过这里叫repository（仓库）
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
    //注，方法名必须要这样写
	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
