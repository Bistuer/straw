package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Permission;
import cn.tedu.straw.portal.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2021-04-13
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    // hello 1

    //根据用户输入的用户名查询用户信息的方法
    @Select("select * from user where username=#{username}")
    User findUserByUsername(String username);

    //查询指定id的用户的所有权限
    @Select("SELECT p.id,p.name" +
            " FROM user u" +
            " LEFT JOIN user_role ur ON u.id=ur.user_id" +
            " LEFT JOIN role r ON r.id=ur.role_id" +
            " LEFT JOIN role_permission rp ON r.id=rp.role_id" +
            " LEFT JOIN permission p ON p.id=rp.permission_id" +
            " WHERE u.id=#{id}")
    List<Permission> findUserPermissionsById(Integer id);

}