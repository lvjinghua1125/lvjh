package xsxsxs;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring/applicationContext*.xml"}) //加载配置文件
public class AllTests {
 
    @Resource
    RedisTemplate<String, String> redisTemplate;
    @Test
    public void test1() {
        System.err.println("aaa");
        redisTemplate.opsForValue().set("aaa3", "abcdef");
        String value = redisTemplate.opsForValue().get("aaa");
        System.err.println(value);
    }
  
}
