package com.zzu.utils;

import com.zzu.enums.CodeEnum;

public class EnumUtil {
    //返回的结构就是枚举
	public static <T extends CodeEnum> T getByCode(Integer code ,Class<T> enumClass){
		//an array containing the values comprising the enum classrepresented by this Class object in the order they'redeclared,
		//or null if this Class object does notrepresent an enum type
		for(T each:enumClass.getEnumConstants()){
			if(code.equals(each.getCode()))
				return each;
		}
		    
		
		return null;
		
	}
}
