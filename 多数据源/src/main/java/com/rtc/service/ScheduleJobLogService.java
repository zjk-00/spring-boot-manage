package com.rtc.service;

import java.util.List;
import java.util.Map;

import com.rtc.entity.ScheduleJobLogEntity;

/**
 * 定时任务日志
 * 
 * @author zjk
 * 2018年6月28日 下午1:58:41
 */
public interface ScheduleJobLogService {

	/**
	 * 根据ID，查询定时任务日志
	 */
	ScheduleJobLogEntity queryObject(Long jobId);
	
	/**
	 * 查询定时任务日志列表
	 */
	List<ScheduleJobLogEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存定时任务日志
	 */
	void save(ScheduleJobLogEntity log);
	
}
