package cn.tedu.straw.gateway;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class RibbonTest {

    @Resource
    RestTemplate restTemplate;

    @Test
    public void demo(){
        log.debug("Start Ribbon Test");
        String url="http://sys-service/v1/auth/demo";
        String str=restTemplate.getForObject(url,String.class);
        log.debug("Ribbon Result :{}",str);
    }

}
