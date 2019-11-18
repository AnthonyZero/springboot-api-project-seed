package com.anthonyzero.seed.common.core;


import org.apache.http.HttpStatus;
import com.alibaba.fastjson.JSON;

/**
 * 统一API响应结果封装
 */
public class Result<T>{

	private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
	private int code;
	private String message;
	private T data;

	public int getCode() {
		return code;
	}

	public Result<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMessage() {
		return message;
	}

	public Result<T> setMessage(String message) {
		this.message = message;
		return this;
	}

	public T getData() {
		return data;
	}

	public Result<T> setData(T data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public static <T extends Object> Result<T> success() {
		return new Result<T>().setCode(HttpStatus.SC_OK).setMessage(DEFAULT_SUCCESS_MESSAGE);
	}
	
	public static <T extends Object> Result<T> success(String message) {
		return new Result<T>().setCode(HttpStatus.SC_OK).setMessage(message);
	}

	public static <T extends Object> Result<T> success(T data) {
		return new Result<T>().setCode(HttpStatus.SC_OK).setMessage(DEFAULT_SUCCESS_MESSAGE).setData(data);
	}

	public static <T extends Object> Result<T> error(String message) {
		return new Result<T>().setCode(HttpStatus.SC_BAD_REQUEST).setMessage(message);
	}

	public static <T extends Object> Result<T> error(int code, String message) {
		return new Result<T>().setCode(code).setMessage(message);
	}

	public static <T extends Object> Result<T> error() {
		return new Result<T>().setCode(HttpStatus.SC_INTERNAL_SERVER_ERROR).setMessage("未知异常，请联系管理员");
	}
}
