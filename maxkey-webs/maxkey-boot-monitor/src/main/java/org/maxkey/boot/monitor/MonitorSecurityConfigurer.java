package org.maxkey.boot.monitor;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class MonitorSecurityConfigurer extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 登录成功处理类
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl("/");

        http.authorizeRequests()
                //无需认证
                .antMatchers(
                            "/login",           //登录页面
                            "/assets/**",       //静态文件允许访问
                            "/actuator/**",     //springboot-admin监控的请求
                            "/instances/**"     //springboot-admin监控的实例信息请求
                ).permitAll()
                //其他所有请求需要登录
                .anyRequest().authenticated()
                //登录
                .and().formLogin().loginPage("/login").successHandler(successHandler)
                //登出
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login")
                .and().httpBasic()
                .and().csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers(
                        "/instances",
                        "/actuator/**"
                );

    }
}
