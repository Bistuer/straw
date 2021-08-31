package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.vo.QuestionVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
public interface IQuestionService extends IService<Question> {

    /**
     * 按登录用户查询当前用户问题的方法
     *
     * @Return List<Question>
     */
    PageInfo<Question> getMyQuestions(Integer pageNum, Integer pageSize);

    /**
     * 保存用户发布信息的方法
     *
     * @param questionVo 问题的实体类
     * @return void
     */
    void saveQuestiton(QuestionVo questionVo);
}
