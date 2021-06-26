package cn.tedu.straw.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 对应index.html的控制类
 * @author fanzhen
 * @Date 2021.06.26
 */
@RestController
@Slf4j
public class HomeController {

    /**
     * 显示首页
     * @return ModelAndView
     */
    @GetMapping("/index.html")
    public ModelAndView index(){
        return new ModelAndView("index");
    }



}
