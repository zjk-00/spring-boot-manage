package com.common.config;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/** 
* 类说明: 把kaptcha加入到bean中，可以在controller中注入其对象
* 
* @author  zjk
* 2018年3月1日 下午3:29:41 
*/
@Component
public class KaptchaConfig {
	
	@Bean  
    public DefaultKaptcha getDefaultKaptcha(){
        com.google.code.kaptcha.impl.DefaultKaptcha defaultKaptcha = new com.google.code.kaptcha.impl.DefaultKaptcha();  
        Properties properties = new Properties();  
        properties.setProperty("kaptcha.border", "yes");  
        properties.setProperty("kaptcha.border.color", "105,179,90");  
        properties.setProperty("kaptcha.textproducer.font.color", "blue");  
        properties.setProperty("kaptcha.image.width", "140");  
        properties.setProperty("kaptcha.image.height", "60");  
        properties.setProperty("kaptcha.textproducer.font.size", "50");  
        properties.setProperty("kaptcha.session.key", "code");  
        properties.setProperty("kaptcha.textproducer.char.length", "4");  
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");  
        Config config = new Config(properties);  
        defaultKaptcha.setConfig(config);  
          
        return defaultKaptcha;  
    } 
}
