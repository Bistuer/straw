package cn.tedu.straw.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@Slf4j
public class HomeController {

    @GetMapping("/register.html")
    public ModelAndView register() {
        return new ModelAndView("register");
    }
}
