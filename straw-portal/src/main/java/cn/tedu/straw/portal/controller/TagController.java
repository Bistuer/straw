package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.ITagService;
import cn.tedu.straw.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@RestController
//下面的注解表示想访问本控制器中的任何方法需要前缀/portal/tag
@RequestMapping("/portal/tag")
public class TagController {

    @Autowired(required = false)
    private IUserService userService;

    @GetMapping("/get")  //localhost/portal/tag/get?id=3
    //获取权限可以正常访问/tag/get
    @PreAuthorize("hasAuthority('/tag/get')")
    public User get(Integer id) {
        return userService.getById(3);
    }

    @GetMapping("/request")
    public User getUser(Integer id) {
        return userService.getById(5);
    }

    @Autowired
    private ITagService tagService;

    //编写一套可以按tagid查询一个tag信息的控制方法
    @GetMapping("/gettag")
    public Tag getTag(Integer id) {
        return tagService.getById(14);
    }

}
