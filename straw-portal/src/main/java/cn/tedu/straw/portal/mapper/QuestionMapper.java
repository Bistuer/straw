package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    /**
     * 根据问题id修改问题状态
     * 当问题的答案被采纳时,question表问题的状态改为status=2, answer表的accept_status=1
     *
     * @param questionId
     * @param status
     * @return int
     */
    @Update("update question set status=#{status} " +
            " where id=#{questionId}")
    int updateStatus(@Param("questionId") Integer questionId, @Param("status") Integer status);

}
