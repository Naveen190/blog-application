package com.springbootblog.springbootblogrestapi.service;

import com.springbootblog.springbootblogrestapi.entity.Post;
import com.springbootblog.springbootblogrestapi.payload.PostDTO;
import com.springbootblog.springbootblogrestapi.payload.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService {

    PostDTO createPost(PostDTO postDTO);

    PostResponse getAllPost(int pageNo , int pageSize , String sortBy , String sortDir);

    PostDTO getPostById(long id);

    PostDTO updatePost(PostDTO postDTO , long id);

    void deletePostById(long id);
}
