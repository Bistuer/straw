package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@Repository
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 查询被学生指定老师解答的问题和老师自己提出的问题
     *
     * @param userId
     * @return List<Question>
     */
    @Select("SELECT q.* " +
            " FROM question q" +
            " LEFT JOIN user_question uq " +
            "      ON q.id=uq.question_id" +
            " WHERE uq.user_id=#{userId} OR q.user_id=#{userId}" +
            " ORDER BY q.createtime DESC")
    List<Question> findTeachersQuestions(Integer userId);
}
