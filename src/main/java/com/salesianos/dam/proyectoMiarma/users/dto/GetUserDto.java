package com.salesianos.dam.proyectoMiarma.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private String avatar;
    private String fullName;
    private String email;
    private String role;

}
