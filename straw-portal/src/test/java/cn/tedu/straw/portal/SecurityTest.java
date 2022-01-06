package cn.tedu.straw.portal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


@SpringBootTest
public class SecurityTest {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testEncoder(){
        /*
        測試密碼加密功能
         */
//        String pwd =passwordEncoder.encode("123456");
//        System.out.println(pwd);
        //$2a$10$lfhw.eGhYq1UY5o7jcJByOFV/D.WRdwNgWuUAQZ36Emip9uIKkVjm
    }

    @Test
    public void testMatch(){
        /*
        測試密碼比對功能
         */
//        boolean b=passwordEncoder.matches("123456","$2a$10$lfhw.eGhYq1UY5o7jcJByOFV/D.WRdwNgWuUAQZ36Emip9uIKkVjm");
//        System.out.println(b);
    }

}
