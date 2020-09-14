package com.demo.good.exception;



import com.demo.good.common.IBaseInfo;

/**
 * 接口统一异常
 * Create by yaoping.ni
 */
public class APIException extends BaseException {

	
	public APIException() {
		super();
	}


	public APIException(Integer code, String msg) {
		super(msg);
		super.setCode(code);
		super.setMsg(msg);	
		
	}
	


        public APIException(IBaseInfo baseEnum){
		super.setCode(Integer.valueOf(baseEnum.getCode().toString()));
		super.setMsg(baseEnum.getMsg());	
	}

	
}
