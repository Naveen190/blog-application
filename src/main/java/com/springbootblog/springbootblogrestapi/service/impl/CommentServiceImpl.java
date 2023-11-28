package com.springbootblog.springbootblogrestapi.service.impl;

import com.springbootblog.springbootblogrestapi.entity.Comment;
import com.springbootblog.springbootblogrestapi.entity.Post;
import com.springbootblog.springbootblogrestapi.exception.BlogApiException;
import com.springbootblog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springbootblog.springbootblogrestapi.payload.CommentDTO;
import com.springbootblog.springbootblogrestapi.repository.CommentRepository;
import com.springbootblog.springbootblogrestapi.repository.PostRepository;
import com.springbootblog.springbootblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDTO createComment(Long postId, CommentDTO commentDTO) {
        Comment comment = mapToEntity(commentDTO);
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post" , "id" , postId));

        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);

        return mapToDto(newComment);
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post" , "id" , postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment" , "id" , commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST , "Comments does not belong to post");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDTO updateComment(Long postId, Long commentId, CommentDTO commentDTO) {

        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post" , "id" , postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment" , "id" , commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST , "Comment does not belong to posts");
        }
        comment.setBody(commentDTO.getBody());
        comment.setEmail(commentDTO.getEmail());
        comment.setName(commentDTO.getName());

        Comment updatedComment = commentRepository.save(comment);
        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->
                new ResourceNotFoundException("post" , "id" , postId));


        Comment comment = commentRepository.findById(commentId).orElseThrow(()->
                new ResourceNotFoundException("Comment" , "id" , commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException(HttpStatus.BAD_REQUEST , "Comment does not belong to posts");
        }
        commentRepository.delete(comment);

    }

    private CommentDTO mapToDto(Comment comment){

        CommentDTO commentDTO = mapper.map(comment , CommentDTO.class);
//        CommentDTO commentDTO = new CommentDTO();
//        commentDTO.setId(comment.getId());
//        commentDTO.setName(comment.getName());
//        commentDTO.setBody(comment.getBody());
//        commentDTO.setEmail(comment.getEmail());
        return commentDTO;
    }

    private Comment mapToEntity(CommentDTO commentDTO){
        Comment comment = mapper.map(commentDTO , Comment.class);

//        Comment comment = new Comment();
//        comment.setId(commentDTO.getId());
//        comment.setBody(commentDTO.getBody());
//        comment.setName(commentDTO.getName());
//        comment.setEmail(commentDTO.getEmail());
        return comment;
    }
}
