package com.study.user.security;

import com.study.user.exception.InvalidTokenException;
import com.study.user.redis.RedisTemplate;
import com.study.user.util.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.naming.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;


@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, InvalidTokenException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        try {
            if(jwtTokenProvider.validateToken("ACCESS", token)){
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                Map<String, String> menu = redisTemplate.getEntries("menu");
                if(!CollectionUtil.isEmpty(menu.get(((HttpServletRequest) request).getRequestURI()))){
                    /**
                     * method를 object로 변환한 부분과 (예: GET -> READ)
                     * 현재 사용자의 토큰 정보의 role을 가져와 허용 여부를 판별한 뒤 path check
                     */
                }

            } else {
                // access token 기한 만료
                throw new InvalidTokenException("user.token.valid.error");
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        chain.doFilter(request, response);
    }
}
