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

}
