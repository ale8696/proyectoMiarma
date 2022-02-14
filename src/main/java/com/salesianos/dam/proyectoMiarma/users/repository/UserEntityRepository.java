package com.salesianos.dam.proyectoMiarma.users.repository;

import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {
}
