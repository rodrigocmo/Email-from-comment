package org.example.controller;


import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")

public class UserController {

@Autowired
UserService userService;

    @GetMapping(path = "/testedenovo")
    public String outroTeste(){
    return "ooutroteste";
    }
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
            return Optional.of(userService.getAllUsers()).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findUserById(@PathVariable Long id){
        return userService.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

}
