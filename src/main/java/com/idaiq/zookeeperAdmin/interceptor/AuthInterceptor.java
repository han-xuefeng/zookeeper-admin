package com.idaiq.zookeeperAdmin.interceptor;

import com.github.benmanes.caffeine.cache.Cache;
import com.idaiq.zookeeperAdmin.entity.User;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.PrintWriter;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Resource
    private Cache<String, User> tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String tokenHeader = request.getHeader("X-Auth-Token");
        if (!StringUtils.hasText(tokenHeader)) {
            tokenHeader = request.getParameter("X-Auth-Token");
        }

        if (StringUtils.hasText(tokenHeader)) {
            User user = tokenManager.getIfPresent(tokenHeader);
            if (user != null) {
                return true;
            }
        }
        try (PrintWriter writer = response.getWriter()) {
            response.addHeader("Content-Type", "application/json");
            response.setStatus(401);
            writer.write("{\"code\":\"401\",\"message\":\"Unauthorized\"}");
        }

        return false;
    }
}
