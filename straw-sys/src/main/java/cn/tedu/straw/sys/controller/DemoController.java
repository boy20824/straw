package cn.tedu.straw.sys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/sys")
public class DemoController {
    
    @GetMapping("/demo")
    public String demo(){
        log.debug("執行了Demo");
        return "Hello sys";
    }

    @GetMapping("/me")
    public String me(@AuthenticationPrincipal UserDetails userDetails){
        log.debug("userDetails: {}",userDetails);
        return userDetails.getUsername();
    }
}
