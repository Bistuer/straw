package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.mapper.UserRoleMapper;
import cn.tedu.straw.portal.model.Classroom;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.vo.RegisterVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@SpringBootTest
public class QueryWrapperTest {

    @Autowired
    ClassroomMapper classroomMapper;

    @Autowired
    UserRoleMapper userRoleMapper;

    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Test
    public void testWrapper() {

        //按邀请码查询Classroom对象  <>泛型里面放入实体类，这个实体类对应mysql中的一个表
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        //设置条件  .eq   key value
        queryWrapper.eq("invite_code", "JSD1912-876840");
        //执行查询
        Classroom cr = classroomMapper.selectOne(queryWrapper);

        System.out.println(cr);
    }

    @Autowired
    IUserService userService;

    @Test
    public void testUserService() {
        RegisterVo registerVo = new RegisterVo();
        registerVo.setPhone("13188888888");
        registerVo.setNickname("大树");
        registerVo.setInviteCode("JSD1912-876840");
        registerVo.setPassword("123456");
        registerVo.setConfirm("123456");
        System.out.println("complate!");
    }

}
