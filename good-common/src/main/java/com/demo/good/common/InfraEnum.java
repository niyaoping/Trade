package com.demo.good.common;

import lombok.Getter;

/**
 * 基础设施枚举
 * Create  By  Yaoping.NI
 */
@Getter
public enum InfraEnum  implements IBaseInfo<Integer> {

	express(0, "物流公司"),

	transport(1, "运输方式"),;


	/** code */
	private Integer code;

	/** msg */
	private String msg;

	InfraEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
