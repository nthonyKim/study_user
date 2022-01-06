package com.study.user.service;

import com.study.user.dto.ResponseDTO;
import com.study.user.dto.UserDTO;
import com.study.user.entity.User;
import com.study.user.exception.NotFoundException;
import com.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LoginService {
    private final UserRepository userRepository;

    public ResponseDTO login(UserDTO userDTO) {
        BCryptPasswordEncoder pwEncoder = new BCryptPasswordEncoder();
        User user = userRepository.findById(userDTO.getUserId())
                .orElseThrow(() -> new NotFoundException("user.login.no_user.error"));

        if(user.getUserPw().equals(pwEncoder.encode(userDTO.getUserPw()))) {
            if(!user.getUseFlag()) { ResponseDTO.fail("user.login.use_false.error"); }
            if(user.getWrongPwCnt() >= 5) { ResponseDTO.fail("user.login.wrong_cnt.error"); }

            // token 생성 등의 기능을 추가할 예정

            return ResponseDTO.success();
        } else {
            user.updateWrongPwCnt();
            return ResponseDTO.fail("user.login.wrong_pw.error");
        }
    }
}
