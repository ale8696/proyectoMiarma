package com.salesianos.dam.proyectoMiarma.service;

import com.salesianos.dam.proyectoMiarma.error.exception.ListEntityNotFoundException;
import com.salesianos.dam.proyectoMiarma.error.exception.SingleEntityNotFoundException;
import com.salesianos.dam.proyectoMiarma.error.exception.UnauthoricedUserException;
import com.salesianos.dam.proyectoMiarma.model.Follow;
import com.salesianos.dam.proyectoMiarma.model.FollowPK;
import com.salesianos.dam.proyectoMiarma.model.dto.FollowDto;
import com.salesianos.dam.proyectoMiarma.repository.FollowRepository;
import com.salesianos.dam.proyectoMiarma.service.base.BaseService;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import com.salesianos.dam.proyectoMiarma.users.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService extends BaseService<Follow, FollowPK, FollowRepository> {

    private final UserEntityService userEntityService;

    public boolean postPetition(UserEntity currentUser, String nick) {
        UserEntity user = userEntityService.loadUserByNick(nick);
        Follow petition = new Follow();
        petition.addFollow(currentUser, user);
        if (petition == repository.save(petition)) {
            return true;
        }
        return false;
    }

    public boolean acceptPetition(UserEntity currentUser, FollowPK id) {
        Follow petition = repository.findById(id).orElseThrow(() ->
                new SingleEntityNotFoundException(id.toString(), Follow.class));
        if (!currentUser.equals(petition.getFollowing())) {
            throw new UnauthoricedUserException();
        }
        petition.setAccepted(true);
        return true;
    }

    public boolean declinePetition(UserEntity currentUser, FollowPK id) {
        Follow petition = repository.findById(id).orElseThrow(() ->
                new SingleEntityNotFoundException(id.toString(), Follow.class));
        if (!currentUser.equals(petition.getFollowing())) {
            throw new UnauthoricedUserException();
        }
        UserEntity follower = petition.getFollower();
        petition.removeFollow(follower, currentUser);
        repository.deleteById(id);
        if (repository.findById(id).isEmpty()) {
            return true;
        }
        return false;
    }

    public List<FollowDto> getPetitions(UserEntity currentUser) {
        List<FollowDto> petitions = repository.findByAccepted(false, currentUser.getNick());
        if (petitions.isEmpty()) {
            throw new ListEntityNotFoundException(Follow.class);
        }
        return petitions;
    }

}
