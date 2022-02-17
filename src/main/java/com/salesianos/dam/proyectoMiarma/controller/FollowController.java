package com.salesianos.dam.proyectoMiarma.controller;

import com.salesianos.dam.proyectoMiarma.model.Follow;
import com.salesianos.dam.proyectoMiarma.model.FollowPK;
import com.salesianos.dam.proyectoMiarma.model.dto.FollowDto;
import com.salesianos.dam.proyectoMiarma.service.FollowService;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {

    private final FollowService followService;

    @GetMapping("/list")
    public ResponseEntity<List<FollowDto>> getAllPetitions(
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        List<FollowDto> list = followService.getPetitions(currentUser);
        ResponseEntity<List<FollowDto>> response = ResponseEntity.ok(list);
        return response;
    }

    @PostMapping(value = "/{nick}")
    public ResponseEntity<?> postFollow(@PathVariable String nick,
                                        @AuthenticationPrincipal UserEntity currentUser) {
        if (followService.postPetition(currentUser, nick)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else  {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/accept/{id}", params = "id")
    public ResponseEntity<?> acceptFollow(@AuthenticationPrincipal UserEntity currentUser,
                                          FollowPK id) {
        if (followService.acceptPetition(currentUser, id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(value = "/decline/{id}", params = "id")
    public ResponseEntity<?> declineFollow(@AuthenticationPrincipal UserEntity currentUser,
                                          FollowPK id) {
        if (followService.declinePetition(currentUser, id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }

}
