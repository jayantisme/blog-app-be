package com.springboot.controller;

import com.springboot.payload.CommentDto;
import com.springboot.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "CRUD REST APIs For Comment Resource")
@RestController
@RequestMapping("api/v1/posts")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PostMapping("{postId}/new-comment")
    public ResponseEntity<CommentDto> createNewComment(@PathVariable long postId, @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }

    @GetMapping("{postId}/comments")
    public ResponseEntity<List<CommentDto>> getAllComment(@PathVariable long postId) {
        return ResponseEntity.ok(commentService.getCommentByPostId(postId));
    }

    @GetMapping("{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId) {
        return ResponseEntity.ok(commentService.getCommentById(postId, commentId));
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("{postId}/comments/update/{commentId}")
    public ResponseEntity<CommentDto> updateCommentById(@PathVariable long postId,
                                                        @PathVariable long commentId,
                                                        @Valid @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.updateComment(postId, commentId, commentDto));
    }

    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("{postId}/comments/delete/{commentId}")
    public ResponseEntity<String> deleteCommentById(@PathVariable long postId, @PathVariable long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseEntity<>("Comment Deleted Successfully", HttpStatus.NO_CONTENT);
    }
}
