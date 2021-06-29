package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.Question;
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
public interface IQuestionService extends IService<Question> {

    /**
     * 按登录用户查询当前用户问题的方法
     * @Return List<Question>
     */
    List<Question> getMyQuestions();

}
