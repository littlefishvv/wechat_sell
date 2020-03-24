package com.zzu.utils;

import java.util.Random;

public class KeyUtil {
	//生成唯一的主键，格式当前毫秒数加随机数
	//synchronized多线程关键字
    public static synchronized String genUniqueKey(){
		Random random=new Random();
    	Integer a=random.nextInt(900000)+100000;
    	return System.currentTimeMillis()+String.valueOf(a);
    }
}
