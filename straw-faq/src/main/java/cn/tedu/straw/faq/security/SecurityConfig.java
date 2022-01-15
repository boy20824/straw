package cn.tedu.straw.faq.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true) //開啟權限註解
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //覆寫configure(HttpSecurity)方法
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //放過全部的請求,安全功能由straw-gateway負責
        http.csrf().disable().authorizeRequests().anyRequest().permitAll();
    }
}
