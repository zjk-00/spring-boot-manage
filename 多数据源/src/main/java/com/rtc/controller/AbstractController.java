package com.rtc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.common.util.ShiroUtils;
import com.rtc.entity.SysUserEntity;

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
}
