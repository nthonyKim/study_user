package com.study.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.user.dto.MenuDTO;
import com.study.user.dto.ResponseDTO;
import com.study.user.entity.AuthGroupDetail;
import com.study.user.entity.Menu;
import com.study.user.exception.NotFoundException;
import com.study.user.mapper.MenuMapper;
import com.study.user.repository.AuthGroupDetailRepository;
import com.study.user.repository.MenuRepository;
import com.study.user.util.CollectionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class MenuService {
    private final MenuRepository menuRepository;
    private final AuthGroupDetailRepository authGroupDetailRepository;
    private final MenuMapper menuMapper;
    private static final String MESSAGE_KEY_HEAD = "user.menu.";

    public List<MenuDTO> findAll(){
        List<Menu> menus = menuRepository.findAll();
        return menus.stream().map(menuMapper::toDTO).collect(Collectors.toList());
    }

    public MenuDTO findById(final String menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_menu.error"));
        return menuMapper.toDTO(menu);
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
        Menu menu = menuRepository.findById(menuDTO.getMenuId())
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_menu.error"));
        menuRepository.save(menuMapper.toEntity(menuDTO));
        return ResponseDTO.success(MESSAGE_KEY_HEAD + "update.success");
    }

    public ResponseDTO deleteMenu(MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuDTO.getMenuId())
                .orElseThrow(() -> new NotFoundException(MESSAGE_KEY_HEAD + "no_menu.error"));
        menu.softDelete();
        return ResponseDTO.success(MESSAGE_KEY_HEAD + "delete.success");
    }

    public ResponseDTO updateToRedis() throws JsonProcessingException {
        ObjectMapper objectWrapper = new ObjectMapper();
        List<Menu> menus = menuRepository.findAll();
//        Map<Object, List<AuthGroupDetail>> authGroupDetailMap = authGroupDetailRepository.findAll()
//                .stream().collect(Collectors.groupingBy(a -> a.getMenu().getMenuId()));

        for(Menu menu : menus) {
            Map<String, String> menuMap = objectWrapper.convertValue(menuMapper.toDTO(menu), Map.class);
            Map<String, String> authMap = new HashMap<>();

//            List<AuthGroupDetail> authGroupDetails = authGroupDetailMap.get(menu.getMenuId());
//            if(CollectionUtil.isNotEmpty(authGroupDetails)){
//                //
//            }

        }
        return ResponseDTO.success();
    }
}
