package cn.tedu.straw.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class RedisTest {

    @Resource
    RedisTemplate<String,String> redisTemplate;

    @Test
    public void hello(){
        //將數據通過set命令保存到Redis
        redisTemplate.opsForValue().set("msg","Hello World");
        String str=redisTemplate.opsForValue().get("msg");
        log.debug("{}",str);
        redisTemplate.delete("msg");
    }
}
