package com.study.user.mapper;

import com.study.user.dto.UserDTO;
import com.study.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDTO, User> {


}