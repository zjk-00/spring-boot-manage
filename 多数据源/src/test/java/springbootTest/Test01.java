package springbootTest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.Application;
import com.common.util.HttpClientUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class Test01 {
	
	@Resource
    private StringRedisTemplate stringRedisTemplate;
	@Resource
	private RedisTemplate<String, String> redisTemplate;
    
    @Test
    public void test01() throws Exception{
    	Map<String, String> params = new HashMap<>();
    	params.put("username", "18337100001");
    	params.put("password", "123456");
    	params.put("terminal", "ios");
    	params.put("equipmentId", "123213217");
    	String post = HttpClientUtil.post("http://cs.ruitecloud.com/checkLogin", params);
    	System.out.println(post);
    }
    
    @Test
    public void test02(){
    	Map<String, String> param = new HashMap<>();
    	for (int i = 0; i < 1000; i++) {
			param.clear();
			param.put("url_long", "http://blog.csdn.net/u010327174/article/details/3797129" + i);
			String post = HttpClientUtil.post("http://c1.rtcatchworld.com:8082/short_url", param);
			System.out.println(post.toString());
		}
    	
    }
}
