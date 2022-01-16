package cn.tedu.straw.gateway.controller;

import cn.tedu.straw.commons.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.sql.rowset.serial.SerialException;

@Controller
@Slf4j
public class HomeController {

    static final GrantedAuthority STUDENT = new SimpleGrantedAuthority("ROLE_STUDENT");
    static final GrantedAuthority TEACHER = new SimpleGrantedAuthority("ROLE_TEACHER");
    
    @GetMapping("/register.html")
    public String register(){
        log.debug("轉發到register");
        return "register";
    }
    
    @GetMapping("/login.html")
    public String login(){
        return "login";
    }

    @GetMapping("/index.html")
    public String index(@AuthenticationPrincipal UserDetails userDetails){

        if(userDetails.getAuthorities().contains(STUDENT)){
            //當前登錄用戶的角色是學生,轉到學生首頁
            return "index";
        }else if(userDetails.getAuthorities().contains(TEACHER)){
            //當前登錄用戶的角色是老師,轉到老師首頁
            return "index_teacher";
        }
        throw new ServiceException("需要登錄");
    }

    @GetMapping("/question/create.html")
    public String create(){
        return "question/create";
    }

    @GetMapping("/question/detail.html")
    public String detail(@AuthenticationPrincipal UserDetails userDetails){

        if(userDetails.getAuthorities().contains(STUDENT)){
            //當前登錄用戶的角色是學生,轉到學生首頁
            return "question/detail";
        }else if(userDetails.getAuthorities().contains(TEACHER)){
            //當前登錄用戶的角色是老師,轉到老師首頁
            return "question/detail_teacher.html";
        }
        throw new ServiceException("需要登錄");
    }
}
