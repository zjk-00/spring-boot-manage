package com.common.util;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {
	
	private static final Log log = LogFactory.getLog(HttpClientUtil.class);
	
	private static PoolingHttpClientConnectionManager cm = null;
	static {
		// 针对使用SSL的https连接
		LayeredConnectionSocketFactory sslsf = null;
		try {
			sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
		} catch (NoSuchAlgorithmException e) {
			log.error("创建SSL连接失败");
		}
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	                .register("https", sslsf)
	                .register("http", new PlainConnectionSocketFactory())
	                .build();
		cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		cm.setMaxTotal(1000); // 设置整个连接池最大连接数
		//设置过小无法支持大并发，路由是对maxTotal的细分,如果只有一个路由，就让他等于最大值
		cm.setDefaultMaxPerRoute(500);
	}

	// 获取httpclient
	public static CloseableHttpClient getHttpClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler(0,false)).setConnectionManager(cm).build();
		return httpClient;
	}
	
	/**
	 * <p>Title:发送post请求</p>
	 * @param uri
	 * @param params
	 * @author LGG
	 */
	public static String post(String url, Map<String, String> params) {
		String responseStr = "";
		// 获取httpclient实例
		CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();
		CloseableHttpResponse httpResponse = null;
		HttpEntity entity = null;
		try {
			//创建post方式请求对象
			HttpPost httpPost = new HttpPost(url);
			//设置请求和传输超时时间以及连接不够用的时候等待超时时间
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(60000)
					.setConnectTimeout(60000)
					.setConnectionRequestTimeout(60000)
					.build();  
			httpPost.setConfig(requestConfig);
			//装填参数
			List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>(); 
			if(params!=null){  
	            for (Entry<String, String> entry : params.entrySet()) {  
	                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
	            }  
	        }  
			 //设置参数到请求对象中  
	        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
	        log.info("发送http-post请求:请求地址："+ url +"===请求参数："+nvps.toString());
	        //设置header信息  
	        //指定报文头【Content-type】、【User-Agent】  
	        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded"); //application/json
	        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	        //执行请求操作，并拿到结果（同步阻塞） 
	        httpResponse = httpClient.execute(httpPost);
	        //获取结果实体  
	        entity = httpResponse.getEntity(); 
	        if (entity != null) {  
	            //按指定编码转换结果实体为String类型  
	        	responseStr = EntityUtils.toString(entity, "utf-8");  
	        }  
		} catch(IOException e){
			log.error("httpclient-post请求失败", e);
			return null;
		}finally {
			if (entity != null) {
				try {
					EntityUtils.consume(entity);
					httpResponse.close();
				} catch (IOException e) {
					log.error("关闭response失败", e);
					
				}
			}
		}
		return responseStr;	
	}
}
