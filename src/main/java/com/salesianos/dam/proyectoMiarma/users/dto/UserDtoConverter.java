package com.salesianos.dam.proyectoMiarma.users.dto;

import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public LoginUserDto userEntityToLoginUserDto(UserEntity user, String jwt) {
        return LoginUserDto.builder()
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .token(jwt)
                .build();
    }

    public UserEntity createUserDtoToUserEntity(CreateUserDto dto) {
        return UserEntity.builder()
                .email(dto.getEmail())
                .nick(dto.getNick())
                .password(dto.getPassword())
                .avatar(dto.getAvatar())
                .fullName(dto.getFullName())
                .privacity(dto.isPrivacity())
                .build();
    }

}
