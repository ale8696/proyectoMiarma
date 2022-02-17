package com.salesianos.dam.proyectoMiarma.users.service;

import com.salesianos.dam.proyectoMiarma.security.SecurityConfig;
import com.salesianos.dam.proyectoMiarma.service.base.BaseService;
import com.salesianos.dam.proyectoMiarma.users.dto.CreateUserDto;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import com.salesianos.dam.proyectoMiarma.users.model.UserRole;
import com.salesianos.dam.proyectoMiarma.users.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("userDetailsService")
@RequiredArgsConstructor
public class UserEntityService extends BaseService<UserEntity, UUID, UserEntityRepository> implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.repository.findFirstByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email + " not found"));
    }

    public UserEntity loadUserByNick(String nick) throws UsernameNotFoundException {
        return this.repository.findFirstByNick(nick)
                .orElseThrow(()-> new UsernameNotFoundException(nick + " not found"));
    }

    public UserEntity save(CreateUserDto newUser) {
        if (newUser.getPassword().contentEquals(newUser.getPassword2())) {
            UserEntity userEntity = UserEntity.builder()
                    .password(passwordEncoder.encode(newUser.getPassword()))
                    .avatar(newUser.getAvatar())
                    .fullName(newUser.getFullName())
                    .email(newUser.getEmail())
                    .privacity(newUser.isPrivacity())
                    .role(UserRole.USER)
                    .build();
            return save(userEntity);
        } else {
            return null;
        }
    }


}
