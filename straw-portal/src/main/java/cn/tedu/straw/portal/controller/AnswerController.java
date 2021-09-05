package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.vo.AnswerVo;
import cn.tedu.straw.portal.vo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.print.attribute.standard.PresentationDirection;
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
@RequestMapping("/v1/answers")
@Slf4j
public class AnswerController {

    @Resource
    private IAnswerService answerService;

    /**
     * 新增回复的控制方法
     *
     * @param answerVo
     * @param result
     * @param user
     * @return R
     */
    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public R postAnswer(@Validated AnswerVo answerVo, BindingResult result, @AuthenticationPrincipal User user) {
        log.debug("收到回复信息{}", answerVo);
        if (result.hasErrors()) {
            String message = result.getFieldError().getDefaultMessage();
            log.warn(message);
            return R.unproecsableEntity(message);
        }
        //这里调用业务逻辑层方法
        Answer answer = answerService.saveAnswer(answerVo, user.getUsername());
        return R.created(answer);
    }

    /**
     * 根据问题id获得这个问题的所有回答的方法 例如:/v1/answers/question/12
     * PathVariable Integer id 把这个id传递给GetMapping中的id
     *
     * @param id
     * @return
     */
    @GetMapping("/question/{id}")
    public R<List<Answer>> questionAnswers(@PathVariable Integer id) {
        if (id == null) {
            return R.invalidRequest("问题ID不能为空!");
        }
        List<Answer> answers = answerService
                .getAnswersByQuestionId(id);
        return R.ok(answers);
    }
}
