package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StrawPortalApplicationTests {

    @Autowired(required = false)
    TagMapper tagMapper;

    @Autowired
    UserMapper userMapper;

//    @Test
//    void contextLoads() {
//        Msg m1 = new Msg();
//        m1.setId(1);
//        m1.setName("新闻");
//        m1.setContent("很厉害的新闻");
//
//        System.out.println(m1);
//    }

    @Test
    public void testTag() {
        Tag tag = tagMapper.selectById(14);
        System.out.println(tag);
    }

    @Test
    public void selectUser() {
        User user = userMapper.selectById(3);
        System.out.println(user);
    }

}
