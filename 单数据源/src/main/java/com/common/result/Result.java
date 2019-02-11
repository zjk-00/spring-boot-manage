package com.common.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

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
	
	public static Result error(String code, String msg) {
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
	
	public static Result parse(String jsonStr){
		if(StringUtils.isBlank(jsonStr)){
			return Result.error();
		}
		JSONObject jso=JSON.parseObject(jsonStr);
		if(!jso.isEmpty()){
			Map<String, Object> map = new HashMap<>();
			map.put("code", jso.get("code") + "");
			map.put("msg", jso.get("msg") + "");
			map.put("data", jso.getJSONObject("data"));
			return Result.ok(map);
		}
		return Result.error();
	}
	
}
