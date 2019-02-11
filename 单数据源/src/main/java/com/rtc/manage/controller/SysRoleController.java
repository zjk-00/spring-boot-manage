package com.rtc.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.result.Result;
import com.common.util.PageUtils;
import com.rtc.manage.entity.SysRoleEntity;
import com.rtc.manage.service.SysRoleMenuService;
import com.rtc.manage.service.SysRoleService;

/**
 * 角色管理
 * 
 * @author zjk
 * 2018年3月2日 下午3:48:13
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public Result list(String roleName, Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("roleName", roleName);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<SysRoleEntity> list = sysRoleService.queryList(map);
		int total = sysRoleService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(list, total, limit, page);
		
		return Result.ok().put("page", pageUtil);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public Result select(){
		//查询列表数据
		List<SysRoleEntity> list = sysRoleService.queryList(new HashMap<String, Object>());
		
		return Result.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public Result info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.queryObject(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return Result.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public Result save(@RequestBody SysRoleEntity role){
		if(StringUtils.isBlank(role.getRoleName())){
			return Result.error("角色名称不能为空");
		}
		
		sysRoleService.save(role);
		
		return Result.ok();
	}
	
	/**
	 * 修改角色
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public Result update(@RequestBody SysRoleEntity role){
		if(StringUtils.isBlank(role.getRoleName())){
			return Result.error("角色名称不能为空");
		}
		
		sysRoleService.update(role);
		
		return Result.ok();
	}
	
	/**
	 * 删除角色
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public Result delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return Result.ok();
	}
}
