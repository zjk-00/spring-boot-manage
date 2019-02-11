package com.common.exception;

import com.common.result.ResultInfoInterface;

/** 
* 类说明: ip认证异常类
* 
* @author  zjk
* 2018年2月27日 下午5:55:27 
*/
public class IpAuthException extends RuntimeException{
private static final long serialVersionUID = 1L;
	
	private ResultInfoInterface resultInfo;

	public IpAuthException(ResultInfoInterface resultInfo) {
		super();
		this.resultInfo = resultInfo;
	}

	public ResultInfoInterface getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfoInterface resultInfo) {
		this.resultInfo = resultInfo;
	}
}
