package com.ll.platform.robertService.common;

/**
 * @Title Result.java
 * @Description 统一响应结果封装
 * @author Robert
 * @date 2019年11月26日
 */
public class Result<T> {
	private int code;
    private String message;
    private T data;

    public Result() {
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     */
    public static <T> Result<T> success() {
    	return new Result<>(ResultCodeEnum.SUCCESS.code(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.code(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回结果
     *
     * @param data 获取的数据
     * @param  message 提示信息
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(ResultCodeEnum.SUCCESS.code(), message, data);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     */
    public static <T> Result<T> failure(ResultCodeEnum errorCode) {
        return new Result<>(errorCode.code(), errorCode.getMessage(), null);
    }

    /**
     * 失败返回结果
     * @param errorCode 错误码
     * @param message 提示信息
     */
    public static <T> Result<T> failure(ResultCodeEnum errorCode, String message) {
        return new Result<>(errorCode.code(), message, null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
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
