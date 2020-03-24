package com.zzu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zzu.dataobject.ProductCategory;
import com.zzu.repository.ProductCategoryRepository;
@Service
public class CategoryServiceImpl implements CategoryService{
@Autowired
private ProductCategoryRepository repository;

@Override
public Optional<ProductCategory> findOne(Integer categoryId) {
	// TODO Auto-generated method stub
	return repository.findById(categoryId);
}

@Override
public List<ProductCategory> findAll() {
	// TODO Auto-generated method stub
	return repository.findAll();
}

@Override
public List<ProductCategory> findCategoryTypeIn(List<Integer> categoryTypeIn) {
	// TODO Auto-generated method stub
	return repository.findByCategoryTypeIn(categoryTypeIn);
}

@Override
public ProductCategory save(ProductCategory productCategory) {
	// TODO Auto-generated method stub
	return repository.save(productCategory);
}

}
