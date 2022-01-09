package cn.tedu.straw.gateway.security;

import cn.tedu.straw.commons.model.Permission;
import cn.tedu.straw.commons.model.Role;
import cn.tedu.straw.commons.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    RestTemplate restTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        String url="http://sys-service/v1/auth/user?username={1}";
        //url的{1}會被替換成username
        User user =restTemplate.getForObject(url,User.class,username);
        if(user==null){
            throw new UsernameNotFoundException("您是否忘記了用戶名或密碼");
        }
        //獲取用戶全部權限
        url="http://sys-service/v1/auth/permissions?userId={1}";
        //要用回傳的JavaBean陣列型態接收
        Permission[] permissions=restTemplate.getForObject(url,Permission[].class,user.getId());

        //獲取用戶全部角色
        url="http://sys-service/v1/auth/roles?userId={1}";
        Role[] roles=restTemplate.getForObject(url,Role[].class,user.getId());

        //spring-security權限寫入(角色跟權限)
        String[] authorities=new String[permissions.length+roles.length];
        int index=0;
        for(Permission permission:permissions){
            authorities[index++]=permission.getName();
        }
        for(Role role:roles){
            authorities[index++]=role.getName();
        }
        UserDetails userDetails= org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(authorities)
                .accountLocked(user.getLocked().equals(1))
                .disabled(user.getEnabled().equals(0))
                .build();
        log.debug("UserDetail :{}",userDetails);
        return userDetails;


    }
}

