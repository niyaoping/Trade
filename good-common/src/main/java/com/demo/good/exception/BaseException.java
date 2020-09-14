package com.demo.good.exception;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
/**
 * 抽象异常
 * Create by  yaoping.ni
 */

public abstract class BaseException extends RuntimeException  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1973158310522728153L;

	@Getter
	@Setter
	private Integer code;

	@Getter 
	@Setter
	private String msg;
	public BaseException(String msg) {
	        super(msg);
	}
	public BaseException() {
	
		
	}
}
