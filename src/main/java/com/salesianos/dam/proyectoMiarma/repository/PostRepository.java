package com.salesianos.dam.proyectoMiarma.repository;

import com.salesianos.dam.proyectoMiarma.model.Post;
import com.salesianos.dam.proyectoMiarma.model.dto.PostDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
            select new com.salesianos.dam.proyectoMiarma.model.dto.PostDto(
                p.title, p.text, p.doc, p.privacity, u.nick
            )
            from Post p LEFT JOIN p.owner u
            where p.privacity = :privacity
            """)
    List<PostDto> publicPosts(@Param("privacity") boolean privacity);

    @Query("""
            select new com.salesianos.dam.proyectoMiarma.model.dto.PostDto(
                p.title, p.text, p.doc, p.privacity, u.nick
            )
            from Post p LEFT JOIN p.owner u
            where u.nick = :nick
            """)
    List<PostDto> userPosts(@Param("nick") String nick);


}
