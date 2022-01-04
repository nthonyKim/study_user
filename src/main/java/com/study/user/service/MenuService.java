package com.study.user.service;

import com.study.user.dto.MenuDTO;
import com.study.user.dto.ResponseDTO;
import com.study.user.dto.UserDTO;
import com.study.user.entity.Menu;
import com.study.user.entity.User;
import com.study.user.mapper.MenuMapper;
import com.study.user.mapper.UserMapper;
import com.study.user.repository.MenuRepository;
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
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    private static final String MESSAGE_KEY_HEAD = "user.menu.";

    public List<MenuDTO> findAll(){
        List<Menu> menus = menuRepository.findAll();
        return menus.stream().map(menuMapper::toDTO).collect(Collectors.toList());
    }

    public MenuDTO findById(final String menuId) {
        return menuMapper.toDTO(menuRepository.findById(menuId).orElseThrow());
    }

    public ResponseDTO createMenu(MenuDTO menuDTO) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuDTO.getMenuId());
        if(optionalMenu.isPresent()){
            return ResponseDTO.fail(MESSAGE_KEY_HEAD + "id_exist.error");
        } else {
            menuRepository.save(menuMapper.toEntity(menuDTO));
            return ResponseDTO.success(MESSAGE_KEY_HEAD + "create.success");
        }
    }

    public ResponseDTO updateMenu(MenuDTO menuDTO) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuDTO.getMenuId());
        if(optionalMenu.isPresent()){
            menuRepository.save(menuMapper.toEntity(menuDTO));
            return ResponseDTO.success(MESSAGE_KEY_HEAD + "update.success");
        } else {
            return ResponseDTO.fail(MESSAGE_KEY_HEAD + "no_exist.error");
        }
    }

    public ResponseDTO deleteMenu(MenuDTO menuDTO) {
        Optional<Menu> optionalMenu = menuRepository.findById(menuDTO.getMenuId());
        if(optionalMenu.isPresent()){
            optionalMenu.get().softDelete();
            return ResponseDTO.success(MESSAGE_KEY_HEAD + "delete.success");
        } else {
            return ResponseDTO.fail(MESSAGE_KEY_HEAD + "no_exist.error");
        }
    }
}
