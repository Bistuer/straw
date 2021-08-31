package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.mapper.ClassroomMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.mapper.UserRoleMapper;
import cn.tedu.straw.portal.model.Classroom;
import cn.tedu.straw.portal.model.Permission;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserRole;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ServiceException;
import cn.tedu.straw.portal.vo.RegisterVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@Slf4j
@Service  //注入到spring容器中
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassroomMapper classroomMapper;
    @Autowired
    UserRoleMapper userRoleMapper;

    /**
     * 用户密码不能明文输入
     * 工具类
     */
    BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    @Override
    public UserDetails getUserDetails(String username) {
        //根据用户名获得用户对象
        User user = userMapper.findUserByUsername(username);
        //判断用户对象是否为空
        if (user == null) {
            //如果为空直接返回null
            return null;
        }
        //如果不为空根据用户的id查询这个用户的所有权限
        List<Permission> permissions =
                userMapper.findUserPermissionsById(user.getId());
        //将权限List中的权限转成数组方便赋值
        String[] auths = new String[permissions.size()];
        for (int i = 0; i < auths.length; i++) {
            auths[i] = permissions.get(i).getName();
        }
        //创建UserDetails对象,并为他赋值
        UserDetails ud = org.springframework.security.core.userdetails
                .User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                //写==1是判断锁定  0是正常
                .accountLocked(user.getLocked() == 1)
                //写==0是判断不可用   1是正常
                .disabled(user.getEnabled() == 0)
                .authorities(auths).build();
        //最后返回UserDetails对象
        return ud;
    }

    @Override
    public void registerStudent(RegisterVo registerVo) {
        //首先先判断registerVo非空
        if (registerVo == null) {
            //如果信息是空则发生异常
            //这里的异常逻辑是我们编写的项目发生的不是系统异常
            //所以这里以及以后的方法中都需要抛出自定义的异常
            throw ServiceException.unprocesabelEntity("表单数据为空");
            //也可以写成 throw new ServiceException();
        }
        //根据输入的邀请码，查询班级验证邀请码有效性
        QueryWrapper<Classroom> queryWrapper = new QueryWrapper<>();
        //eq就是equals的意思，意思就是左边 "invite_code"要等于registerVo.getInviteCode()
        //invite_code 是数据库的列名
        queryWrapper.eq("invite_code", registerVo.getInviteCode());
        Classroom classroom = classroomMapper.selectOne(queryWrapper);
        //SpringBoot默认不加@Slf4j 也会包含log 但是功能不全，所以我们加上以避免下面报错
        log.debug("邀请码对应的班级为:{}", classroom);
        if (classroom == null) {
            throw ServiceException.unprocesabelEntity("邀请码错误!");
        }
        //验证数据库中是否已经注册过输入的用户名(手机号)
        //用户名查询用户对象
        User u = userMapper.findUserByUsername(registerVo.getPhone());
        if (u != null) {
            //用户已存在
            throw ServiceException.unprocesabelEntity("手机号已经注册!");
        }
        //User对象的赋值(将表单中的值和一些默认值确定后)  手机号就是用户名
        User user = new User();
        user.setUsername(registerVo.getPhone());
        user.setPhone(registerVo.getPhone());
        user.setNickname(registerVo.getNickname());
        //用户输入的是明文密码,数据库保存的是带算法ID的加密结果!
        user.setPassword("{bcrypt}" +
                passwordEncoder.encode(registerVo.getPassword()));
        user.setClassroomId(classroom.getId());
        user.setCreatetime(LocalDateTime.now());
        // `enabled` tinyint(4) NOT NULL COMMENT '账号是否可用，0-》否，1-》是'
        user.setEnabled(1);
        // `locked` tinyint(4) NOT NULL COMMENT '账号是否被锁住，0-》否，1-》是'
        user.setLocked(0);
        //执行User新增
        int num = userMapper.insert(user);
        //验证新增结果
        if (num != 1) {
            throw new ServiceException("服务器忙,稍后再试");
        }
        //将新增的用户赋予学生身份(新增User_role的关系)
        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        // @TableField("role_id")
        // private Integer roleId;  学生的id就是2
        userRole.setRoleId(2);
        num = userRoleMapper.insert(userRole);
        //验证关系表新增结果
        if (num != 1) {
            throw new ServiceException("服务器忙,稍后再试");
        }
    }

    /**
     * 获取当前用户的信息，获得登录用户的username
     * 主要是为了后续index页面中用户查询自己的question
     *
     * @return
     */
    @Override
    public String currentUsername() {
        //利用Spring-Security框架获得当前登录用户信息
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        //判断当前用户有没有登录，如果没有抛出异常
        //只要不是匿名用户就行 ; AnonymousAuthenticationToken是匿名用户令牌
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //上面的代码是判断当前用户的抽象权限类型是不是匿名用户
            //如果不是匿名用户，就是登录的用户，只有登录的用户才能返回用户名
            String username = authentication.getName();
            return username;
        }
        //没运行上面的if证明用户没有登录，抛出异常即可
        throw ServiceException.notFound("没有登录");
    }

    private final List<User> masters = new CopyOnWriteArrayList<>();
    private final Map<String, User> masterMap = new ConcurrentHashMap<>();
    private final Timer timer = new Timer();

    //初始化块:在构造方法运行前开始运行
    {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                synchronized (masters) {
                    masters.clear();
                    masterMap.clear();
                }
            }
        }, 1000 * 60 * 30, 1000 * 60 * 30);
    }

    /**
     * 查询所有老师 user表中 type = 1的是老师
     * @return List<User>
     */
    @Override
    public List<User> getMasters() {
        if (masters.isEmpty()) {
            synchronized (masters) {
                if (masters.isEmpty()) {
                    QueryWrapper<User> query = new QueryWrapper<>();
                    query.eq("type", 1);
                    //将所有老师缓存masters集合中
                    masters.addAll(userMapper.selectList(query));
                    for (User u : masters) {
                        masterMap.put(u.getNickname(), u);
                    }
                    //脱敏:将敏感信息从数组(集合\map)中移除
                    for (User u : masters) {
                        u.setPassword("");
                    }
                }
            }
        }
        return masters;
    }

}
