package com.springboot.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {

    private Long id;

    @NotEmpty(message = "Title Should Not Be Null or Empty")
    @Size(min = 2, message = "Post Title Should Have At Least Two Characters")
    private String title;

    @NotEmpty(message = "Description Should Not Be Null or Empty")
    @Size(min = 10, message = "Post Description Should Have At Least Ten Characters")
    private String description;

    @NotEmpty(message = "Content Should Not Be Null or Empty")
    private String content;
    private Set<CommentDto> comments;
}
