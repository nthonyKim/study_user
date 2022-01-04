package com.study.user.mapper;

import com.study.user.dto.MenuDTO;
import com.study.user.entity.Menu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MenuMapper extends GenericMapper<MenuDTO, Menu> {
    @Override
    @Mapping(target = "parentMenuId", expression = "java(menu.getParentMenu() != null ? menu.getParentMenu().getMenuId() : null)")
    MenuDTO toDTO(Menu menu);
}