package com.common.result;
/** 
* 类说明: 应用系统级别状态码
* 
* @author  zjk
* 2017年12月26日 下午5:28:54 
*/
public enum GlobalResultInfoEnum implements ResultInfoInterface{

	SUCCESS("200", "success"),
	SHORT_URL_ERROR("500","生成短链接失败！"),
	IP_AUTH_ERROR("500","IP认证未通过！"),
    NOT_FOUND("-1", "service not found");
	
	
	private String code;

	private String message;
	
	private GlobalResultInfoEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
