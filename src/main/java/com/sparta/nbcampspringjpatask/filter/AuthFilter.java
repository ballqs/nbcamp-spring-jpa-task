package com.sparta.nbcampspringjpatask.filter;


import com.sparta.nbcampspringjpatask.jwt.JwtUtil;
import com.sparta.nbcampspringjpatask.jwt.JwtValidationResult;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j(topic = "AuthFilter")
@RequiredArgsConstructor
@Component
@Order(1)
public class AuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String url = request.getRequestURI();
        if (StringUtils.hasText(url) &&
                (url.equals("/api/v1/users/signup") || url.equals("/api/v1/users/login"))) {
            filterChain.doFilter(request, response);
        } else {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(request);

            if (StringUtils.hasText(tokenValue)) { // 토큰이 존재하면 검증 시작
                // JWT 토큰 substring
                String token = jwtUtil.substringToken(tokenValue);

                // 토큰 검증
                JwtValidationResult validationResult = jwtUtil.validateToken(token);
                if (Objects.nonNull(validationResult)) {
                    handleException(response, validationResult.getMessage(), validationResult.getStatusCode());
                } else {
                    filterChain.doFilter(request, response); // 다음 Filter 로 이동
                }
            } else {
                handleException(response, "토큰이 없습니다.", HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    private void handleException(HttpServletResponse response, String message, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"" + message + "\", \"status\": " + statusCode + "}");
        log.error("AuthFilter Error: {}", message);
    }
}
