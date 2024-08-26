package com.sparta.nbcampspringjpatask.filter;


import com.sparta.nbcampspringjpatask.jwt.JwtUtil;
import com.sparta.nbcampspringjpatask.jwt.JwtValidationResult;
import io.jsonwebtoken.Claims;
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
        String method = request.getMethod();

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
                    log.error("토큰에 문제가 있습니다.");
                    handleException(response, validationResult.getMessage(), validationResult.getStatusCode());
                } else {
                    log.info("토큰 검증 성공");
                    Claims info = jwtUtil.getUserInfoFromToken(token);
                    String authority = (String) info.get(jwtUtil.AUTHORIZATION_KEY);

                    if (method.equals("PATCH") || method.equals("DELETE")) {
                        if (!authority.equals("ADMIN")) {
                            log.error("권한이 없습니다.");
                            handleException(response, "권한이 없습니다.", HttpServletResponse.SC_FORBIDDEN);
                            return;
                        }
                    }

                    // 해당 필터는 검증 완료
                    log.info("로그인 성공");
                    filterChain.doFilter(request, response);
                }
            } else {
                log.error("토큰이 없습니다.");
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
