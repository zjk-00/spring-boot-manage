package com.common.shiro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

import com.common.util.RedisUtil;
import com.rtc.manage.entity.SysMenuEntity;
import com.rtc.manage.entity.SysUserEntity;
import com.rtc.manage.service.SysMenuService;
import com.rtc.manage.service.SysUserService;

/**
 * 认证
 * 
 * @author zjk
 * 2018年3月1日 下午5:02:47
 */
public class UserRealm extends AuthorizingRealm {
    @Resource(name="sysUserService")
    private SysUserService sysUserService;
    @Resource(name="sysMenuService")
    private SysMenuService sysMenuService;
    @Resource
	protected RedisUtil redisUtil;
    /**
     * 授权(验证权限时调用)
     */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysUserEntity user = (SysUserEntity)principals.getPrimaryPrincipal();
		Long userId = user.getUserId();
		
		List<String> permsList = null;
		
		//系统管理员，拥有最高权限
		if(userId == 1){
			List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<String, Object>());
			permsList = new ArrayList<>(menuList.size());
			for(SysMenuEntity menu : menuList){
				permsList.add(menu.getPerms());
			}
		}else{
			permsList = sysUserService.queryAllPerms(userId);
		}

		//用户权限列表
		Set<String> permsSet = new HashSet<String>();
		for(String perms : permsList){
			if(StringUtils.isEmpty(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录时调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        
        SysUserEntity user = sysUserService.queryByUserName(username);
        
        //通过redis查询用户信息
        /*Object passwordAndStatu = redisUtil.hget(RedisConstant.USER,username); //根据用户名获取
        if(passwordAndStatu != null){
            String[] users=passwordAndStatu.toString().split("\\|");
            user.setPassword(users[0]);
            user.setStatus(Integer.parseInt(users[1]));
            user.setUserId(Long.parseLong(users[2]));
        }else{
            user = sysUserService.queryByUserName(username);
            if(user != null)
            	redisUtil.hset(RedisConstant.USER, username, user.getPassword()+"|"+user.getStatus()+"|"+user.getUserId());
        }*/
        //账号不存在
        if(user == null) {
            throw new UnknownAccountException("账号不存在");
        }
        
        //密码错误
        if(!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
        
        //账号锁定
        if(user.getStatus() == 0){
        	throw new LockedAccountException("账号已被锁定,请联系管理员");
        }
        
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
	}

}
