package cn.tedu.straw.faq;

import cn.tedu.straw.faq.mapper.QuestionMapper;
import cn.tedu.straw.faq.mapper.QuestionTagMapper;
import cn.tedu.straw.faq.mapper.TagMapper;
import cn.tedu.straw.faq.mapper.UserQuestionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StrawFaqApplicationTests {

    @Resource
    QuestionMapper questionMapper;
    @Resource
    QuestionTagMapper questionTagMapper;
    @Resource
    TagMapper tagMapper;
    @Resource
    UserQuestionMapper userQuestionMapper;

    @Test
    void contextLoads() {
        System.out.println(questionMapper);
        System.out.println(questionTagMapper);
        System.out.println(tagMapper);
        System.out.println(userQuestionMapper);



    }
}
