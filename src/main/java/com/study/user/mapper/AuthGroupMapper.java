package com.study.user.mapper;

import com.study.user.dto.AuthGroupDTO;
import com.study.user.entity.AuthGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthGroupMapper extends GenericMapper<AuthGroupDTO, AuthGroup> {


}