package com.study.user.service;

import com.study.user.dto.AuthGroupDTO;
import com.study.user.dto.AuthGroupDetailDTO;
import com.study.user.dto.ResponseDTO;
import com.study.user.entity.AuthGroup;
import com.study.user.entity.AuthGroupDetail;
import com.study.user.entity.Menu;
import com.study.user.exception.NotFoundException;
import com.study.user.mapper.AuthGroupDetailMapper;
import com.study.user.mapper.AuthGroupMapper;
import com.study.user.repository.AuthGroupDetailRepository;
import com.study.user.repository.AuthGroupRepository;
import com.study.user.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthGroupService {
    private final AuthGroupRepository authGroupRepository;
    private final MenuRepository menuRepository;

    private final AuthGroupMapper authGroupMapper;
    private static final String MESSAGE_KEY_HEAD = "user.authgroup.";

    public List<AuthGroupDTO> findAll(){
        List<AuthGroup> authGroups = authGroupRepository.findAll();
        return authGroups.stream().map(authGroupMapper::toDTO).collect(Collectors.toList());
    }

    public AuthGroupDTO findById(final String authGroupId) {
        AuthGroup authGroup = authGroupRepository.findById(authGroupId)
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_authgroup.error"));
        return authGroupMapper.toDTO(authGroup);
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
        authGroupRepository.findById(authGroupDTO.getAuthGroupId())
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_authgroup.error"));

        AuthGroup authGroup = authGroupMapper.toEntity(authGroupDTO);
        List<AuthGroupDetail>  authGroupDetails = new ArrayList<>();
        List<AuthGroupDetailDTO> list = authGroupDTO.getAuthGroupDetails();
        for (AuthGroupDetailDTO ele : list) {
            Menu menu = menuRepository.findById(ele.getMenuId())
                    .orElseThrow(() -> new NotFoundException("user.menu.no_menu.error"));
            AuthGroupDetail authGroupDetail = AuthGroupDetail.builder()
                    .authType(ele.getAuthType())
                    .authGroup(authGroup)
                    .menu(menu)
                    .build();

            authGroupDetails.add(authGroupDetail);
        }

        authGroup.setAuthGroupDetails(authGroupDetails);
        authGroupRepository.save(authGroup);
        return ResponseDTO.success(MESSAGE_KEY_HEAD + "update.success");
    }

    public ResponseDTO deleteAuthGroup(AuthGroupDTO authGroupDTO) {
        AuthGroup authGroup = authGroupRepository.findById(authGroupDTO.getAuthGroupId())
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_authgroup.error"));
        authGroup.softDelete();
        return ResponseDTO.success(MESSAGE_KEY_HEAD + "delete.success");
    }
}
