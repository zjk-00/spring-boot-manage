package com.rtc.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.result.Result;
import com.common.util.PageUtils;
import com.rtc.manage.entity.ScheduleJobLogEntity;
import com.rtc.manage.service.ScheduleJobLogService;

/**
 * 定时任务日志
 * 
 * @author zjk
 * 2018年10月25日 上午10:18:08
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	/**
	 * 定时任务日志列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public Result list(Integer page, Integer limit, Integer jobId){
		Map<String, Object> map = new HashMap<>();
		map.put("jobId", jobId);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(map);
		int total = scheduleJobLogService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(jobList, total, limit, page);
		
		return Result.ok().put("page", pageUtil);
	}
	
	/**
	 * 定时任务日志信息
	 */
	@RequestMapping("/info/{logId}")
	public Result info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);
		
		return Result.ok().put("log", log);
	}
}
