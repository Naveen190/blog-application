package com.springbootblog.springbootblogrestapi.payload;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDTO {
    private long id;
    @NotEmpty
    @Size(min = 2 , message = "Post title should have at least 2 character")
    private String title;
    @NotEmpty
    @Size(min = 10 , message = "Post description should have at least 10 character")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDTO> commentDTOSet;
}
