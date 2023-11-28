package com.springbootblog.springbootblogrestapi.payload;


import com.springbootblog.springbootblogrestapi.entity.Post;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CommentDTO {
    private long id;
    @NotEmpty(message = "Name Should not be null or empty")
    private String name;
    @NotEmpty(message = "Email can not be null")
    @Email
    private String email;
    @Size(min = 10 , message = "Comment body must be minimum 10 character")
    private String body;
}
