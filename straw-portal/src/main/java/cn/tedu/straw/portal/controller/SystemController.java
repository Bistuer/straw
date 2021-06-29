package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.R;
import cn.tedu.straw.portal.vo.RegisterVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
     * @Validated 验证registerVo 是否符合类中成员变量上面的注解 就是@NotBlank(message = "昵称不能为空")，
     * @Pattern(regexp = "^.{2,20}$", message = "昵称在2到20位之间") 这些注解
     * BindingResult validaResult 上面注解的结果会赋值给validaResult 是固定用法
     * @Date 2021/06/14
     */
    @PostMapping("/register")
    public R registerStudent(@Validated RegisterVo registerVo, BindingResult validaResult) {
        //在控制器调用业务逻辑前，先判断BindingResult对象中是否有错误
        if (validaResult.hasErrors()) {
            //如果验证结果中包含任何错误信息，进入这个if
            //获得其中的一个错误信息显示，一般是按顺序第一个错误信息
            String error = validaResult.getFieldError().getDefaultMessage();
            return R.unproecsableEntity(error);
        }

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
