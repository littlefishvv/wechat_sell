package com.zzu.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.zzu.dataobject.ProductCategory;
import com.zzu.form.CategoryForm;
import com.zzu.service.CategoryService;

import exception.SellException;

@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private CategoryService categoryService;
	
	//这里我们不用分页
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }
    //展示  如果有category就是修改，否则说明是新增类目
	@GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        if (categoryId != null) {
            Optional<ProductCategory> productCategory = categoryService.findOne(categoryId);
            map.put("category", productCategory.orElse(null));
        }

        return new ModelAndView("category/index", map);
    }
	@PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        
        try {
            if (form.getCategoryId() != null) {
               Optional<ProductCategory> productCategory = categoryService.findOne(form.getCategoryId());
              
                 BeanUtils.copyProperties(form, productCategory.orElse(null));
                 
                 categoryService.save(productCategory.orElse(null));}
            else{
            	ProductCategory productCategory=new ProductCategory();
            	BeanUtils.copyProperties(form, productCategory);
                categoryService.save(productCategory);
            }
        } catch (SellException e) {
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("common/error", map);
        }

        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("common/success", map);
    }

}
