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
        if (post.isPrivacity() || !post.getOwnerName().equals(user.getUsername())) {
            // UnauthorizedException!!!
        }
        return post;
    }

    public List<Post> getPublicPosts(){
        List<Post> list = repository.findByPrivacity(false);
        if(list.isEmpty()){
            throw new ListEntityNotFoundException(Post.class);
        }
        return list;
    }

    //Hay que darle un repaso
    public List<Post> getUserPosts(String userName, UserEntity currentUser) {
        List<Post> list = repository.findByUserName(userName);
        if (list.isEmpty()) {
            throw new ListEntityNotFoundException(Post.class);
        }
        return list;
    }

    public Post postPost(PostDto postDto){
        return repository.save(postDtoConverter.postDtoToPost(postDto));
    }

    public Post putPost(Long id, PostDto postDto, UserEntity user){
        repository.findById(id).map(p->{
            p.setTitulo(postDto.getTitulo());
            p.setTexto(postDto.getTexto());
            p.setDoc(postDto.getDoc());
            repository.save(p);
            return p;
        }).orElseThrow(()-> new SingleEntityNotFoundException(id.toString(), Post.class));
    }

    public void delete(Long id){
        repository.findById(id).orElseThrow(()->
                new SingleEntityNotFoundException(id.toString(), Post.class));
        if(repository.findById(id).isPresent())
            repository.deleteById(id);
    }

}
