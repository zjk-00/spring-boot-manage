package com.common.exception;

import com.common.result.ResultInfoInterface;

/** 
* 类说明: 统一错误异常
* 
* @author  zjk
* 2017年12月26日 下午6:09:06 
*/
public class GlobalErrorInfoException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private ResultInfoInterface resultInfo;

	public GlobalErrorInfoException(ResultInfoInterface resultInfo) {
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
