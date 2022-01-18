package com.study.user.service;

import com.study.user.dto.ResponseDTO;
import com.study.user.dto.ToKenDTO;
import com.study.user.dto.UserDTO;
import com.study.user.entity.User;
import com.study.user.exception.NotFoundException;
import com.study.user.redis.Redis;
import com.study.user.repository.UserRepository;
import com.study.user.security.JwtTokenProvider;
import com.study.user.security.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final Redis redis;

    public ResponseDTO login(final String userId, final String userPw) {
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("user.login.no_user.error"));

        if(pwEncoder.matches(userPw, user.getUserPw())) {
            if(!user.getUseFlag()) { ResponseDTO.fail("user.login.use_false.error"); }
            if(user.getWrongPwCnt() >= 5) { ResponseDTO.fail("user.login.wrong_cnt.error"); }

            String accessToken = jwtTokenProvider.createToken(TokenType.ACCESS, user.getUserId(), user.getAuthGroup().getAuthGroupId());
            String refreshToken = jwtTokenProvider.createToken(TokenType.REFRESH, user.getUserId(), user.getAuthGroup().getAuthGroupId());

            redis.putHash("refresh", userId, refreshToken);
            return ResponseDTO.success("user.login.success", ToKenDTO.builder()
                                                            .accessToken(accessToken)
                                                            .refreshToken(refreshToken)
                                                            .build());
        } else {
            user.updateWrongPwCnt();
            return ResponseDTO.fail("user.login.wrong_pw.error");
        }
    }
}
