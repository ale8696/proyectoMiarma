package com.salesianos.dam.proyectoMiarma.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String avatar;
    private String nick;
    private String fullName;
    private String email;
    private boolean privacity;
    private String password;
    private String password2;

}
