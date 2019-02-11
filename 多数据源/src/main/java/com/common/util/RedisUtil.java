package com.common.util;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * redis操作工具类
 * 
 * @author zjk
 * 2017年12月5日 下午6:01:47
 */
@Component
public class RedisUtil {

	@Resource
	private StringRedisTemplate stringRedisTemplate;
	@Resource
	private RedisTemplate<Object, Object> redisTemplate;
	@Resource(name = "stringRedisTemplate")
	private ValueOperations<String, String> valOpsStr;
	@Resource(name = "redisTemplate")
	private ValueOperations<Object, Object> valOpsObj;

	/**
	 * 根据指定key获取string
	 * 
	 * @param key
	 * @return
	 */
	public String getStr(String key) {
		return valOpsStr.get(key);
	}

	/**
	 * 设置str缓存
	 * 
	 * @param key
	 * @param value
	 */
	public void setStr(String key, String value) {
		valOpsStr.set(key, value);
	}
	
	/**
     * 设置 key 的值为 value
     * 其它规则与 set(K key, V value)一样
     * @param key 不能为空
     * @param value 设置的值
     * @param timeout 设置过期的时间
     * @param unit 时间单位。不能为空
     */
	public void setStr(String key, String value, long timeout) {
		valOpsStr.set(key, value, timeout, TimeUnit.SECONDS);
	}
	
	/** 
     * 指定缓存失效时间 
     * @param key 键 
     * @param time 时间(秒) 
     * @return 
     */  
    public boolean expire(String key,long time){  
        try {  
            if(time>0){  
                redisTemplate.expire(key, time, TimeUnit.SECONDS);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
	
    /** 
     * HashGet 
     * @param key 键 不能为null 
     * @param item 项 不能为null 
     * @return 值 
     */  
    public Object hget(String key,String item){  
        return redisTemplate.opsForHash().get(key, item);  
    }  
      
    /** 
     * 获取hashKey对应的所有键值 
     * @param key 键 
     * @return 对应的多个键值 
     */  
    public Map<Object,Object> hmget(String key){  
        return redisTemplate.opsForHash().entries(key);  
    }
    
	/** 
     * HashSet 
     * @param key 键 
     * @param map 对应多个键值 
     * @return true 成功 false 失败 
     */  
    public boolean hmset(String key, Map<String,Object> map){    
        try {  
            redisTemplate.opsForHash().putAll(key, map);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
    
    /** 
     * 向一张hash表中放入数据,如果不存在将创建 
     * @param key 键 
     * @param item 项 
     * @param value 值 
     * @return true 成功 false失败 
     */  
    public boolean hset(String key,String item,Object value) {  
         try {  
            redisTemplate.opsForHash().put(key, item, value);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
	
    /** 
     * 向一张hash表中放入数据,如果不存在将创建 
     * @param key 键 
     * @param item 项 
     * @param value 值 
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间 
     * @return true 成功 false失败 
     */  
    public boolean hset(String key,String item,Object value,long time) {  
         try {  
            redisTemplate.opsForHash().put(key, item, value);  
            if(time>0){  
                expire(key, time);  
            }  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }  
    }  
      
    /** 
     * 删除hash表中的值 
     * @param key 键 不能为null 
     * @param item 项 可以使多个 不能为null 
     */  
    public void hdel(String key, Object... item){    
        redisTemplate.opsForHash().delete(key,item);  
    }   
      
    /** 
     * 判断hash表中是否有该项的值 
     * @param key 键 不能为null 
     * @param item 项 不能为null 
     * @return true 存在 false不存在 
     */  
    public boolean hHasKey(String key, String item){  
        return redisTemplate.opsForHash().hasKey(key, item);  
    }   
    
	/**
	 * 删除指定的key（str）
	 * 
	 * @param key
	 */
	public void delStr(String key) {
		stringRedisTemplate.delete(key);
	}

	/**
	 * 根据指定的o获取object
	 * 
	 * @param o
	 * @return 
	 * @return
	 */
	public <T> Object getObj(T o) {
		return valOpsObj.get(o);
	}

	/**
	 * 设置obj缓存
	 * 
	 * @param o1
	 * @param o2
	 */
	public void setObj(Object o1, Object o2) {
		valOpsObj.set(o1, o2);
	}
	
	/**
	 * 删除Obj缓存
	 * @param o
	 */
	public void delObj(Object o) {
		redisTemplate.delete(o);
	}
	
	/**
	 * 判断key是否存在
	 * @return
	 */
	public boolean hasKey(Object o) { 
		return redisTemplate.hasKey(o);
	}
}
