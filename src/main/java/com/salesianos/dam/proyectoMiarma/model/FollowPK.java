package com.salesianos.dam.proyectoMiarma.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class FollowPK implements Serializable {

    private Long follower_id;
    private Long following_id;

}
