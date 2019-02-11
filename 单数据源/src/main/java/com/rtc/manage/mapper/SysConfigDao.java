package com.rtc.manage.mapper;

import org.apache.ibatis.annotations.Param;

import com.rtc.manage.entity.SysConfigEntity;

public interface SysConfigDao extends BaseDao<SysConfigEntity> {
	
	/**
	 * 根据key，查询value
	 */
	String queryByKey(String paramKey);
	
	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);
	
}
