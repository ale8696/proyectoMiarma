package com.salesianos.dam.proyectoMiarma.repository;

import com.salesianos.dam.proyectoMiarma.model.Follow;
import com.salesianos.dam.proyectoMiarma.model.FollowPK;
import com.salesianos.dam.proyectoMiarma.model.dto.FollowDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

    @Query("""
            select new com.salesianos.dam.proyectoMiarma.model.dto.FollowDto(
                c.nick, u.nick, f.createdAt, f.accepted
            )
            from Follow f JOIN f.follower c JOIN f.following u
            where f.accepted = :accepted
            and u.nick = nick
            """)
    List<FollowDto> findByAccepted(
            @Param("accepted") boolean accepted,
            @Param("currentUser") String nick);

}
