package com.rtc.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统页面视图
 * 
 * @author zjk
 * 2018年3月2日 下午3:47:42
 */
@Controller
public class SysPageController {
	
	@RequestMapping("sys/{url}.html")
	public String page(@PathVariable("url") String url){
		return "sys/" + url;
	}
	
	@RequestMapping("index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("sys/main")
	public String main(){
		return "sys/main";
	}
	
	@RequestMapping("sys/header")
	public String header(){
		return "sys/header";
	}
	
}
