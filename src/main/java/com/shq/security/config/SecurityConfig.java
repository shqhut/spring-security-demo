package com.shq.security.config;

import com.shq.security.filters.TokenAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthFilter tokenAuthFilter;

    /**
     * 实际项目中我们不会把密码明文存储在数据库中。
     * 默认使用的PasswordEncoder要求数据库中的密码格式为：{id}password 。
     * 它会根据id去判断密码的加密方式。但是我们一般不会采用这种方式。所以就需要替换PasswordEncoder。
     * 我们一般使用SpringSecurity为我们提供的BCryptPasswordEncoder。
     * 我们只需要使用把BCryptPasswordEncoder对象注入Spring容器中，SpringSecurity就会使用该PasswordEncoder来进行密码校验。
     * @return
     */
    @Bean
    public PasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 自定义认证相关配置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//关闭csrf
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //不通过Session获取SecurityContext
                .and()
                .authorizeRequests()
                .antMatchers("/login").anonymous()//对于登录接口，允许匿名访问
                .anyRequest().authenticated();//除上面外的所有请求全部需要鉴权认证
        //设置token认证过滤器先执行
        http.addFilterBefore(tokenAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }




}
