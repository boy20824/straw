package cn.tedu.straw.sys;

import cn.tedu.straw.sys.mapper.ClassroomMapper;
import cn.tedu.straw.sys.mapper.UserMapper;
import cn.tedu.straw.sys.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class MapperTest {

    @Resource
    ClassroomMapper classroomMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    UserRoleMapper userRoleMapper;

    @Test
    public void mappers(){
        log.debug("{}",classroomMapper);
        log.debug("{}",userMapper);
        log.debug("{}",userRoleMapper);
    }
}
