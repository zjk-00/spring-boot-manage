package com.rtc.manage.mapper;

import java.util.List;

import com.rtc.manage.entity.SysUserRoleEntity;

/**
 * 用户与角色对应关系
 * 
 * @author zjk
 * 2018年3月1日 下午4:36:14
 */
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */
	List<Long> queryRoleIdList(Long userId);
}
