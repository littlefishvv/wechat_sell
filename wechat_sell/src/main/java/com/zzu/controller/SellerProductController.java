package com.zzu.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzu.dataobject.ProductCategory;
import com.zzu.dataobject.ProductInfo;
import com.zzu.dto.OrderDTO;
import com.zzu.form.ProductForm;
import com.zzu.service.CategoryService;
import com.zzu.service.OrderService;
import com.zzu.service.ProductService;
import com.zzu.utils.KeyUtil;

import exception.SellException;
import freemarker.template.utility.StringUtil;

@Controller
@RequestMapping("/seller/product")
public class SellerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
	@GetMapping("/list")
	public ModelAndView list(@RequestParam(value="page",defaultValue="1") Integer page,
                             @RequestParam(value="size",defaultValue="10") Integer size,
                             Map<String,Object> map){
		//我们传入的是从第1页开始，但接口里用的是第0页 ，所有要查的话先减	1				
				PageRequest request=PageRequest.of(page-1, size);
				Page<ProductInfo> productInfoPage=productService.findAll(request);
			    map.put("productInfoPage", productInfoPage);
			    map.put("currentPage", page);
			    map.put("size", size);
				return new ModelAndView("product/list",map);
				
	}
	@GetMapping("/on_sale")
	public ModelAndView onSale(@RequestParam("productId") String productId,
			                   Map<String,Object> map){
		try{
			productService.onSale(productId);
		}catch(SellException e){
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			return new ModelAndView("common/error",map);
		}
		
		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success",map);
		
	}
	@GetMapping("/off_sale")
	public ModelAndView offSale(@RequestParam("productId") String productId,
			                   Map<String,Object> map){
		try{
			productService.offSale(productId);
		}catch(SellException e){
			map.put("msg", e.getMessage());
			map.put("url", "/sell/seller/product/list");
			return new ModelAndView("common/error",map);
		}
		
		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success",map);
		
	}
	@GetMapping("/index")
	//required=false表示这个参数是非必传的  因为这个方法的目的既可以新增商品，也可以修改商品
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                      Map<String, Object> map) {
		//包要引对
        if (!StringUtils.isEmpty(productId)) {
            Optional<ProductInfo> productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo.orElse(null));
        }

        //查询所有的类目 展示给前端
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);

        return new ModelAndView("product/index", map);
    }
	//save方法要传的字段比较多，所以我们就不用上面的方式进行了，我们新建一个对象专门处理表单传过来的字段
	@PostMapping("/save")
	public ModelAndView save(@Valid ProductForm form,
			                 BindingResult bindingResult,
			                 Map<String,Object> map){
		
		if(bindingResult.hasErrors()){
			map.put("msg", bindingResult.getFieldError().getDefaultMessage());
			map.put("url", "sell/seller/product/index");
			return new ModelAndView("common/error",map);
		}
		Optional<ProductInfo> productInfo;
		try{
			if(!StringUtils.isEmpty(form.getProductId())){
			 productInfo=productService.findOne(form.getProductId());
			BeanUtils.copyProperties(form, productInfo.orElse(null));
			productService.save(productInfo.orElse(null));
			}else{
				ProductInfo productI=new ProductInfo();
				form.setProductId(KeyUtil.genUniqueKey());
				BeanUtils.copyProperties(form, productI);
				productService.save(productI);
			}
			
			
		}catch(SellException e ){
			map.put("msg",e.getMessage());
			map.put("url", "sell/seller/product/index");
			return new ModelAndView("common/error",map);
		}
		map.put("url", "/sell/seller/product/list");
		return new ModelAndView("common/success",map);
	}
}
