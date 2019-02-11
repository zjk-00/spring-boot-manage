package com.rtc.manage.mapper;

import java.util.Map;

import com.rtc.manage.entity.ScheduleJobEntity;

/**
 * 定时任务
 * 
 * @author zjk
 * 2018年10月25日 上午10:22:41
 */
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
