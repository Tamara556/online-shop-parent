package com.online.store.onlineshopcommon.mapper;

import com.online.store.onlineshopcommon.dto.RegisterUserRequest;
import com.online.store.onlineshopcommon.dto.SaveUserRequest;
import com.online.store.onlineshopcommon.dto.UserDto;
import com.online.store.onlineshopcommon.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    User toEntity(SaveUserRequest request);

}
