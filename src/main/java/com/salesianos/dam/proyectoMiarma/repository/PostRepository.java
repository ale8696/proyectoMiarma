package com.salesianos.dam.proyectoMiarma.repository;

import com.salesianos.dam.proyectoMiarma.model.Post;
import com.salesianos.dam.proyectoMiarma.model.dto.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            select new com.salesianos.dam.proyectoMiarma.model.dto.PostDto(
                p.title, p.text, p.doc, p.privacity
            )
            from Post p
            where p.privacity = :privacity
            """)
    List<PostDto> publicPosts(@Param("privacity") boolean privacity);

    @Query("""
            select new com.salesianos.dam.proyectoMiarma.model.dto.PostDto(
                p.title, p.text, p.doc, p.privacity
            )
            from Post p LEFT JOIN p.owner u
            where u.email = :userName
            """)
    List<PostDto> userPosts(@Param("userName") String userName);

}
