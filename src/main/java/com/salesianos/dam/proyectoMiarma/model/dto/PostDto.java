package com.salesianos.dam.proyectoMiarma.model.dto;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.File;

@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class PostDto {

    // Attributes
    private String titulo;
    private String texto;
    private File doc;
    private boolean privacity;

}
