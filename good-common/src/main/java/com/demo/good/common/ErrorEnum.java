package com.demo.good.common;
import lombok.Getter;

/**
 * 错误码枚举
 * Create  By  Yaoping.NI
 */
@Getter
public enum ErrorEnum implements IBaseInfo<Integer> {

	/** SYSTEN_EXCEPTION */
	SYSTEN_EXCEPTION(500, "System Exception"),

	/** success */
	SUCCESS(200, "SUCCESS"),

	/** UNKNOWN_EXCEPTION */
	UNKNOWN_EXCEPTION(100001, "Unknown Exception"),

	/** SERVICE_EXCEPTION */
	SERVICE_EXCEPTION(100002, "Service Exception"),

	/** DB_EXCEPTION */
	DB_EXCEPTION(100003, "DB Exception"),

	/** PARAM_EXCEPTION */
	PARAM_EXCEPTION(100004, "Parameter Exception"),

	SIGNATURE_VERIFICATION_FAIL(100006,"Signature Verification Fail"),
	MISS_SIGNATURE_PARAMETER(100007, "Miss signature parameter"),

	/** ILLEGAL_TOKEN */
	ILLEGAL_TOKEN(100008, "Illegal Token"),
	FAILED(100009, "FAILED"),

	FORBIDDEN(100010, "FORBIDDEN"),
	UNAUTHORIZED(100012, "UNAUTHORIZED"),
	VALIDATE_FAILED(100013, "VALIDATE_FAILED"),
	HTTPERROR(100014, "HTTP ERROR"),
	QUERY_FAILED(100015, "QUERY FAILED"),
	;

	/** code */
	private Integer code;

	/** desc */
	private String msg;

	ErrorEnum(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

}
