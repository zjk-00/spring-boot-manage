package com.rtc.service;

import java.util.List;

/**
 * 角色与菜单对应关系
 * 
 * @author zjk
 * 2018年3月1日 下午4:39:59
 */
public interface SysRoleMenuService {
	
	void saveOrUpdate(Long roleId, List<Long> menuIdList);
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
	
}
