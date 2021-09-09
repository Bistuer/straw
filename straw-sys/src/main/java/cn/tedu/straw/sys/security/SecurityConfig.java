package cn.tedu.straw.sys.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author fanzhen
 * @EnableGlobalMethodSecurity(prePostEnabled = true) 表示通知Spring-Security开启权限管理功能，就是像拦截器那样的功能
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 控制授权代码在这里!!!!!
     * <p>
     * 方法说明:
     * <p>
     * 1. csrf().disable():关闭防跨域攻击功能,不关闭容易发生错误
     * 2. authorizeRequests所有权限
     * 3. anyRequest()所有请求
     * 4. permitAll()都允许
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .anyRequest().permitAll();
    }

}







