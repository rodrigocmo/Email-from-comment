package org.example.controller;


import org.example.entity.Post;
import org.example.entity.User;
import org.example.service.PostService;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")

public class PostController {

@Autowired
PostService postService;


    @GetMapping
    public ResponseEntity<List<Post>> getAllUsers(){
            return Optional.of(postService.getAllPosts()).map(ResponseEntity::ok).orElse(ResponseEntity.noContent().build());
    }

}
