package com.zh.module.interceptor;

import com.zh.module.interceptor.AuthenticationInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: R.D.Y.LMain
 * @description: 拦截器
 * @author: zhaohe
 * @create: 2019-12-19 18:48
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] excludes = new String[]{"/js/**", "/css/**", "/static/**"};
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**").excludePathPatterns(excludes);
    }
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}