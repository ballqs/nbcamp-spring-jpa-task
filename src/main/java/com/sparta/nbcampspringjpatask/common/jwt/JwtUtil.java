package com.sparta.nbcampspringjpatask.common.jwt;

import com.sparta.nbcampspringjpatask.user.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    // 사용자 권한 값의 KEY (내가 임의로 다른 값으로 바꿀수 있다.)
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간(ms 단위)
    private final long TOKEN_EXPIRATION_TIME = 60 * 60 * 1000L; // 60분

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // 로그 설정
    public static final Logger logger = LoggerFactory.getLogger("JWT 관련 로그");

    @PostConstruct
    public void init() {
        // decode를 하면 byte[] 로 반환댐
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public String createToken(String value , UserRoleEnum role) {
        Date date = new Date();

        // setSubject 토큰의 용도를 명시한다. (디코딩했을때 값이 제대로 나오면 인가난것)
        // ㄴunique한 값이 일반적으로 들어감
        // claim는 헤더에 보내는 행위 자체를 객체를 만드는 자체를 payload에 담는다.
        // setExpiration 만료 시간을 설정한다.
        // setIssuedAt 생성시간을 설정한다.
        // signWith는 어떤 알고리즘과 key값으로 payload를 SH256방식으로 암호화한다.
        // compact 토큰을 생성한다.
        return BEARER_PREFIX +
                Jwts.builder()
                    .setSubject(value) // 사용자 식별자값(ID)
                    .claim(AUTHORIZATION_KEY, role) // 사용자 권한 Map<Key , Value>
                    .setExpiration(new Date(date.getTime() + TOKEN_EXPIRATION_TIME)) // 만료 시간
                    .setIssuedAt(date) // 발급일
                    .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                    .compact();
    }

    // JWT 토큰 substring
    public String substringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            // "Bearer "이 공백을 포함하여 7자를 자른다.
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    // HttpServletRequest 에서 header : JWT 가져오기
    public String getTokenFromRequest(HttpServletRequest req) {
        return req.getHeader(HttpHeaders.AUTHORIZATION);
    }
}