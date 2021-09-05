package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * BaseMapper是框架MybatisPlus提供的
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {

    /**
     * 复杂映射查询按问题id获得所有回答以及每个回答包含的评论
     *
     * @param questionId
     * @return
     */
    List<Answer> findAnswersByQuestionId(Integer questionId);

}
