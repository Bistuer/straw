package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Permission;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class SecurityTest {

    @Autowired(required = false)
    PasswordEncoder passwordEncoder;

    @Test
    public void encodeTest() {
        /**
         * 每次运行加密结果不同
         * 是因为加密对象采用了"随机加盐"技术,提高安全性
         */
        String pwd = passwordEncoder.encode("123456");
        System.out.println(pwd);
        //$2a$10$I.xH0Ih7R7sU.gm7wpOov.eCWUF4oa/o0Vftci61n2a/VuIdJmyRu
    }

    @Test
    public void matchTest() {
        /*
        验证我们输入的密码是不是能匹配生成的密文
         */
        boolean b = passwordEncoder.matches("123456", "$2a$10$0.xDn7j/1WpZEtsRzkYZLO4XIkP4Dkh2pmwdDmtSEm3sdJm/t9jUu");
        System.out.println(b);
    }

    @Autowired
    UserMapper userMapper;

    @Test
    public void findUser() {
        User user = userMapper.findUserByUsername("tc2");
        System.out.println(user);
    }

    @Test
    public void findPermissions() {
        List<Permission> list = userMapper.findUserPermissionsById(3);
        for (Permission permission : list) {
            System.out.println(permission);
        }
    }

    @Autowired
//    IUserService userService;
    UserServiceImpl userService;
    @Test
    public void abcLogin() {
        UserDetails ud = userService.getUserDetails("tc2");
        System.err.println(ud);
    }

}
