package com.zzu.service;

import java.util.List;
import java.util.Optional;

import com.zzu.dataobject.ProductCategory;

public interface CategoryService {
	Optional<ProductCategory> findOne(Integer categoryId);
  List<ProductCategory> findAll();
  List<ProductCategory> findCategoryTypeIn(List<Integer> categoryTypeIn);
  ProductCategory save(ProductCategory productCategory);
}
