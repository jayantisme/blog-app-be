package com.springboot.controller;

import com.springboot.payload.PostDto;
import com.springboot.payload.PostResponse;
import com.springboot.service.PostService;
import com.springboot.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CRUD REST APIs For Post Resource")
@RestController
@RequestMapping("api/v1/posts")
public class PostController {
    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post Rest API",
            description = "Create post REST API is used to save post into database."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http Status 201 CREATED"
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("new-post")
    public ResponseEntity<PostDto> createNewPost(@Valid @RequestBody PostDto postDto) {
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get All Post Rest API",
            description = "Get all post REST API is used to get all posts from database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {
        return ResponseEntity.ok(postService.getAllPost(pageNo, pageSize, sortBy, sortDir));
    }

    @Operation(
            summary = "Get Post By Id Rest API",
            description = "Get post by id REST API is used to get single post from database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @Operation(
            summary = "Update Post By Id REST API",
            description = "Update post by id REST API is used to update post in database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("update/{id}")
    public ResponseEntity<PostDto> updatePostById(@Valid @RequestBody PostDto postDto, @PathVariable long id) {
        return ResponseEntity.ok(postService.updatePost(postDto, id));
    }

    @Operation(
            summary = "Delete Post By Id REST API",
            description = "Delete post by id REST API is used to delete post from database."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @SecurityRequirement(name = "Bear Authentication")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deletePostById(@PathVariable long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Successfully Deleted!", HttpStatus.OK);
    }

    @Operation(
            summary = "Get Posts By Category Id REST API",
            description = "Get Posts By Category Id REST API is used to get all posts of given category."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable("id") Long categoryId) {
        return ResponseEntity.ok(postService.getPostByCategory(categoryId));
    }
}
