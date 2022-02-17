package com.salesianos.dam.proyectoMiarma.model.dto;

import com.salesianos.dam.proyectoMiarma.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDtoConverter {

    public Post postDtoToPost(PostDto dto) {
        return Post.builder()
                .title(dto.getTitle())
                .text(dto.getText())
                .doc(dto.getDoc())
                .privacity(dto.isPrivacity())
                .build();
    }

    public PostDto postToPostDto(Post post) {
        return PostDto.builder()
                .title(post.getTitle())
                .text(post.getText())
                .doc(post.getDoc())
                .privacity(post.isPrivacity())
                .Owner(post.getOwner().getNick())
                .build();
    }

}
