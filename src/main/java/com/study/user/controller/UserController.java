package com.study.user.controller;

import com.study.user.dto.ResponseDTO;
import com.study.user.dto.UserDTO;
import com.study.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="01. User", description = "사용자")
@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
public class UserController {
    private final UserService userService;

    @Operation(summary  = "사용자 리스트 조회")
    @GetMapping("/list")
    public List<UserDTO> findAll(){
        return userService.findAll();
    }

    @Operation(summary  = "사용자 단일 조회")
    @GetMapping("/{userId}")
    public UserDTO findById(@PathVariable String userId){
        return userService.findById(userId);
    }

    @Operation(summary  = "사용자 등록")
    @PostMapping
    public ResponseDTO registerUser(@RequestBody UserDTO userDTO){
        return userService.registerUser(userDTO);
    }

    @Operation(summary  = "사용자 수정")
    @PutMapping
    public ResponseDTO updateUser(@RequestBody UserDTO userDTO){
        return userService.updateUser(userDTO);
    }

    @Operation(summary  = "사용자 삭제")
    @DeleteMapping
    public ResponseDTO deleteUser(@RequestBody UserDTO userDTO){
        return userService.deleteUser(userDTO);
    }

    @Operation(summary  = "비밀번호 변경")
    @PutMapping("/password")
    public ResponseDTO changePassword(@RequestBody UserDTO userDTO){
        return userService.changePassword(userDTO);
    }

}
