package com.common.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.common.result.Result;
import com.common.result.ResultInfoInterface;

/** 
* 类说明: 统一错误异常处理类
* 
* @author  zjk
* 2017年12月26日 下午7:20:12 
*/
@RestControllerAdvice
public class GlobalErrorInfoHandler {
	
	/**
	 * 处理Exception异常
	 * 
	 * 2017年12月26日 下午7:58:26
	 * @param request
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	public Result defaultErrorHandler(HttpServletRequest request, Exception exception){
		return Result.error();
	}
	
	/**
	 * 处理IpAuthException异常
	 * 
	 * 2018年2月27日 下午5:57:38
	 * @return
	 */
	@ExceptionHandler(value = IpAuthException.class)
	public Result ipAuthError(HttpServletRequest request, IpAuthException exception){
		ResultInfoInterface errorInfo = exception.getResultInfo();
		return Result.error(errorInfo);
	}
	
	/**
	 * 处理GlobalErrorInfoException异常
	 * 
	 * 2017年12月26日 下午7:40:43
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = GlobalErrorInfoException.class)
	public Result jsonErrorHandler(HttpServletRequest request, GlobalErrorInfoException exception){
		ResultInfoInterface errorInfo = exception.getResultInfo();
		return Result.error(errorInfo);
	}
	
	/**
	 * 处理RRException异常
	 * 
	 * 2018年2月27日 下午5:57:38
	 * @return
	 */
	@ExceptionHandler(value = RRException.class)
	public Result RRError(HttpServletRequest request, RRException exception){
		return Result.error(String.valueOf(exception.getCode()),exception.getMsg());
	}
	
}
