package com.study.user.security;

import com.study.user.exception.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Component
@NoArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.secret-key")
    private String secretKey;
    @Value("${jwt.access.valid")
    private String ACCESS_VALID;
    @Value("${jwt.refresh.valid")
    private String REFRESH_VALID;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String tokenType, String userId, String authGroupId) {
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", authGroupId);
        claims.put("type", isAccess(tokenType) ? TokenType.ACCESS : TokenType.REFRESH );

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Timestamp.valueOf(LocalDateTime.now())) // 토큰 발행 일자
                .setExpiration(new Date(new Date().getTime() + (isAccess(tokenType) ? ACCESS_VALID : REFRESH_VALID))) // 만료 기간
                .signWith(SignatureAlgorithm.HS512, secretKey) // 암호화 알고리즘, secret 값
                .compact(); // Token 생성
    }

    public String getUserId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String getRole(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role", String.class);
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("X-AUTH-TOKEN");
    }

    public boolean validateToken(String tokenType, String token) throws AuthenticationException {
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        String inTokenType = claims.getBody().get("type", String.class);
        if(!tokenType.equals(inTokenType)){
            throw new InvalidTokenException("user.token.type.error");
        }
        return !claims.getBody().getExpiration().before(new Date());
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = new SecurityUser(getUserId(token), getRole(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private boolean isAccess(String tokenType){
        return tokenType.equals(TokenType.ACCESS);
    }
}
