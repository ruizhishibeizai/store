package com.cy.store.config;

import com.cy.store.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: ZSZ
 * @create: 2022-10-17 21:11
 **/
/**处理拦截器的注册**/
@Configuration //加载当前的拦截器并注册到容器中
public class LoginInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //配置拦截器
        HandlerInterceptor interceptor = new LoginInterceptor();
        /** 配置白名单 存放在一个list中**/
        List<String> patterns = new ArrayList<String>();
        patterns.add("/bootstrap3/**");
        patterns.add("/css/**");
        patterns.add("/images/**");
        patterns.add("/js/**");
        patterns.add("/web/register.html");
        patterns.add("/web/login.html");
        patterns.add("/web/index.html");
        patterns.add("/web/product.html");
        patterns.add("/users/reg");
        patterns.add("/users/login");
        patterns.add("/districts/**");

        //拦截器的注册
        registry.addInterceptor(interceptor)
                .addPathPatterns("/**") //拦截所有
                .excludePathPatterns(patterns); //除了...(list)

    }
}
