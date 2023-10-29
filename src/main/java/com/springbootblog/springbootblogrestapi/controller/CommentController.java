package com.springbootblog.springbootblogrestapi.controller;

import com.springbootblog.springbootblogrestapi.payload.CommentDTO;
import com.springbootblog.springbootblogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {


    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/posts/{postid}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable(value = "postid") Long postId,
                                                    @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }


    @GetMapping("/posts/{postid}/comments")
    public List<CommentDTO> commentByPostId(@PathVariable(value = "postid") Long postId) {
        return commentService.getCommentsByPostId(postId);
    }

    @GetMapping("/posts/{postid}/comments/{id}")
    public ResponseEntity<CommentDTO> getCommentById(@PathVariable(value = "postid") Long postId,
                                                     @PathVariable(value = "id") Long commentId) {
        CommentDTO commentDTO = commentService.getCommentById(postId, commentId);
        return new ResponseEntity<>(commentDTO, HttpStatus.OK);
    }


    @PutMapping("/posts/{postid}/comments/{id}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable(value = "postid") Long postId,
                                                    @PathVariable(value = "id") Long commentId,
                                                    @RequestBody CommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseEntity<>(updatedComment , HttpStatus.OK);
    }



    @DeleteMapping("/posts/{postid}/comments/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable(value = "postid") Long postId ,
            @PathVariable(value = "id") Long commentId){
        commentService.deleteComment(postId, commentId);
        return ResponseEntity.ok("Comments Deleted Successfully");
    }
}
