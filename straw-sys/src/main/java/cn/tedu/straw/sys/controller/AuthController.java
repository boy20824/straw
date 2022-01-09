package cn.tedu.straw.sys.controller;

import cn.tedu.straw.commons.model.Permission;
import cn.tedu.straw.commons.model.Role;
import cn.tedu.straw.commons.model.User;
import cn.tedu.straw.sys.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/v1/auth")
public class AuthController {

    @Resource
    IUserService iUserService;

    @GetMapping("/demo")
    public String demo() {
        log.debug("Call Demo()");
        return "Hello World!";
    }

    @GetMapping("/user")
    public User getUser(String username) {
        return iUserService.getUserByUsername(username);
    }

    @GetMapping("/permissions")
    public List<Permission> getPermissions(Integer userId) {
        return iUserService.getUserPermissions(userId);
    }

    @GetMapping("/roles")
    public List<Role> getRoles(Integer userId) {
        return iUserService.getUserRoles(userId);
    }
}
