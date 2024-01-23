package com.example.filter.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
public class OpenApiInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("pre handle");
        // true: controller에게 전달, false: controller에게 전달하지 않음.
        var handlerMethod = (HandlerMethod) handler; // HandlerMethod로 형변환

        var methodLevel = handlerMethod.getMethodAnnotation(OpenApi.class); // OpenApi 어노테이션을 가진 메소드만 통과
        if (methodLevel != null) {
            log.info("method level 통과");
            return true;
        }

        var classLevel = handlerMethod.getBeanType().getAnnotation(OpenApi.class); // OpenApi 어노테이션을 가진 클래스만 통과
        if (classLevel != null) {
            log.info("class level 통과");
            return true;
        }

        log.info("oepn api가 아닙니다: {}", request.getRequestURI());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        log.info("post handle");

//        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("after completion");
//        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
