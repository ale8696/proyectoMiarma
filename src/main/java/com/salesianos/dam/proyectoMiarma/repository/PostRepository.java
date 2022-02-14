package com.salesianos.dam.proyectoMiarma.repository;

import com.salesianos.dam.proyectoMiarma.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByPrivacity(boolean privacity);

    List<Post> findByUserName(String userName);

}
