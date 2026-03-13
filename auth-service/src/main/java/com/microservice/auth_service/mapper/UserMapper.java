package com.microservice.auth_service.mapper;

import com.microservice.auth_service.dto.AdminCreateRequestUser;
import com.microservice.auth_service.dto.RegisterRequest;
import com.microservice.auth_service.dto.UserResponse;
import com.microservice.auth_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "USER")
    @Mapping(target = "enabled", constant = "true")
    User registerRequestToUser(RegisterRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    User AdminCreateRequestUserToUser(AdminCreateRequestUser requestUser);

    UserResponse userToUserResponse(User user);


}
