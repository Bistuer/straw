package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.R;
import cn.tedu.straw.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author fanzhen
 * @sl4j 是启动日志功能
 */
@RestController
@Slf4j
public class SystemController {


    /**
     * 之前我们把index.html，放在static下是SpringBoot会自动将static下的Index.html作为默认的主页
     * 在网址输入localhost:80 会跳转到static下的index.html
     * 但是我们现在讲index.html放在thymeleaf 优先级 就要比 static 高
     * <p>
     * 可以看看下面的帖子
     * https://www.it610.com/article/1275475344475832320.htm
     * <p>
     * 显示登录页面的方法
     *
     * @return ModelAndView
     */
    @GetMapping("/login.html")
    public ModelAndView loginForm() {
        //ModelAndView("login");对应的是resources/templates/login.html
        return new ModelAndView("login");
    }

    /**
     * 显示注册页面的方法
     *
     * @return ModelAndView
     */
    @GetMapping("/register.html")
    public ModelAndView register() {
        return new ModelAndView("register");
    }

    @Autowired
    IUserService userService;

    /**
     * @param registerVo
     * @return R
     * @Date 2021/06/14
     */
    @PostMapping("/register")
    public R registerStudent(RegisterVo registerVo) {
        System.out.println(registerVo);
        //{}是占位符，会将后面的registerVo数据放到{}中
        log.debug("得到信息为:{}", registerVo);
        try {
            userService.registerStudent(registerVo);
            return R.created("注册成功!");
        } catch (ServiceException e) {
            log.error("注册失败", e);
            return R.failed(e);
        }
    }

}
