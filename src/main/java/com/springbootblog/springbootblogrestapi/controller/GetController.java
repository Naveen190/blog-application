package com.springbootblog.springbootblogrestapi.controller;


import com.springbootblog.springbootblogrestapi.entity.Post;
import com.springbootblog.springbootblogrestapi.payload.PostDTO;
import com.springbootblog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/get")
@RestController
public class GetController {

    private final PostService postService;


    @Autowired
    public GetController(PostService postService) {
        this.postService = postService;
    }


    @GetMapping
    public List<PostDTO> getPost(){
        return postService.getAllPost();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletPost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post Deleted Successfully");
    }


}
