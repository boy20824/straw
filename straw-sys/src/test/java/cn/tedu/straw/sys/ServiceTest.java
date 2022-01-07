package cn.tedu.straw.sys;

import cn.tedu.straw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class ServiceTest {

    @Resource
    IUserService iUserService;

    @Test
    public void service(){
        log.debug("{}",iUserService);

    }
}
