package com.example.filter.config;

import com.example.filter.interceptor.OpenApiInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private OpenApiInterceptor openApiInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(openApiInterceptor) // add한 인터셉터 순서대로 인터셉터가 실행 됨. (또는 order()를 통해 우선순위를 부여할 수 있음)
                .addPathPatterns("/**"); // interceptor를 매핑할 주소 (여기선 모든 경로에 매핑)
    }
}
