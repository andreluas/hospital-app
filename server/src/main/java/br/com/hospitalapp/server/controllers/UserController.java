package br.com.hospitalapp.server.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.hospitalapp.server.dtos.UserDTO;
import br.com.hospitalapp.server.services.UserService;

@RestController
@RequestMapping("/register")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO dto) {
        UserDTO userDTO = service.register(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userDTO.getUserId())
                .toUri();
        return ResponseEntity.created(uri).body(userDTO);
    }
}
