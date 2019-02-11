package com.common.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.common.shiro.UserRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/** 
* 类说明: shiro配置类
* 
* @author  zjk
* 2018年3月1日 下午4:18:20 
*/
@Configuration
public class ShiroConfiguration {
	
	@Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
	
	/**
     * LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，
     * 负责org.apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。
     * 主要是AuthorizingRealm类的子类，以及EhCacheManager类。
     */
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    /**
     * HashedCredentialsMatcher，这个类是为了对密码进行编码的，
     * 防止密码在数据库里明码保存，当然在登陆认证的时候，
     * 这个类也负责对form里输入的密码进行编码。
     */
    @Bean(name = "hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");
        credentialsMatcher.setHashIterations(2);
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }
    
    /**UserRealm，这是个自定义的认证类，继承自AuthorizingRealm，
     * 负责用户的认证和权限的处理，可以参考JdbcRealm的实现。
     */
    @Bean(name = "userRealm")
    @DependsOn("lifecycleBeanPostProcessor")
    public UserRealm userRealm() {
    	UserRealm realm = new UserRealm();
        //realm.setCredentialsMatcher(hashedCredentialsMatcher());
        return realm;
    }
    
	  /**
	  * EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，
	  * 然后每次用户请求时，放入用户的session中，如果不设置这个bean，每个请求都会查询一次数据库。
	  */
	 /*@Bean(name = "ehCacheManager")
	 @DependsOn("lifecycleBeanPostProcessor")
	 public EhCacheManager ehCacheManager() {
	     return new EhCacheManager();
	 }*/
    
    /**
     * SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm());
//      securityManager.setCacheManager(ehCacheManager());
        return securityManager;
    }
    
    /**
     * ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。
     * 它主要保持了三项数据，securityManager，filters，filterChainDefinitionManager。
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //Shiro的核心安全接口,这个属性是必须的
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.html"页面
        shiroFilterFactoryBean.setLoginUrl("/login.html");
        //用户访问未对其授权的资源时,所显示的连接
        shiroFilterFactoryBean.setUnauthorizedUrl("/");

        Map<String, String> filterChainDefinitionManager = new LinkedHashMap<String, String>();
        filterChainDefinitionManager.put("/dj/**", "anon");
        filterChainDefinitionManager.put("/statics/**", "anon");
        filterChainDefinitionManager.put("/interface/**", "anon");
        filterChainDefinitionManager.put("/login.html", "anon");
        filterChainDefinitionManager.put("/sys/login", "anon");
        filterChainDefinitionManager.put("/captcha.jpg", "anon");
        filterChainDefinitionManager.put("/**", "authc");//用户为ROLE_USER 角色可以访问。由用户角色控制用户行为。
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionManager);

        return shiroFilterFactoryBean;
    }

    /**
     * DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
     */
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    /**
     * AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
     * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor aASA = new AuthorizationAttributeSourceAdvisor();
        aASA.setSecurityManager(securityManager());
        return aASA;
    }
    
}
