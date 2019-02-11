package com.common.filter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;

import com.alibaba.druid.support.http.WebStatFilter;

/**
 * druid过滤器
 * 
 * @author zjk
 * 2017年12月5日 下午6:05:55
 */
@WebFilter(filterName="druidStatFilter",urlPatterns="/*",initParams={
		@WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")//忽略资源  
	})
public class DruidStatFilter extends WebStatFilter {

}
