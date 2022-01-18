package com.study.user.security;

import com.study.user.entity.AuthGroupDetail;
import com.study.user.exception.InvalidTokenException;
import com.study.user.redis.Redis;
import com.study.user.util.CollectionUtil;
import com.study.user.util.RequestUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final Redis redis;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, InvalidTokenException {
        String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);

        try {
            if(jwtTokenProvider.validateToken(TokenType.ACCESS, token)){
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                Map<String, String> menu = redis.getEntries("menu");
                String requestURI = ((HttpServletRequest) request).getRequestURI();
                if(CollectionUtil.isNotEmpty(menu.get(requestURI))){
                    String authDetail = convertMethodToCRUD(RequestUtil.getMethod());
                    System.out.println(RequestUtil.getUri());
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

    private String convertMethodToCRUD(String method){
        switch (method){
            case "POST" : return AuthGroupDetail.TYPE_CREATE;
            case "GET" : return AuthGroupDetail.TYPE_READ;
            case "PUT" : return AuthGroupDetail.TYPE_UPDATE;
            case "DELETE" : return AuthGroupDetail.TYPE_DELETE;
        }

        return null;
    }
}
