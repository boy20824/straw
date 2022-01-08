package cn.tedu.straw.sys.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/v1/auth")
public class AuthController {

    @GetMapping("/demo")
    public String demo(){
        log.debug("Call Demo()");
        return "Hello World!";
    }
}
