package com.salesianos.dam.proyectoMiarma.model;

import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor @Builder
@Getter @Setter
public class Follow {

    // Atributes
    @EmbeddedId
    private FollowPK id = new FollowPK();

    @ManyToOne
    @MapsId("follower_id")
    @JoinColumn(name = "follower_id")
    private UserEntity follower;

    @ManyToOne
    @MapsId("following_id")
    @JoinColumn(name = "following_id")
    private UserEntity following;

    @Builder.Default
    @CreatedDate
    private LocalDateTime followingSince = LocalDateTime.now();

    @Builder.Default
    private boolean accepted = false;

    // Helpers
    public void addToFollower(UserEntity user) {
        follower = user;
        user.getFollowing().add(this);
    }

    public void removeFromFollower(UserEntity user) {
        user.getFollowing().remove(this);
        follower = null;
    }

    public void addToFollowing(UserEntity user) {
        following = user;
        user.getFollowers().add(this);
    }

    public void removeFromFollowing(UserEntity user) {
        user.getFollowers().remove(this);
        following = null;
    }

    public void addFollow(UserEntity follower, UserEntity following) {
        addToFollower(follower);
        addToFollowing(following);
    }

    public void removeFollow(UserEntity follower, UserEntity following) {
        removeFromFollower(follower);
        removeFromFollowing(following);
    }

}
