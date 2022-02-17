package com.salesianos.dam.proyectoMiarma.controller;

import com.salesianos.dam.proyectoMiarma.model.dto.PostDto;
import com.salesianos.dam.proyectoMiarma.service.PostService;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/public")
    public ResponseEntity<List<PostDto>> getPublicPosts() {
        return ResponseEntity.ok(postService.getPublicPosts());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id,
                                           @AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(postService.getPost(id, currentUser));
    }

    //@GetMapping("{nick}")
    //public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable String nick,
    //                                                  @AuthenticationPrincipal UserEntity currentUser) {
    //    return ResponseEntity.ok(postService.get)
    //}

}
