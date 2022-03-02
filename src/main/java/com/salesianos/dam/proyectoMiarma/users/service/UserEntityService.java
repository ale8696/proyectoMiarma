package com.salesianos.dam.proyectoMiarma.users.service;

import com.salesianos.dam.proyectoMiarma.service.base.BaseService;
import com.salesianos.dam.proyectoMiarma.users.dto.CreateUserDto;
import com.salesianos.dam.proyectoMiarma.users.dto.UserDtoConverter;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import com.salesianos.dam.proyectoMiarma.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("userEntityService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, Long, UserEntityRepository> implements UserDetailsService {

    private final UserDtoConverter userDtoConverter;

    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " not found"));
    }

    public UserEntity loadUserByNick(String nick) throws UsernameNotFoundException {
        return repository.findFirstByNick(nick)
                .orElseThrow(()-> new UsernameNotFoundException(nick + " not found"));
    }

    public UserEntity createUser(CreateUserDto newUser) {

        return repository.save(userDtoConverter.createUserDtoToUserEntity(newUser));

    }

}
