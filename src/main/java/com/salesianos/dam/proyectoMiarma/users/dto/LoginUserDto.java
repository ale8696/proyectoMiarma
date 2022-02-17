package com.salesianos.dam.proyectoMiarma.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDto {

    private String avatar;
    private String nick;
    private String fullName;
    private String email;
    private boolean privacity;
    private String role;
    private String token;

}
