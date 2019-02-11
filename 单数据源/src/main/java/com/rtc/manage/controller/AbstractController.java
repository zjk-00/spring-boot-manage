package com.rtc.manage.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.exception.RRException;
import com.common.util.ShiroUtils;
import com.rtc.manage.entity.SysUserEntity;

/**
 * Controller公共组件
 * 
 * @author zjk
 * 2018年3月2日 下午3:44:32
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return ShiroUtils.getUserEntity();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
	
	/**
	 * 参数非空验证
	 * 
	 * 2018年10月19日 上午9:42:08
	 * @return
	 */
	protected void checkNotNull(Object param, String msg){
		if(param == null || StringUtils.isBlank(param.toString())){
			throw new RRException(msg);
		}
	}
}
