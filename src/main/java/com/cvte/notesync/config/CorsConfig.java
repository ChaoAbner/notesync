package com.cvte.notesync.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 解决跨域问题
 */

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // 允许任何域名
                .allowedOrigins("*")
                // 允许证书
                .allowCredentials(true)
                // 任何方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 允许任何
                .allowedHeaders("*");
    }

//    @Bean
//    public CorsFilter corsFilter() {
//
//        CorsConfiguration config = new CorsConfiguration();
//        //开放哪些ip、端口、域名的访问权限，星号表示开放所有域
//        config.addAllowedOrigin("**");
//        //是否允许发送Cookie信息
//        config.setAllowCredentials(true);
//        //开放哪些Http方法，允许跨域访问
//        config.addAllowedMethod(HttpMethod.DELETE);
//        config.addAllowedMethod(HttpMethod.GET);
//        config.addAllowedMethod(HttpMethod.POST);
//        config.addAllowedMethod(HttpMethod.PUT);
//        //允许HTTP请求中的携带哪些Header信息
//        config.addAllowedHeader("**");
//        //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//        config.addExposedHeader("**");
//
//        //添加映射路径，“/**”表示对所有的路径实行全局跨域访问权限的设置
//        UrlBasedCorsConfigurationSource configSource = new UrlBasedCorsConfigurationSource();
//        configSource.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(configSource);
//    }


}
