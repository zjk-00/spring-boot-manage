package com.common.quartz;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.common.util.DateUtils;

/** 
* 类说明: 定时器测试bean
* 
* @author zjk 
* 2018年6月29日 下午5:12:05 
*/
@Component("testTask")
public class TestTask {
	
	public void test01(String param){
		System.out.println(DateUtils.format(new Date()) + ":测试方法test01执行--" + param);
	}
	
	/*public void test1(){
		System.out.println(DateUtils.format(new Date()) + ":测试方法test1执行");
	}
	
	public void test2(String param){
		System.out.println(DateUtils.format(new Date()) + ":测试方法test2执行--" + param);
	}*/
}
