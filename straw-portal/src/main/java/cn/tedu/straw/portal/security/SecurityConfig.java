package cn.tedu.straw.portal.security;

import cn.tedu.straw.portal.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author fanzhen
 * @Configuration表示当前类是配置类，可能向Spring容器中注入对象
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 表示通知Spring-Security开启权限管理功能
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //密码中设定了算法ID 下面的注入就省略了

    //注入一个加密对象
    @Bean
    public PasswordEncoder passwordEncoder() {
        //这个加密对象使用BCrypt加密内容
        return new BCryptPasswordEncoder();
    }


    @Autowired(required = false)
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);
        /**
         * //方法中配置哪个用户可以有什么样的权限
         auth.inMemoryAuthentication().withUser("Tom")
         .password("{bcrypt}$2a$10$I.xH0Ih7R7sU.gm7wpOov.eCWUF4oa/o0Vftci61n2a/VuIdJmyRu")
         //方法"/tag/get"权限
         .authorities("/tag/get");
         *
         */

    }


    /**
     * 控制授权代码在这里!!!!!
     * <p>
     * 方法说明:
     * <p>
     * 1. csrf().disable():关闭防跨域攻击功能,不关闭容易发生错误
     * 2. loginPage:指定登录页面路径
     * 3. loginProcessingUrl:指定表单提交的路径  表单里面的action
     * 4. failureUrl:指定登录失败时的路径
     * 5. defaultSuccessUrl:指定登录成功时的路径
     * 6. logout():表示开始配置登出时的内容
     * 7. logoutUrl:指定出的路径(当页面有这个请求时,Spring-Security去执行用户登出操作)
     * 8. logoutSuccessUrl:指定登出成功之后显示的页面
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()//对当前全部请求进行授权
                .antMatchers(
                        "/img/*",
                        "/js/*",
                        "/css/*",
                        "/bower_components/**",
                        "/login.html",
                        //对register.html页面进行放行
                        "/register.html",
                        //放行注册业务!!!!!!
                        "/register"
                )//设置路径
                .permitAll()//允许全部请求访问上面定义的路径
                //其它路径需要全部进行表单登录验证 !!!
                .anyRequest().authenticated().and()
                .formLogin()
                //指定登录页面
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .failureUrl("/login.html?error")
                .defaultSuccessUrl("/index.html")
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html?logout");
    }

}

