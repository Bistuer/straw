package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
