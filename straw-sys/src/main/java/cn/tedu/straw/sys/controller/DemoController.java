package cn.tedu.straw.sys.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/sys")
public class DemoController {

    @GetMapping("/demo")
    public String demo() {
        return "Hello Sys";
    }
}
