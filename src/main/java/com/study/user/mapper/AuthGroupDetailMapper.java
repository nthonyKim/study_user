package com.study.user.mapper;

import com.study.user.dto.AuthGroupDetailDTO;
import com.study.user.entity.AuthGroupDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthGroupDetailMapper extends GenericMapper<AuthGroupDetailDTO, AuthGroupDetail> {


}