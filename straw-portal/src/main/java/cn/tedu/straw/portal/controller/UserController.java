package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.vo.R;
import cn.tedu.straw.portal.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-11-14
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    IUserService userService;

//   當初測試spring-security
//    @GetMapping("/get")
//    /*
//    如果登錄用戶有"/user/get"權限時候,才能執行get(id)方法
//     */
//    @PreAuthorize("hasAnyAuthority('/user/get')")
//    public User get(int id) {
//        User user = userService.getById(id);
//        return user;
//    }
//
//    @GetMapping("/list")
//    /*
//    如果登錄用戶有"/user/list"權限時候,才能執行list()方法
//     */
//    @PreAuthorize("hasAnyAuthority('/user/list')")
//    public List<User> list() {
//        return userService.list();
//    }

    @GetMapping("/master")
    public R<List<User>> getMasters(){
        List<User> masters=userService.getMasters();
        return R.ok(masters);
    }

    /**
     * 請求路徑 /v1/users/me
     * @return
     */
    @GetMapping("/me")
    public R<UserVO> me(){
        UserVO userVO=userService.getCurrentUserVo();
        return R.ok(userVO);
    }



}
