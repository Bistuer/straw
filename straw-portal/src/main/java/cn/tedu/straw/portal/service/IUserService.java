package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

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
     * 这个方法用于查询获得用户详情对象的业务
     * UserDetails是SpringSecurity验证用户必要的信息
     * String username是SpringSecurity接收的用户输入的用户名
     * 密码返回对比封装到Spring-Security中
     *
     * @param username
     * @return
     */
    public abstract UserDetails getUserDetails(String username);

    /**
     * 用户注册的方法(现在是针对学生注册)
     * @param registerVo
     * @return void
     */
    void registerStudent(RegisterVo registerVo);

}
