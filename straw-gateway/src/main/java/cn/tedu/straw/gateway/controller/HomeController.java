package cn.tedu.straw.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {
    
    @GetMapping("/register.html")
    public String register(){
        log.debug("轉發到register");
        return "register";
    }
    
    @GetMapping("/login.html")
    public String login(){
        return "login";
    }
}
