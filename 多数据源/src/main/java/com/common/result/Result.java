package com.common.result;

import java.util.HashMap;
import java.util.Map;

/**
 * ajax请求返回数据
 * @author zjk
 */
public class Result extends HashMap<String, Object>{
	
	private static final long serialVersionUID = 1L;

	public Result() {
		put("code", GlobalResultInfoEnum.SUCCESS.getCode());
		put("msg", GlobalResultInfoEnum.SUCCESS.getMessage());
	}
	
	public static Result error() {
		return error("500", "未知异常，请联系管理员");
	}
	
	public static Result error(String msg) {
		return error("500", msg);
	}
	
	public static Result error(ResultInfoInterface errorInfo) {
		return error(errorInfo.getCode(), errorInfo.getMessage());
	}
	
	private static Result error(String code, String msg) {
		Result r = new Result();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}
	
	public static Result ok(String msg) {
		Result r = new Result();
		r.put("msg", msg);
		return r;
	}
	
	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	
	public static Result ok() {
		return new Result();
	}
	
	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
}
