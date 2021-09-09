package cn.tedu.straw.sys;

import cn.tedu.straw.sys.mapper.ClassroomMapper;
import cn.tedu.straw.sys.mapper.UserMapper;
import cn.tedu.straw.sys.mapper.UserRoleMapper;
import cn.tedu.straw.sys.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class StrawSysApplicationTests {

    @Resource
    ClassroomMapper classroomMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    UserRoleMapper userRoleMapper;


    @Test
    void contextLoads() {
        System.out.println(classroomMapper);
        System.out.println(userMapper);
        System.out.println(userRoleMapper);
    }

    @Resource
    IUserService iUserService;

    @Test
    void testService() {
        System.out.println(iUserService);
    }

}
