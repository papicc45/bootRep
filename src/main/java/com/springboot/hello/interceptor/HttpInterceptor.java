package com.springboot.hello.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(HttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info("[preHandle] preHandle is performed");
        LOGGER.info("[preHandle] request : {}", request);
        LOGGER.info("[preHandle] request path info : {}", request.getPathInfo());
        LOGGER.info("[preHandle] request header names : {}", request.getHeaderNames());
        LOGGER.info("[preHandle] request URL  : {}", request.getRequestURL());
        LOGGER.info("[preHandle] request URI  : {}", request.getRequestURI());
        LOGGER.info("[preHandle] request Session Id  : {}", request.getRequestedSessionId());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.info("[postHandle] postHandle is performed");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LOGGER.info("[afterCompletion] afterCompletion is performed");
    }
}
