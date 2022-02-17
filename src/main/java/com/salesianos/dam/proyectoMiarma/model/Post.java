package com.salesianos.dam.proyectoMiarma.model;

import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import java.io.File;

@Entity
@Table(name = "post")
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Post {

    // Attributes
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String text;
    private String doc;
    private boolean privacity;

    @ManyToOne
    private UserEntity owner;

}
