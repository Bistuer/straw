package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.service.ITagService;
import cn.tedu.straw.portal.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */

/**
 * 在 Spring Boot 中，接口返回 JSON 格式的数据很简单，
 * 在 Controller 中使用 @RestController 注解即可返回 JSON 格式的数据。
 *
 * @RestController 注解包含了原来的 @Controller 和 @ResponseBody注解。
 * <p>
 * 下面的注解表示想访问本控制器中的任何方法需要前缀/v1/tags
 * 这个v1开头的格式是后期微服务的标准名为RESTful
 */
@RestController
@RequestMapping("/v1/tags")
public class TagController {

    @Autowired
    private ITagService tagService;

    /**
     * 查询所有标签@GetMapping("")表示使用类上声明的前缀就可以访问这个方法就是 /v1/tags就可以访问这个方法
     *
     * @Date 2021/06/26
     */
    @GetMapping("")
    public R<List<Tag>> tags() {
        List<Tag> list = tagService.getTags();
        return R.ok(list);
    }

}
