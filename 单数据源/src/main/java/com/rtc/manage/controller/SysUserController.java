package com.rtc.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.common.result.Result;
import com.common.util.PageUtils;
import com.common.util.ShiroUtils;
import com.rtc.manage.entity.SysUserEntity;
import com.rtc.manage.service.SysUserRoleService;
import com.rtc.manage.service.SysUserService;

/**
 * 系统用户
 * 
 * @author zjk
 * 2018年3月2日 下午3:48:54
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public Result list(String username, Integer page, Integer limit){
		Map<String, Object> map = new HashMap<>();
		map.put("username", username);
		map.put("offset", (page - 1) * limit);
		map.put("limit", limit);
		
		//查询列表数据
		List<SysUserEntity> userList = sysUserService.queryList(map);
		int total = sysUserService.queryTotal(map);
		
		PageUtils pageUtil = new PageUtils(userList, total, limit, page);
		
		return Result.ok().put("page", pageUtil);
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public Result info(){
		SysUserEntity user = getUser();
		return Result.ok().put("user", user);
	}
	
	/**
	 * 修改登录用户密码
	 */
	@RequestMapping("/password")
	public Result password(String userName,String password, String newPassword){
		if(StringUtils.isBlank(newPassword)){
			return Result.error("新密码不为能空");
		}
		
		//sha256加密
		password = new Sha256Hash(password).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword).toHex();
				
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword,userName);
		if(count == 0){
			return Result.error("原密码不正确");
		}
		
		//退出
		ShiroUtils.logout();
		
		return Result.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public Result info(@PathVariable("userId") Long userId){
		SysUserEntity user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return Result.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public Result save(@RequestBody SysUserEntity user){
		if(StringUtils.isBlank(user.getUsername())){
			return Result.error("用户名不能为空");
		}
		if(StringUtils.isBlank(user.getPassword())){
			return Result.error("密码不能为空");
		}
		
		sysUserService.save(user);
		
		return Result.ok();
	}
	
	/**
	 * 修改用户
	 */
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public Result update(@RequestBody SysUserEntity user){
		if(StringUtils.isBlank(user.getUsername())){
			return Result.error("用户名不能为空");
		}
		
		sysUserService.update(user);
		
		return Result.ok();
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public Result delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return Result.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return Result.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return Result.ok();
	}
}
