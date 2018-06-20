package com.newcoder.toutiao.configuration;

import com.newcoder.toutiao.intercepter.LoginRequiredInterceptor;
import com.newcoder.toutiao.intercepter.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * created by zsj in 20:11 2018/5/26
 * description:
 **/
@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter{

    @Autowired
    PassportInterceptor passportInterceptor;

    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //全局拦截器，处理所有的页面
        registry.addInterceptor(passportInterceptor);
        //只拦截某些页面。比如这个是拦截/setting页面。
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/setting*");
        super.addInterceptors(registry);
    }
}
