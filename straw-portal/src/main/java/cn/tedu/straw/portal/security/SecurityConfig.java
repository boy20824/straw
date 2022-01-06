package cn.tedu.straw.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)//開啟註解權限管理
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
        添加一個測試用戶,用戶:Tom 密碼:123456 權限:/user/get
         */
//        auth.inMemoryAuthentication().withUser("Tom").password("{bcrypt}$2a$10$lfhw.eGhYq1UY5o7jcJByOFV/D.WRdwNgWuUAQZ36Emip9uIKkVjm")
//                .authorities("/user/get","/user/list");
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                //不需要驗證就可以登錄
                .antMatchers(
                        "/login.html",
                        "/img/*",
                        "/js/*",
                        "/css/*",
                        "/browser_components/**",
                        "/register.html",
                        "/register"
                ).permitAll()
                //其他都需要驗證才能登錄
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error")
                .defaultSuccessUrl("/index.html",true)
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html?logout");
    }
}
