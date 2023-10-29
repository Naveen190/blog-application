package com.springbootblog.springbootblogrestapi.payload;


import com.springbootblog.springbootblogrestapi.entity.Post;
import lombok.Data;

@Data
public class CommentDTO {
    private long id;
    private String name;
    private String email;
    private String body;
}
