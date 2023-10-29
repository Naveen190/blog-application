package com.springbootblog.springbootblogrestapi.service.impl;

import com.springbootblog.springbootblogrestapi.entity.Post;
import com.springbootblog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springbootblog.springbootblogrestapi.payload.PostDTO;
import com.springbootblog.springbootblogrestapi.payload.PostResponse;
import com.springbootblog.springbootblogrestapi.repository.PostRepository;
import com.springbootblog.springbootblogrestapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private final PostRepository postRepository;


    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Post post = mapToEntity(postDTO);
        Post newPost = postRepository.save(post);

        PostDTO postResponse = mapToDTO(newPost);
        return postResponse;
    }

    @Override
    public PostResponse getAllPost(int pageNo , int pageSize , String sortBy , String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending() ;
        Pageable pageable = PageRequest.of(pageNo , pageSize , sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> listPosts = posts.getContent();
        List<PostDTO> content = posts.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements((int) posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());
        return postResponse;
    }

    @Override
    public PostDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post" , "id" , id));
        return mapToDTO(post);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post" , "id" , id));
        post.setDescription(postDTO.getDescription());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        Post updatedPost = postRepository.save(post);
        return mapToDTO(updatedPost);
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("post" , "id" , id));
        postRepository.delete(post);
    }


    // Converted Entity to DTO
    private PostDTO mapToDTO(Post post){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setTitle(post.getTitle());
        postDTO.setDescription(post.getDescription());
        return postDTO;
    }

    private Post mapToEntity(PostDTO postDTO){
        Post post = new Post();
        post.setDescription(postDTO.getDescription());
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        return post;
    }
}
