package com.springboot.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentDto {
    private long id;

    @NotEmpty(message = "Name Should Not Be Null or Empty")
    private String name;

    @NotEmpty(message = "Email Should Not Be Null or Empty")
    @Email(message = "Invalid Email")
    private String email;

    @NotEmpty(message = "Body Should Not Be Null or Empty")
    @Size(min = 10, message = "Comment Body Should Have At Least Ten Characters")
    private String body;
}
