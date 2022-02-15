package com.salesianos.dam.proyectoMiarma.repository;

import com.salesianos.dam.proyectoMiarma.model.Follow;
import com.salesianos.dam.proyectoMiarma.model.FollowPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

    List<Follow> findByAccepted(boolean accepted);

}
