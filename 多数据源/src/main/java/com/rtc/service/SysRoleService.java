package com.rtc.service;

import java.util.List;
import java.util.Map;

import com.rtc.entity.SysRoleEntity;

/**
 * 角色
 * 
 * @author zjk
 * 2018年3月1日 下午4:40:15
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleEntity role);
	
	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);
}
