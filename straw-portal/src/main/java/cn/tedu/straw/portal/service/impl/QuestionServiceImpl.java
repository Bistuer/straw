package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

}
