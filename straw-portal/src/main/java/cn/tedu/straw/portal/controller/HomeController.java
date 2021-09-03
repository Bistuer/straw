package cn.tedu.straw.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.MARSHAL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


/**
 * 对应index.html的控制类
 *
 * @author fanzhen
 * @Date 2021.06.26
 */
@RestController
@Slf4j
public class HomeController {

    /**
     * 声明两个常亮以便判断用户的角色
     */
    static final GrantedAuthority STUDENT = new SimpleGrantedAuthority("ROLE_STUDENT");
    static final GrantedAuthority TEACHER = new SimpleGrantedAuthority("ROLE_TEACHER");

    /**
     * 显示首页
     * AuthenticationPrincipal 注解后面跟Spring-Security的User类型参数 (如上面的STUDENT TEACHER)
     * 表示需要Spring-Security将当前登录用户的权限信息赋值给User对象
     * 以便我们在方法中验证他的权限或身份
     * 参数User导入 org.springframework.security.core.userdetails.User 这个包
     *
     * @return ModelAndView
     */
    @GetMapping("/index.html")
    public ModelAndView index(
            @AuthenticationPrincipal User user) {
        //  根据Spring-Security提供的用户判断权限,绝对返回哪个页面
        if (user.getAuthorities().contains(STUDENT)) {
            return new ModelAndView("index");
        } else if (user.getAuthorities().contains(TEACHER)) {
            return new ModelAndView("index_teacher");
        }
        return null;
    }


    /**
     * 显示学生问题发布页面
     */
    @GetMapping("/question/create.html")
    public ModelAndView createQuestion() {
        //return new ModelAndView("question/create"); 返回的是templates目录下的question/create
        return new ModelAndView("question/create");
    }

//    //    临时显示讲师主页的控制器方法
//    @GetMapping("/index_teacher.html")
//    public ModelAndView indexTeacher() {
//        return new ModelAndView("index_teacher");
//    }

}
