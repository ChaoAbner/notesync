package com.cvte.notesync.config;

import com.cvte.notesync.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 添加拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/user/**")
                .addPathPatterns("/note/**")
                .addPathPatterns("/sync/**");
//                .addPathPatterns("image/note/**");
    }
}
