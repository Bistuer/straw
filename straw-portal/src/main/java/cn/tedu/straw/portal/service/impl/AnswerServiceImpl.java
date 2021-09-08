package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.mapper.AnswerMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IAnswerService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.AnswerVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

    @Resource
    private AnswerMapper answerMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private QuestionMapper questionMapper;

    /**
     * 提交讲师回复问题的答案信息
     *
     * @param answerVo
     * @param username
     * @return
     */
    @Override
    @Transactional
    public Answer saveAnswer(AnswerVo answerVo, String username) {
        //收集信息,先获得当前回答问题的讲师的用户信息,结合answerVo
        User user = userMapper.findUserByUsername(username);
        Answer answer = new Answer()
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setContent(answerVo.getContent())
                .setQuestId(answerVo.getQuestionId())
                .setLikeCount(0)
                .setAcceptStatus(0)
                .setCreatetime(LocalDateTime.now());
        int rows = answerMapper.insert(answer);
        if (rows != 1) {
            throw new ServiceException("数据库忙!");
        }
        return answer;
    }

    /**
     * 根据问题id查询这个问题的所有回答的方法
     */
    @Override
    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        if (questionId == null) {
            throw ServiceException.invalidRequest("问题id不能为空");
        }
        List<Answer> answers = answerMapper.findAnswersByQuestionId(questionId);
        return answers;
    }

    /**
     * 采纳问题答案的方法
     *
     * @param answerId
     * @return boolean
     */
    @Override
    @Transactional
    public boolean accept(Integer answerId) {
        //查询当前要采纳的answer对象
        Answer answer = answerMapper.selectById(answerId);
        //判断这个answer是不是已经被采纳
        if (answer.getAcceptStatus() == 1) {
            //如果已经被采纳返回false
            return false;
        }
        //开始执行采纳业务
        answer.setAcceptStatus(1);
        int num = answerMapper.updateStatus(answerId, answer.getAcceptStatus());
        if (num != 1) {
            throw ServiceException.busy();
        }
        //修改问题状态为已解决
        num = questionMapper.updateStatus(answer.getQuestId(), Question.SOLVED);
        if (num != 1) {
            throw ServiceException.busy();
        }
        return true;
    }

}
