package com.example.filter.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Slf4j
//@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // 진입 전
        log.info(">>>> 진입");

        var req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) response);

        chain.doFilter(req, res); // controller에서 받는 request와 response는 한 번 변환된 req와 res가 전달됨.

        var reqJson = new String(req.getContentAsByteArray());
        var resJson = new String(res.getContentAsByteArray());

        log.info("req : {}", reqJson); // 클라이언트에서 받은 날 것의 데이터를 로그에 출력해보기.
        log.info("res : {}", resJson);


        // 진입 후
        log.info("<<<< 리턴");

        res.copyBodyToResponse(); // 캐싱했던 데이터를 다시 담아준다.
    }
}
