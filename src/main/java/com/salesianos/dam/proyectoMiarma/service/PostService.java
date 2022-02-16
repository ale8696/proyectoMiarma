package com.salesianos.dam.proyectoMiarma.service;

import com.salesianos.dam.proyectoMiarma.error.exception.ListEntityNotFoundException;
import com.salesianos.dam.proyectoMiarma.error.exception.SingleEntityNotFoundException;
import com.salesianos.dam.proyectoMiarma.model.Post;
import com.salesianos.dam.proyectoMiarma.model.dto.PostDto;
import com.salesianos.dam.proyectoMiarma.model.dto.PostDtoConverter;
import com.salesianos.dam.proyectoMiarma.repository.PostRepository;
import com.salesianos.dam.proyectoMiarma.service.base.BaseService;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post, Long, PostRepository> {

    private final PostDtoConverter postDtoConverter;

    public Post getPost(Long id, UserEntity user){
        Post post = repository.findById(id).orElseThrow(()->
                new SingleEntityNotFoundException(id.toString(), Post.class));
        if (post.isPrivacity() || !post.getOwner().getUsername().equals(user.getUsername())) {
            // UnauthorizedException!!!
        }
        return post;
    }

    public List<PostDto> getPublicPosts(){
        List<PostDto> list = repository.publicPosts(false);
        if(list.isEmpty()){
            throw new ListEntityNotFoundException(Post.class);
        }
        return list;
    }

    public List<PostDto> getUserPosts(String userName, UserEntity currentUser) {
        List<PostDto> list = repository.userPosts(userName);
        if (list.isEmpty()) {
            throw new ListEntityNotFoundException(Post.class);
        }
        return list;
    }

    public Post postPost(PostDto postDto){
        return repository.save(postDtoConverter.postDtoToPost(postDto));
    }

    /*
    public Post putPost(Long id, PostDto postDto, UserEntity user){
        repository.findById(id).map(p->{
            p.setTitle(postDto.getTitle());
            p.setText(postDto.getText());
            p.setDoc(postDto.getDoc());
            repository.save(p);
            return postDto;
        }).orElseThrow(()-> new SingleEntityNotFoundException(id.toString(), Post.class));
    }
    */

    public void delete(Long id){
        repository.findById(id).orElseThrow(()->
                new SingleEntityNotFoundException(id.toString(), Post.class));
        if(repository.findById(id).isPresent())
            repository.deleteById(id);
    }

}
