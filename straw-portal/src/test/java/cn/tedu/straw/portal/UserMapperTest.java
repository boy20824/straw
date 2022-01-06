package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Permission;
import cn.tedu.straw.portal.model.Role;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void findUserByUsername(){
        User user=userMapper.findUserByUsername("Tom");
        System.out.println(user);
    }

    @Test
    public void findUserPermissionById(){
        List<Permission> list=userMapper.findUserPermissionById(1);
         list.forEach(permission -> System.out.println(permission));
    }

    @Test
    public void getUserVoByUsername(){
        UserVO userVO=userMapper.getUserVoByUsername("11111111111");
        System.out.println(userVO);
    }

    @Test
    public void findUserRolesById(){
        List<Role> list=userMapper.findUserRolesById(1);
        list.forEach(role->log.debug("role:{}",role));
    }
}
