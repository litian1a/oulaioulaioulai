package com.sleep.kaola.http.model;

/**
 * 公共数据格式
 * @author Mr.W
 *
 * @param <T>
 */
public class Result<T> {
	public int code;
	public String msg;
	public T data;

}
