package com.salesianos.dam.proyectoMiarma.model.dto;

import com.salesianos.dam.proyectoMiarma.model.Follow;

public class FollowDtoConverter {

    public FollowDto followToFollowDto(Follow follow) {
        return FollowDto.builder()
                .follower(follow.getFollower().getNick())
                .following(follow.getFollowing().getNick())
                .createdAt(follow.getCreatedAt())
                .accepted(follow.isAccepted())
                .build();
    }

}
