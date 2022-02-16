package com.salesianos.dam.proyectoMiarma.controller;

import com.salesianos.dam.proyectoMiarma.model.Follow;
import com.salesianos.dam.proyectoMiarma.model.dto.FollowDto;
import com.salesianos.dam.proyectoMiarma.service.FollowService;
import com.salesianos.dam.proyectoMiarma.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
