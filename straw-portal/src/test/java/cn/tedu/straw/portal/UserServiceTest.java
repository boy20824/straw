package cn.tedu.straw.portal;

import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.vo.RegisterVo;
import cn.tedu.straw.portal.vo.UserVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    IUserService userService;

    @Test
    public void getUserDetail() {
        UserDetails user = userService.getUserDetails("Tom");
        System.out.println(user);
    }

    @Test
    public void registerStudent(){
        RegisterVo registerVo=new RegisterVo();
        registerVo.setInviteCode("JSD1912-876840");
        registerVo.setPhone("1234567890");
        registerVo.setNickname("神仙");
        registerVo.setPassword("123456");
        registerVo.setConfirm("123456");

        userService.registerStudent(registerVo);
        System.out.println("註冊成功");
    }

    @Test
    @WithMockUser(username = "11111111111" ,password = "123456")
    public void testCurrentUsername(){
        String username=userService.currentUsername();
        System.out.println(username);
    }

    @Test
    public void getMasters(){
        List<User> masters=userService.getMasters();
        masters.forEach(master-> System.out.println(master));
    }

    @Test
    public void getMasterMap(){
        Map<String,User> map=userService.getMasterMap();
        map.forEach((nickname,master)-> System.out.println(nickname+":"+master));
    }

    @Test
    @WithMockUser(username = "11111111111" ,password = "123456")
    public void getCurrentUserVo(){
        UserVO userVO=userService.getCurrentUserVo();
        System.out.println(userVO);
    }
}
