package com.springboot.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Schema(
        description = "PostDto Model Information"
)
@Data
public class PostDto {

    private Long id;

    @Schema(description = "Blog Post Title")
    @NotEmpty(message = "Title Should Not Be Null or Empty")
    @Size(min = 2, message = "Post Title Should Have At Least Two Characters")
    private String title;

    @Schema(description = "Blog Post Description")
    @NotEmpty(message = "Description Should Not Be Null or Empty")
    @Size(min = 10, message = "Post Description Should Have At Least Ten Characters")
    private String description;

    @Schema(description = "Blog Post Content")
    @NotEmpty(message = "Content Should Not Be Null or Empty")
    private String content;

    private Set<CommentDto> comments;

    @Schema(description = "Blog Post Category")
    private Long categoryId;
}
