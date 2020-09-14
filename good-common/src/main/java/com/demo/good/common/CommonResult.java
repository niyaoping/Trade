package com.demo.good.common;

import lombok.Data;

/**
 * 通用返回对象
Create  By  Yaoping.NI
 */
@Data
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    public CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    public CommonResult(IBaseInfo<Integer> ibase, T data) {
        this.code = ibase.getCode();
        this.message = ibase.getMsg();
        this.data = data;
    }
    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(ErrorEnum.SUCCESS, data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> CommonResult<T> success(T data, String message) {
        return new CommonResult<T>(ErrorEnum.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回结果
     * @param错误码
     */
    public static <T> CommonResult<T> failed() {
        return new CommonResult<T>(ErrorEnum.FAILED, null);
    }

    /**
     * 失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ErrorEnum.FAILED.getCode(), message, null);
    }

   

    /**
     * 参数验证失败返回结果
     */
    public static <T> CommonResult<T> validateFailed() {
        return failed();
    }

    /**
     * 参数验证失败返回结果
     * @param message 提示信息
     */
    public static <T> CommonResult<T> validateFailed(String message) {
        return new CommonResult<T>(ErrorEnum.VALIDATE_FAILED.getCode(), message, null);
    }

    /**
     * 未登录返回结果
     */
    public static <T> CommonResult<T> unauthorized(T data) {
        return new CommonResult<T>(ErrorEnum.UNAUTHORIZED.getCode(), ErrorEnum.UNAUTHORIZED.getMsg(), data);
    }

    /**
     * 未授权返回结果
     */
    public static <T> CommonResult<T> forbidden(T data) {
        return new CommonResult<T>(ErrorEnum.FORBIDDEN.getCode(), ErrorEnum.FORBIDDEN.getMsg(), data);
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
