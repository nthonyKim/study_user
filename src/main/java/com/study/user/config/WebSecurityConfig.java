package com.study.user.config;

import com.study.user.redis.RedisTemplate;
import com.study.user.security.JwtAuthenticationFilter;
import com.study.user.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

    @Override
    public void configure(WebSecurity web)  {
        // ignore config
        web.ignoring().antMatchers("/**");
//        web.ignoring().antMatchers("/v3/api-docs", "/swagger", "/swagger-ui/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // cors config
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(CorsConfiguration.ALL);
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedHeader(CorsConfiguration.ALL);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        http.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), BasicAuthenticationFilter.class);
    }
}