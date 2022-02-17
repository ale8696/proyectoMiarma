package com.salesianos.dam.proyectoMiarma.users.controller;

import com.salesianos.dam.proyectoMiarma.error.exception.PasswordNotMatchException;
import com.salesianos.dam.proyectoMiarma.security.jwt.JwtProvider;
import com.salesianos.dam.proyectoMiarma.users.dto.CreateUserDto;
import com.salesianos.dam.proyectoMiarma.users.dto.LoginUserDto;
import com.salesianos.dam.proyectoMiarma.users.dto.UserDtoConverter;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import com.salesianos.dam.proyectoMiarma.users.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserEntityController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/register")
    public ResponseEntity<LoginUserDto> registerUser(@RequestBody CreateUserDto newUser) {

        //LoginUserDto loginUserDto = userEntityService.createUser(newUser);
//
        //return ResponseEntity.status(HttpStatus.CREATED).body(loginUserDto);

        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {


            UserEntity userEntity = userDtoConverter.createUserDtoToUserEntity(newUser);

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userEntity.getNick(),
                            userEntity.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateToken(authentication);

            LoginUserDto loginUserDto = userDtoConverter.userEntityToLoginUserDto(userEntity, jwt);

            return ResponseEntity.status(HttpStatus.CREATED).body(loginUserDto);

        }else {
            throw new PasswordNotMatchException();
        }

    }




}
