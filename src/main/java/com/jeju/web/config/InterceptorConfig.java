package com.jeju.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public UserAuthorityInterceptor userAuthorityInterceptor() {
        return new UserAuthorityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userAuthorityInterceptor())
                .excludePathPatterns("/css/**", "/images/**", "/script/**", "/util/**").addPathPatterns("/jeju/**");
    }
}
