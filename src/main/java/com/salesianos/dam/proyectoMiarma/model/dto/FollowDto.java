package com.salesianos.dam.proyectoMiarma.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class FollowDto {

    // Attributes
    private String follower;
    private String following;
    private LocalDateTime createdAt;
    private boolean accepted;

}
