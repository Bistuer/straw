package cn.tedu.straw.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * @author fanzhen
 * @date 2021/9/10
 */
@RestController
@Slf4j
public class HomeController {


    /**
     * 声明两个常亮以便判断用户的角色
     */
    static final GrantedAuthority STUDENT = new SimpleGrantedAuthority("ROLE_STUDENT");
    static final GrantedAuthority TEACHER = new SimpleGrantedAuthority("ROLE_TEACHER");

    @GetMapping("/register.html")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    /**
     * 显示首页
     *
     * @param user
     * @return
     */
    @GetMapping("/index.html")
    public ModelAndView index(@AuthenticationPrincipal User user) {
        if (user.getAuthorities().contains(STUDENT)) {
            return new ModelAndView("index");
        } else if (user.getAuthorities().contains(TEACHER)) {
            return new ModelAndView("index_teacher");
        }
        return null;
    }

}
