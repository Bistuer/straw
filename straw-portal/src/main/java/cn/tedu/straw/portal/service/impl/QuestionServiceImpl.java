package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.QuestionTagMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.mapper.UserQuestionMapper;
import cn.tedu.straw.portal.model.*;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ITagService;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.QuestionVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    QuestionTagMapper questionTagMapper;
    @Autowired
    UserQuestionMapper userQuestionMapper;

    /**
     * 按登录用户查询当前用户问题的方法
     *
     * @param pageNum  页码
     * @param pageSize 每页显示数量
     * @return PageInfo<Question>
     */
    @Override
    public PageInfo<Question> getMyQuestions(
            //传入分页查询的参数
            Integer pageNum, Integer pageSize
    ) {
        //分页查询,决定查询的页数
        if (pageNum == null || pageSize == null) {
            //分页查询信息不全,直接抛异常
            throw ServiceException.invalidRequest("参数不能为空");
        }
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
        //执行查询之前，要设置分页查询信息
        PageHelper.startPage(pageNum, pageSize);
        //紧接着的查询就是按照上面分页配置的分页查询
        List<Question> list = questionMapper.selectList(queryWrapper);
        log.debug("当前用户的问题数量为:{}", list.size());

        //遍历当前查询出的所有对象问题
        for (Question question : list) {
            //将问题每个对象的对应的Tag都查询出来并赋值给实体类中的List<Tag>
            List<Tag> tags = tagNamesToTags(question.getTagNames());
            question.setTags(tags);
        }
        return new PageInfo<Question>(list);
    }

    /**
     * 保存用户发布信息的方法
     *
     * @param questionVo 问题的实体类
     * @return void
     */
    @Transactional
    @Override
    public void saveQuestion(QuestionVo questionVo) {
        log.debug("收到问题数据{}", questionVo);
        // 获取当前登录用户信息(可以验证登录情况)
        String username = userService.currentUsername();
        User user = userMapper.findUserByUsername(username);
        // 将该问题包含的标签拼接成字符串以","分割 以便添加tag_names列
        StringBuilder bud = new StringBuilder();
        for (String tag : questionVo.getTagNames()) {
            bud.append(tag).append(",");
        }
        //删除最后一个","
        bud.deleteCharAt(bud.length() - 1);
        String tagNames = bud.toString();
        // 构造Question对象
        Question question = new Question()
                .setTitle(questionVo.getTitle())
                .setContent(questionVo.getContent())
                .setUserId(user.getId())
                .setUserNickName(user.getNickname())
                .setTagNames(tagNames)
                .setCreatetime(LocalDateTime.now())
                .setStatus(0)
                .setPageViews(0)
                .setPublicStatus(0)
                .setDeleteStatus(0);
        // 新增Question对象
        int num = questionMapper.insert(question);
        if (num != 1) {
            throw new ServiceException("服务器忙!");
        }
        log.debug("保存了对象:{}", question);
        // 处理新增的Question和对应Tag的关系
        Map<String, Tag> name2TagMap = tagService.getName2TagMap();
        for (String tagName : questionVo.getTagNames()) {
            //根据本次循环的标签名称获得对应的标签对象
            Tag tag = name2TagMap.get(tagName);
            //构建QuestionTag实体类对象
            QuestionTag questionTag = new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(tag.getId());
            //执行新增
            num = questionTagMapper.insert(questionTag);
            if (num != 1) {
                throw new ServiceException("数据库忙!");
            }
            log.debug("新增了问题和标签的关系:{}", questionTag);
        }

        // 处理新增的Question和对应User(老师)的关系
        Map<String, User> masterMap = userService.getMasterMap();
        for (String masterName : questionVo.getTeacherNickNames()) {
            //根据本次循环的讲师名称获得对应的讲师对象
            User uu = masterMap.get(masterName);
            //构建QuestionTag实体类对象
            UserQuestion userQuestion = new UserQuestion()
                    .setQuestionId(question.getId())
                    .setUserId(uu.getId())
                    .setCreatetime(LocalDateTime.now());
            //执行新增
            num = userQuestionMapper.insert(userQuestion);
            if (num != 1) {
                throw new ServiceException("数据库忙!");
            }
            log.debug("新增了问题和讲师的关系:{}", userQuestion);
        }

    }


    @Autowired
    ITagService tagService;

    /**
     * 根据数据库question表的tag_names字段(列)的值,返回List<Tag>
     */
    private List<Tag> tagNamesToTags(String tagNames) {
        //将得到的tag_name拆分字符串 如："Java基础,SpringCloud" 拆分后的得到{"Java基础","SpringCloud"}
        String[] names = tagNames.split(",");
        //声明List以便返回  list最后存的是tag表的对象
        List<Tag> list = new ArrayList<>();
        //map结果:如key:JAVA基础 value:1,java基础,admin,2020-03-09 14:39:48
        Map<String, Tag> map = tagService.getName2TagMap();
        //遍历String数组
        for (String name : names) {
            //根据String数组当前的元素.获得map对应的value
            Tag tag = map.get(name);
            //将这个value保存在list对象中
            list.add(tag);
        }
        return list;
    }

}
