package com.ecomm.ecommservice.mapper;

import com.ecomm.ecommservice.dto.response.UserProfileDto;
import com.ecomm.ecommservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserProfileDtoMapper {

    @Mapping(source = "userProfile.firstName", target = "firstName")
    @Mapping(source = "userProfile.lastName", target = "lastName")
    UserProfileDto toDto(User user);
}
