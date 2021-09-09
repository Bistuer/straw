package cn.tedu.straw.sys.service;


import cn.tedu.straw.commons.model.Permission;
import cn.tedu.straw.commons.model.Role;
import cn.tedu.straw.commons.model.User;
import cn.tedu.straw.sys.vo.RegisterVo;
import cn.tedu.straw.sys.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * <p>
 * 实现类是UserServiceImpl
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
public interface IUserService extends IService<User> {

    /**
     * 用户注册的方法(现在是针对学生注册)
     *
     * @param registerVo
     */
    void registerStudent(RegisterVo registerVo);

    /**
     * 查询所有老师用户的方法
     *
     * @return
     */
    List<User> getMasters();

    /**
     * 查询所有老师用户的Map方法
     *
     * @return
     */
    Map<String, User> getMasterMap();

    /**
     * 查询当前登录用户信息面板的方法
     * 这个方法的参数有变化!!!!注意!!!!
     *
     * @param username
     * @return
     */
    UserVo currentUserVo(String username);

    /**
     * 根据用户名获得用户信息
     * 这个方法的参数有变化
     *
     * @param username
     * @return
     */
    User getUserByUsername(String username);

    /**
     * 根据用户id获得用户权限
     *
     * @param userId
     * @return
     */
    List<Permission> getUserPermissions(Integer userId);

    /**
     * 根据用户id获得角色
     *
     * @param userId
     * @return
     */
    List<Role> getUserRoles(Integer userId);

}
