package com.zzu.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zzu.dataobject.ProductCategory;
import com.zzu.dataobject.ProductInfo;
import com.zzu.service.CategoryService;
import com.zzu.service.ProductService;
import com.zzu.utils.ResultVOUtil;
import com.zzu.viewObject.ProductInfoVO;
import com.zzu.viewObject.ProductVO;
import com.zzu.viewObject.ResultVO;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
	
@Autowired
//查询商品信息时用到
ProductService productService;

@Autowired
//查询类目信息时用到
CategoryService categoryService;

@GetMapping("/list")
public ResultVO list(){
	//1.查询所有 上架的商品。
	List<ProductInfo> productInfoList = productService.findUpAll();
	//2.查询类目 获取上面list中商品的所有类目放到一个列表中，然后通过这个列表找到所有类目信息
	//这是java8中新语法我也没怎么看懂
	List<Integer> categoryTypeList=productInfoList.stream()
			.map(e -> e.getProdcutType()).collect(Collectors.toList());
	
	//根据类目信息列表查询类目列表
	List<ProductCategory> productCategoryList=categoryService.findCategoryTypeIn(categoryTypeList);
	//3.数据拼装
	//productVO 也是一个list
	List<ProductVO> productVOList =new ArrayList<>();
	for(ProductCategory productCategory:productCategoryList){
		ProductVO productVO=new ProductVO();
		productVO.setCategoryName(productCategory.getCategoryName());
		productVO.setCategoryType(productCategory.getCategoryType());
		
		//定义一个productInfoVO的列表用于容纳productInfo
		List<ProductInfoVO> productInfoVOList=new ArrayList<>();
		for(ProductInfo productInfo:productInfoList){
			if(productInfo.getProdcutType().equals(productCategory.getCategoryType())){
				ProductInfoVO productInfoVO=new ProductInfoVO();
				//spring库提供的这个方法可以自动product Info到productInfoVO中去
				BeanUtils.copyProperties(productInfo, productInfoVO);
				//添加到inforvolist中去
				productInfoVOList.add(productInfoVO);
			}
			
		}
		//经过循环后把这个类目下的所有商品信息VO都加进去
		productVO.setProductInfoVOList(productInfoVOList);
		//然后把这个商品vo给加到列表中去
		productVOList.add(productVO);
	}
	
	
	/*这样写比较麻烦
	 * ResultVO resultVO=new ResultVO<>();
	resultVO.setCode(0);
	resultVO.setMsg("无错误");
	resultVO.setDate(productVOList);
	return resultVO;*/
	return ResultVOUtil.success(productVOList);
}

}
