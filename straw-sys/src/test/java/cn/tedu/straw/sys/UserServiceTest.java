package cn.tedu.straw.sys;

import cn.tedu.straw.commons.model.Permission;
import cn.tedu.straw.commons.model.Role;
import cn.tedu.straw.commons.model.User;
import cn.tedu.straw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
public class UserServiceTest {

    @Resource
    IUserService iUserService;

    @Test
    void userInfo(){
        User user=iUserService.getUserByUsername("11111111111");
        log.debug("User:{}",user);

        List<Permission> permissionList=iUserService.getUserPermissions(user.getId());
        List<Role> roleList=iUserService.getUserRoles(user.getId());

        permissionList.forEach(permission -> log.debug("{}",permission));
        roleList.forEach(role -> log.debug("{}",role));
    }

}
