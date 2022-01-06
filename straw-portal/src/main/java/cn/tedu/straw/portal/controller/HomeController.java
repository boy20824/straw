package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.service.ServiceException;
import com.sun.org.apache.xpath.internal.operations.Mod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class HomeController {

    static final GrantedAuthority STUDENT = new SimpleGrantedAuthority("ROLE_STUDENT");
    static final GrantedAuthority TEACHER = new SimpleGrantedAuthority("ROLE_TEACHER");

    /**
     * @return
     * @AuthenticationPrincipal User user 注入spring-Security中
     * 已經登錄的用戶訊息,類型是 org.springframework.security.core.userdetails.User
     */
    @GetMapping("/index.html")
    public ModelAndView index(@AuthenticationPrincipal User user) {
        log.debug("當前登錄用戶 {}", user);
        //檢查用戶是否是 學生身分
        if (user.getAuthorities().contains(STUDENT)) {
            //轉發到學生頁面
            return new ModelAndView("index");
        } else if (user.getAuthorities().contains(TEACHER)) {
            //轉發到講師頁面
            return new ModelAndView("index_teacher");
        }
        throw new ServiceException("需要登錄!!");
    }

    @GetMapping("/question/create.html")
    public ModelAndView create() {
        return new ModelAndView("question/create");
    }

//    @GetMapping("/index_teacher.html")
//    public ModelAndView indexTeacher() {
//        return new ModelAndView("index_teacher");
//    };

    @GetMapping("/question/detail.html")
    public ModelAndView detail(@AuthenticationPrincipal User user) {
        if (user.getAuthorities().contains(STUDENT)) {
            return new ModelAndView("question/detail");
        } else if (user.getAuthorities().contains(TEACHER)) {
            return new ModelAndView("question/detail_teacher");
        }
        throw new ServiceException("需要登錄");
    }
}
