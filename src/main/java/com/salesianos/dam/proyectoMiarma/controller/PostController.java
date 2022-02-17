package com.salesianos.dam.proyectoMiarma.controller;

import com.salesianos.dam.proyectoMiarma.model.Post;
import com.salesianos.dam.proyectoMiarma.model.dto.PostDto;
import com.salesianos.dam.proyectoMiarma.service.PostService;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/{nick}")
    public ResponseEntity<List<PostDto>> getUserPosts(@PathVariable String nick,
                                                      @AuthenticationPrincipal UserEntity currentUser) {
        return ResponseEntity.ok(postService.getUserPosts(nick, currentUser));
    }

    @GetMapping("/me")
    public ResponseEntity<List<PostDto>> getMyPosts(@AuthenticationPrincipal UserEntity currentUser) {

        return ResponseEntity.ok(postService.getMyPosts(currentUser));

    }

    @PostMapping("/")
    public ResponseEntity<PostDto> postPost(@AuthenticationPrincipal UserEntity currentUser,
                                            @RequestBody PostDto postDto,
                                            @RequestPart MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.postPost(postDto, currentUser, file));

    }

    @PutMapping("{id}")
    public ResponseEntity<PostDto> putPost(@PathVariable Long id,
                                           @RequestBody PostDto postDto,
                                           @RequestPart MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.putPost(id, postDto, file));

    }

}
