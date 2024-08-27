package com.sparta.nbcampspringjpatask.common.filter;


import com.sparta.nbcampspringjpatask.common.jwt.JwtUtil;
import com.sparta.nbcampspringjpatask.user.entity.UserRoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

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

        if (Strings.isNotBlank(url) && validateNotPublicUrl(url)) {
            // 나머지 API 요청은 인증 처리 진행
            // 토큰 확인
            String tokenValue = jwtUtil.getTokenFromRequest(request);

            if (Strings.isNotBlank(tokenValue)) { // 토큰이 존재하면 검증 시작
                // 토큰 검증
                String token = jwtUtil.substringToken(tokenValue);
                if (!jwtUtil.validateToken(token)) {
                    log.error("인증 실패");
                    // Servlet 영역에서 처리하는 방법은 아래와 같음
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED , "인증에 실패했습니다.");
                    // throw 처리하는 경우는 서버의 영역
                    // throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , "인증에 실패했습니다.");
                } else {
                    log.info("토큰 검증 성공");
                    Claims info = jwtUtil.getUserInfoFromToken(token);
                    String authority = (String) info.get(jwtUtil.AUTHORIZATION_KEY);

                    if (method.equals(HttpMethod.PATCH.name()) || method.equals(HttpMethod.DELETE.name())) {
                        if (!authority.equals(UserRoleEnum.ADMIN.name())) {
                            log.error("권한이 없습니다.");
                            // 인증에 성공했지만 인가에 실패한 경우
                            response.sendError(HttpServletResponse.SC_FORBIDDEN , "권한이 없습니다.");
                        }
                    }
                }
            } else {
                log.error("토큰이 없습니다.");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST , "토큰이 없습니다.");
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean validateNotPublicUrl(String url) {
        return !(url.equals("/api/v1/users/signup") || url.equals("/api/v1/users/login"));
    }
}
