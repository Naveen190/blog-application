package com.springbootblog.springbootblogrestapi.controller;


import com.springbootblog.springbootblogrestapi.entity.Post;
import com.springbootblog.springbootblogrestapi.payload.PostDTO;
import com.springbootblog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO){
        return new ResponseEntity<>(postService.createPost(postDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<PostDTO> updatePost(@Valid  @RequestBody PostDTO postDTO , @PathVariable(name = "id") Long id){
        PostDTO postResponse = postService.updatePost(postDTO , id);
        return ResponseEntity.ok(postResponse);
    }


}
