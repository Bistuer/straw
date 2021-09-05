package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.vo.AnswerVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
public interface IAnswerService extends IService<Answer> {

    /**
     * 提交讲师回复问题的答案信息
     *
     * @param answerVo
     * @param username
     * @return Answer
     */
    Answer saveAnswer(AnswerVo answerVo, String username);

    /**
     * 根据问题id查询这个问题的所有回答的方法
     *
     * @param questionId
     * @return List<Answer>
     */
    List<Answer> getAnswersByQuestionId(Integer questionId);

}
