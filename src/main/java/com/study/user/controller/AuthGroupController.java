package com.study.user.controller;

import com.study.user.dto.AuthGroupDTO;
import com.study.user.dto.ResponseDTO;
import com.study.user.service.AuthGroupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="02. AuthGroup", description = "권한 그룹")
@RequiredArgsConstructor
@RequestMapping("/authGroup")
@RestController
public class AuthGroupController {
    private final AuthGroupService authGroupService;

    @Operation(summary  = "권한 리스트 조회")
    @GetMapping("/list")
    public List<AuthGroupDTO> findAll(){
        return authGroupService.findAll();
    }

    @Operation(summary  = "권한 단일 조회")
    @GetMapping("/{authGroupId}")
    public AuthGroupDTO findById(@PathVariable String authGroupId){
        return authGroupService.findById(authGroupId);
    }

    @Operation(summary  = "권한 등록")
    @PostMapping
    public ResponseDTO createAuthGroup(@RequestBody AuthGroupDTO authGroupDTO){
        return authGroupService.createAuthGroup(authGroupDTO);
    }

    @Operation(summary  = "권한 수정")
    @PutMapping
    public ResponseDTO updateUser(@RequestBody AuthGroupDTO authGroupDTO){
        return authGroupService.updateAuthGroup(authGroupDTO);
    }

    @Operation(summary  = "권한 삭제")
    @DeleteMapping
    public ResponseDTO deleteUser(@RequestBody AuthGroupDTO authGroupDTO){
        return authGroupService.deleteAuthGroup(authGroupDTO);
    }
}
