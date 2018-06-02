package com.lgp.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 类说明
 *
 * @author lgp
 * @create 2018-04-26 21:20
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userRoleService")
    UserDetailsService userRoleService;
    /**
     * 对/和/login不拦截，登陆页面为/login
     * 成功页面为/chat
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/","/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/cors")
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and().csrf().disable();//在Java配置中使用Spring Security，默认情况下启用CSRF保护。在这种情况下，如果您使用POST方法向REST端点发出Ajax请求，您将得到一个csrf标记缺失错误。
    }

    /**
     * 分配两个用户
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userRoleService).passwordEncoder(new MyPasswordEncoder());
//                .withUser("LGP").password("123123").roles("USER")
//                .and().passwordEncoder(new MyPasswordEncoder())
//                .withUser("LI").password("111111").roles("USER");//USER必须是大写
    }

    /**
     * 静态资源不拦截
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/bootstrap/**","/druid/**","/api/**","/user/**","/favicon.ico","/async/**","/mq/**", "/file/**", "/upload/**"
                , "/v2/api-docs", "/swagger-resources/configuration/ui", "/swagger-resources/**", "/swagger-resources/configuration/security", "/swagger-ui.html", "/webjars/springfox-swagger-ui/*","/webjars/**");
    }



}
