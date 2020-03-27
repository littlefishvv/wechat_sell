package com.zzu.viewObject;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ProductVO {
    public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(Integer categoryType) {
		this.categoryType = categoryType;
	}
	public List<ProductInfoVO> getProductInfoVOList() {
		return productInfoVOList;
	}
	public void setProductInfoVOList(List<ProductInfoVO> productInfoVOList) {
		this.productInfoVOList = productInfoVOList;
	}
	//通过这个注解，可以实现虽然这里是categoryName 但返回前端时是name
	@JsonProperty("name")
	private String categoryName;
	@JsonProperty("type")
	private Integer categoryType;
	@JsonProperty("foods")
	private List<ProductInfoVO> productInfoVOList;
	
	
}
