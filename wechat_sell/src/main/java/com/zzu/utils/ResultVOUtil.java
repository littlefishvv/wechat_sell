package com.zzu.utils;

import com.zzu.viewObject.ResultVO;

public class ResultVOUtil {

	public static ResultVO success(Object object){
		ResultVO resultVO=new ResultVO();
		//object实际上是一个productVOlist
		resultVO.setDate(object);
		resultVO.setCode(0);
		resultVO.setMsg("成功");
		return resultVO;
	}
	public static ResultVO success(){
		return success(null);
	}
	public static ResultVO error(Integer code,String msg){
		ResultVO resultVO=new ResultVO<>();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}
}
