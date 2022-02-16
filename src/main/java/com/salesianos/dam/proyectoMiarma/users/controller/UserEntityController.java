package com.salesianos.dam.proyectoMiarma.users.controller;

import com.salesianos.dam.proyectoMiarma.users.dto.CreateUserDto;
import com.salesianos.dam.proyectoMiarma.users.dto.GetUserDto;
import com.salesianos.dam.proyectoMiarma.users.dto.UserDtoConverter;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import com.salesianos.dam.proyectoMiarma.users.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserEntityController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> nuevoUsuario(@RequestBody CreateUserDto newUser) {
        UserEntity saved = userEntityService.save(newUser);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }

}
