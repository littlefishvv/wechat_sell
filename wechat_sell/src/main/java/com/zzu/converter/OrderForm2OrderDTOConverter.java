package com.zzu.converter;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzu.dataobject.OrderDetail;
import com.zzu.dto.OrderDTO;
import com.zzu.enums.ResultEnum;
import com.zzu.form.OrderForm;

import exception.SellException;

public class OrderForm2OrderDTOConverter {
public static OrderDTO convert(OrderForm orderForm){
	OrderDTO orderDTO = new OrderDTO();
    orderDTO.setBuyerName(orderForm.getName());
    orderDTO.setBuyerPhone(orderForm.getPhone());
    orderDTO.setBuyerAddress(orderForm.getAddress());
    orderDTO.setBuyerOpenid("oTgZpwbIFdr-HQ9fYd2P-xtm6jUM");

    //订单详情
    //这是一个插件，把json转成list
    Gson gson = new Gson();
    List<OrderDetail> orderDetailList = new ArrayList<>();
    try {
    	//fromJson(String, Class<T>)
    	//这个没怎么看懂大概就是把string类型的json转换成typetoken中的类型
        orderDetailList = gson.fromJson(orderForm.getItems() ,
                new TypeToken<List<OrderDetail>>(){}.getType());
    }catch (Exception e) {
       // log.error("【对象装换】错误，string={}" , orderForm.getItems());
        throw new SellException(ResultEnum.PARAM_ERROR);
    }

    orderDTO.setOrderDetailList(orderDetailList);

    return orderDTO;
}
}
