package com.salesianos.dam.proyectoMiarma.service;

import com.salesianos.dam.proyectoMiarma.error.exception.ListEntityNotFoundException;
import com.salesianos.dam.proyectoMiarma.error.exception.SingleEntityNotFoundException;
import com.salesianos.dam.proyectoMiarma.error.exception.UnauthoricedUserException;
import com.salesianos.dam.proyectoMiarma.model.Follow;
import com.salesianos.dam.proyectoMiarma.model.Post;
import com.salesianos.dam.proyectoMiarma.model.dto.PostDto;
import com.salesianos.dam.proyectoMiarma.model.dto.PostDtoConverter;
import com.salesianos.dam.proyectoMiarma.repository.PostRepository;
import com.salesianos.dam.proyectoMiarma.service.base.BaseService;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import com.salesianos.dam.proyectoMiarma.users.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService extends BaseService<Post, Long, PostRepository> {

    private final PostDtoConverter postDtoConverter;
    private final FileSystemStorageService storageService;
    private final UserEntityService userEntityService;


    public PostDto getPost(Long id, UserEntity currentUser){
        Post post = repository.findById(id).orElseThrow(()->
                new SingleEntityNotFoundException(id.toString(), Post.class));

        Follow follow = currentUser.getFollowing().stream()
                .filter(f -> f.getFollowing() == post.getOwner())
                .findFirst().orElseThrow(() -> new UnauthoricedUserException());

        if (post.isPrivacity() || follow == null) {
            throw  new UnauthoricedUserException();
        }
        return postDtoConverter.postToPostDto(post);
    }


    public List<PostDto> getPublicPosts(){
        List<PostDto> list = repository.publicPosts(false);
        if(list.isEmpty()){
            throw new ListEntityNotFoundException(Post.class);
        }
        return list;
    }


    public List<PostDto> getUserPosts(String nick, UserEntity currentUser) {
        List<PostDto> list = repository.userPosts(nick);
        if (list.isEmpty()) {
            throw new ListEntityNotFoundException(Post.class);
        }
        List<PostDto> publicList = list.stream().filter(p -> !p.isPrivacity()).toList();
        UserEntity user = userEntityService.loadUserByNick(nick);
        if (currentUser.getFollowing().stream().anyMatch(
                f -> f.getFollowing() == user || f.isAccepted())) {
            return list;
        } else {
            return list.stream().filter(p -> !p.isPrivacity()).toList();
        }
    }

    public List<PostDto> getMyPosts(UserEntity currentUser) {
        List<PostDto> list = repository.userPosts(currentUser.getNick());
        if (list.isEmpty()) {
            throw new ListEntityNotFoundException(Post.class);
        }
        return list;
    }

    public PostDto postPost(PostDto postDto, UserEntity currentUser, MultipartFile file){
        Post post = postDtoConverter.postDtoToPost(postDto);

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        post.setOwner(currentUser);
        post.setDoc(uri);
        return postDtoConverter.postToPostDto(repository.save(post));
    }

    public PostDto putPost(Long id, PostDto postDto, MultipartFile file){

        String filename = storageService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return repository.findById(id).map(p->{
            p.setTitle(postDto.getTitle());
            p.setText(postDto.getText());
            p.setDoc(uri);
            repository.save(p);
            return postDto;
        }).orElseThrow(()-> new SingleEntityNotFoundException(id.toString(), Post.class));
    }

    // TODO
    public void delete(Long id){
        Post post = repository.findById(id).orElseThrow(()->
                new SingleEntityNotFoundException(id.toString(), Post.class));
        post.getDoc();
        if(repository.findById(id).isPresent())
            repository.deleteById(id);
    }

}
