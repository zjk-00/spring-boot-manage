package com.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * springboot过滤器2
 * 
 * @author zjk
 * 2017年12月5日 下午6:07:02
 */
@Component
@Order(12)
@WebFilter(urlPatterns = "/demo2/*",filterName = "filterDemo2")
public class FilterDemo2 implements Filter{
	
	private final static Logger logger = LoggerFactory.getLogger(FilterDemo2.class);
	
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
    	 HttpServletRequest request = (HttpServletRequest) srequest;
         System.out.println("this is MyFilter,url :"+request.getRequestURI());
         logger.info("============== " + request.getRequestURI() + " ==============");
         filterChain.doFilter(srequest, sresponse);
    }

    @Override
    public void destroy() {

    }
}