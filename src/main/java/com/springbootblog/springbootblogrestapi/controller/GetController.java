package com.springbootblog.springbootblogrestapi.controller;


import com.springbootblog.springbootblogrestapi.entity.Post;
import com.springbootblog.springbootblogrestapi.payload.PostDTO;
import com.springbootblog.springbootblogrestapi.payload.PostResponse;
import com.springbootblog.springbootblogrestapi.service.PostService;
import com.springbootblog.springbootblogrestapi.utility.AppContants;
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
    public PostResponse getPost(@RequestParam(value = "pageNo" , defaultValue = AppContants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                @RequestParam(value = "pageSize" , defaultValue = AppContants.DEFAULT_PAGE_SIZE , required = false) int pageSize,
                                @RequestParam(value = "sortBy" , defaultValue = AppContants.DEFAULT_SORT_BY , required = false) String sortBy,
                                @RequestParam(value = "sortDir" , defaultValue = AppContants.DEFAULT_SORT_DIRECTION , required = false) String sortDir
                                 ) {
        return postService.getAllPost(pageNo , pageSize , sortBy , sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletPost(@PathVariable(name = "id") Long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Post Deleted Successfully");
    }


}
