package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Answer;
import cn.tedu.straw.portal.mapper.AnswerMapper;
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
        QueryWrapper<Answer> query = new QueryWrapper<>();
        query.eq("quest_id", questionId);
        query.orderByAsc("createtime");
        List<Answer> answers = answerMapper.selectList(query);
        return answers;
    }

}
