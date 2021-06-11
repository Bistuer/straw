package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 这个类实现UserDetailsService接口
 * 为Spring-Security提供认证的数据
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    //Spring-Security认证信息时
    //会将用户名传递到这个方法中
    //根据这个用户名获得数据库中加密的密码,
    //如果匹配则登录成功

    @Autowired
    IUserService userService;

    @Override
//    loadUserByUsername(String username)拿到了用户名
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //这个方法返回的是用户的详细信息
//        UserDetails user = null;
//        //暂时规定正确的用户名和密码是"Jerry",加密的"123456"
//        if ("Jerry".equals(username)) {
//            //如果用户名正确,将加密的密码保存到User对象
//            //下面的User类也是Spring提供的不是我们的实体类
//            user = User.builder()
//                    .username("Jerry")
//                    .password("{bcrypt}$2a$10$I.xH0Ih7R7sU.gm7wpOov.eCWUF4oa/o0Vftci61n2a/VuIdJmyRu")
//                    //这里也能直接赋予权限
//                    .authorities("/tag/get")
//                    .build();
//        }
//        System.out.println("user:" + user);
//        return user;
        return userService.getUserDetails(username);
    }
}

