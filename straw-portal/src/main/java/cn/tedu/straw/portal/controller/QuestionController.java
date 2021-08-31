package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.QuestionVo;
import cn.tedu.straw.portal.vo.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/v1/questions")
@Slf4j
public class QuestionController {

    @Autowired
    IQuestionService questionService;

    /**
     * 查询返回当前登录用户发布的问题
     *
     * @return R<PageInfo < Question>>
     */
    @GetMapping("/my")
    public R<PageInfo<Question>> my(Integer pageNum) {
        if (pageNum == null) {
            pageNum = 1;
        }
        int pageSize = 8;
        log.debug("开始查询当前用户的问题");
        //这里要处理个异常,因为用户可能没有登录
        try {
            PageInfo<Question> questions =
                    questionService.getMyQuestions(pageNum, pageSize);
            return R.ok(questions);
        } catch (ServiceException e) {
            log.error("用户查询问题失败!", e);
            return R.failed(e);
        }
    }

    /**
     * 学生发布问题的控制器方法
     *
     * @param questionVo 问题的实体类
     * @param result     验证questionVo里面那些注解是否符合要求
     * @return R
     * @Validated questionVo 是否符合类中成员变量上面的注解 就是@NotBlank(message = "不能为空")，结果放在BindingResult
     */
    @PostMapping
    public R createQuestion(@Validated QuestionVo questionVo, BindingResult result) {
        //如果有问题result.hasErrors()=true
        if (result.hasErrors()) {
            String message = result.getFieldError()
                    .getDefaultMessage();
            log.warn(message);
            return R.unproecsableEntity(message);
        }
        if (questionVo.getTagNames().length == 0) {
            log.warn("必须选择至少一个标签");
            return R.unproecsableEntity("必须选择至少一个标签");
        }
        if (questionVo.getTeacherNickNames().length == 0) {
            log.warn("必须选择至少一个老师");
            return R.unproecsableEntity("必须选择至少一个老师");
        }
        //这里应该将vo对象交由service层去新增
        log.debug("接收到表单数据{}", questionVo);
        return R.ok("发布成功!");
    }

}
