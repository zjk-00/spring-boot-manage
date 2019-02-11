package com.rtc.manage.mapper;

import java.util.List;

import com.rtc.manage.entity.SysMenuEntity;

/**
 * 菜单管理
 * 
 * @author zjk
 * 2018年3月1日 下午4:34:44
 */
public interface SysMenuDao extends BaseDao<SysMenuEntity> {
	
	/**
	 * 根据父菜单，查询子菜单
	 * @param parentId 父菜单ID
	 */
	List<SysMenuEntity> queryListParentId(Long parentId);
	
	/**
	 * 获取不包含按钮的菜单列表
	 */
	List<SysMenuEntity> queryNotButtonList();
}
