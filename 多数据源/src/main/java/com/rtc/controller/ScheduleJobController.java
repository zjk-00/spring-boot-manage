package com.rtc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.exception.RRException;
import com.common.result.Result;
import com.common.util.PageUtils;
import com.rtc.entity.ScheduleJobEntity;
import com.rtc.service.ScheduleJobService;

/**
 * 定时任务
 * 
 * @author zjk
 * 2018年6月28日 下午1:56:14
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
	@Resource
	private ScheduleJobService scheduleJobService;
	
	/**
	 * 定时任务列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	public Result list(String beanName, Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("beanName", beanName);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(map);
		int total = scheduleJobService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(jobList, total, limit, page);
		
		return Result.ok().put("page", pageUtil);
	}
	
	/**
	 * 定时任务信息
	 */
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public Result info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);
		
		return Result.ok().put("schedule", schedule);
	}
	
	/**
	 * 保存定时任务
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public Result save(@RequestBody ScheduleJobEntity scheduleJob){
		//数据校验
		verifyForm(scheduleJob);
		
		scheduleJobService.save(scheduleJob);
		
		return Result.ok();
	}
	
	/**
	 * 修改定时任务
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public Result update(@RequestBody ScheduleJobEntity scheduleJob){
		//数据校验
		verifyForm(scheduleJob);
				
		scheduleJobService.update(scheduleJob);
		
		return Result.ok();
	}
	
	/**
	 * 删除定时任务
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public Result delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		
		return Result.ok();
	}
	
	/**
	 * 立即执行任务
	 */
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public Result run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		
		return Result.ok();
	}
	
	/**
	 * 暂停定时任务
	 */
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public Result pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		
		return Result.ok();
	}
	
	/**
	 * 恢复定时任务
	 */
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public Result resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		
		return Result.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(ScheduleJobEntity scheduleJob){
		if(StringUtils.isBlank(scheduleJob.getBeanName())){
			throw new RRException("bean名称不能为空");
		}
		
		if(StringUtils.isBlank(scheduleJob.getMethodName())){
			throw new RRException("方法名称不能为空");
		}
		
		if(StringUtils.isBlank(scheduleJob.getCronExpression())){
			throw new RRException("cron表达式不能为空");
		}
	}
}
