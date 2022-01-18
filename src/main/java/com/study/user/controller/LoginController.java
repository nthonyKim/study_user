package com.study.user.controller;

import com.study.user.dto.ResponseDTO;
import com.study.user.dto.UserDTO;
import com.study.user.service.LoginService;
import com.study.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="04. Login", description = "로그인")
@RequiredArgsConstructor
@RestController
public class LoginController {
    private final LoginService loginService;

    @Operation(summary  = "로그인")
    @PostMapping("/login")
    public ResponseDTO login(@RequestParam String userId, @RequestParam String userPw){
        return loginService.login(userId, userPw);
    }

}
