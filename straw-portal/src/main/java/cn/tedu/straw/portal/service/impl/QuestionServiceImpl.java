package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

    @Autowired
    IUserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    QuestionMapper questionMapper;

    /**
     * 按登录用户查询当前用户问题的方法
     *
     * @return List<Question>
     */
    @Override
    public List<Question> getMyQuestions() {
        //获取当前登录用户的用户名
        String username = userService.currentUsername();
        log.debug("当前登录用户为:{}", username);
        //如果已经登录，使用之前编写好的findUserByUsername方法
        //查询出当前用户的详细信息(实际上主要需要用户的id)
        User user = userMapper.findUserByUsername(username);
        if (user == null) {
            throw ServiceException.gone("登录用户不存在！！！");
        }
        log.debug("开始查询{}用户的问题", user.getId());
        QueryWrapper<Question> queryWrapper = new QueryWrapper<>();
        //eq就是等于
        queryWrapper.eq("user_id", user.getId());
        //查询删除状态是0的，就是未删除的
        queryWrapper.eq("delete_status", 0);
        //按照创建时间的倒序
        queryWrapper.orderByDesc("createtime");

        List<Question> list = questionMapper.selectList(queryWrapper);
        log.debug("当前用户的问题数量为:{}", list.size());

        return list;
    }

}
