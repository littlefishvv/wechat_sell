package com.zzu.utils;

import com.zzu.viewObject.ResultVO;

public class ResultVOUtil {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ResultVO success(Object object){
		ResultVO resultVO=new ResultVO();
		//object实际上是一个productVOlist
		resultVO.setData(object);
		resultVO.setCode(0);
		resultVO.setMsg("成功");
		return resultVO;
	}
	@SuppressWarnings("rawtypes")
	public static ResultVO success(){
		return success(null);
	}
	@SuppressWarnings("rawtypes")
	public static ResultVO error(Integer code,String msg){
		ResultVO resultVO=new ResultVO<>();
		resultVO.setCode(code);
		resultVO.setMsg(msg);
		return resultVO;
	}
}
