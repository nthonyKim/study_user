package com.study.user.service;

import com.study.user.dto.AuthGroupDTO;
import com.study.user.dto.ResponseDTO;
import com.study.user.dto.UserDTO;
import com.study.user.entity.AuthGroup;
import com.study.user.entity.User;
import com.study.user.mapper.AuthGroupMapper;
import com.study.user.mapper.UserMapper;
import com.study.user.repository.AuthGroupRepository;
import com.study.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthGroupService {
    private final AuthGroupRepository authGroupRepository;

    private final AuthGroupMapper authGroupMapper;
    private static final String MESSAGE_KEY_HEAD = "user.authgroup.";

    public List<AuthGroupDTO> findAll(){
        List<AuthGroup> authGroups = authGroupRepository.findAll();
        return authGroups.stream().map(authGroupMapper::toDTO).collect(Collectors.toList());
    }

    public AuthGroupDTO findById(final String authGroupId) {
        return authGroupMapper.toDTO(authGroupRepository.findById(authGroupId).orElseThrow());
    }

    public ResponseDTO createAuthGroup(AuthGroupDTO authGroupDTO) {
        Optional<AuthGroup> optionalAuthGroup = authGroupRepository.findById(authGroupDTO.getAuthGroupId());
        if(optionalAuthGroup.isPresent()){
            return ResponseDTO.fail(MESSAGE_KEY_HEAD + "id_exist.error");
        } else {
            authGroupRepository.save(authGroupMapper.toEntity(authGroupDTO));
            return ResponseDTO.success(MESSAGE_KEY_HEAD + "create.success");
        }
    }

    public ResponseDTO updateAuthGroup(AuthGroupDTO authGroupDTO) {
        AuthGroup authGroup = authGroupMapper.toEntity(authGroupDTO);
        authGroupRepository.save(authGroup);
        return ResponseDTO.success(MESSAGE_KEY_HEAD + "update.success");
    }

    public ResponseDTO deleteAuthGroup(AuthGroupDTO authGroupDTO) {
        Optional<AuthGroup> optionalAuthGroup = authGroupRepository.findById(authGroupDTO.getAuthGroupId());
        if(optionalAuthGroup.isPresent()){
            optionalAuthGroup.get().softDelete();
            return ResponseDTO.success(MESSAGE_KEY_HEAD + "delete.success");
        } else {
            return ResponseDTO.fail(MESSAGE_KEY_HEAD + "no_exist.error");
        }
    }
}
