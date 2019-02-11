package com.rtc.mapper.master;

import java.util.Map;

import com.rtc.entity.ScheduleJobEntity;

/**
 * 定时任务
 * 
 * @author zjk
 * 2018年6月28日 下午2:02:27
 */
public interface ScheduleJobDao extends BaseDao<ScheduleJobEntity> {
	
	/**
	 * 批量更新状态
	 */
	int updateBatch(Map<String, Object> map);
}
