package com.rtc.manage.mapper;

import java.util.List;

import com.rtc.manage.entity.SysRoleMenuEntity;

/**
 * 角色与菜单对应关系
 * 
 * @author zjk
 * 2018年3月1日 下午4:35:37
 */
public interface SysRoleMenuDao extends BaseDao<SysRoleMenuEntity> {
	
	/**
	 * 根据角色ID，获取菜单ID列表
	 */
	List<Long> queryMenuIdList(Long roleId);
}
